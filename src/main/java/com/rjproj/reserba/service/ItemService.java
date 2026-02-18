package com.rjproj.reserba.service;

import com.rjproj.reserba.dto.ItemDto;
import com.rjproj.reserba.exception.ItemNotFoundException;
import com.rjproj.reserba.model.Item;
import com.rjproj.reserba.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemDto saveItem(ItemDto itemDto) {
        Item item = new Item(
                null,
                itemDto.name(),
                itemDto.description(),
                itemDto.price(),
                itemDto.imageUrl(),
                "new Catergory"
        );
        Item savedItem = itemRepository.save(item);

        return new ItemDto(
                savedItem.getId().toString(),
                savedItem.getName(),
                savedItem.getDescription(),
                savedItem.getPrice(),
                savedItem.getImageUrl()
        );
    }

    public List<ItemDto> getItems() {
        List<Item> items = itemRepository.findAll();

        return items.stream()
                .map(item -> new ItemDto(
                        item.getId().toString(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getImageUrl()
                )).toList();
    }

    public ItemDto getItem(UUID id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        return mapToDto(item);
    }

    public ItemDto updateItem(UUID id, ItemDto itemDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"item does not exist" + id));

        Item updatedItem = new Item(
                item.getId(),
                itemDto.name(),
                itemDto.description(),
                itemDto.price(),
                itemDto.imageUrl(),
                item.getCategory()
        );

        Item savedItem = itemRepository.save(updatedItem);

        return new ItemDto(
                savedItem.getId().toString(),
                savedItem.getName(),
                savedItem.getDescription(),
                savedItem.getPrice(),
                savedItem.getImageUrl()
        );
    }

    public void deleteItem(UUID id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"You cannot delete unexisting item " + id));
        itemRepository.delete(item);
    }

    // --------------------- NON CRUD -------------------------
    public List<ItemDto> filterItemsByCategories(List<String> categories) {
        List<Item> items = itemRepository.findAll();

        return items.stream()
                .filter(item -> categories.contains(item.getCategory()))
                .map(itemFiltered -> new ItemDto(
                        itemFiltered.getId().toString(),
                        itemFiltered.getName(),
                        itemFiltered.getDescription(),
                        itemFiltered.getPrice(),
                        itemFiltered.getImageUrl()
                )).toList();
    }

    public List<String> getCategories() {
        return itemRepository.findAllCategories();
    }

    public BigDecimal getSumPriceOfCategory(String category) {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .filter(item -> category.equals(item.getCategory()))
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        return items.stream()
//                .filter(item -> category.equals(item.getCategory()))
//                .mapToDouble(Item::getPrice)
//                .sum();
    }

    public Map<String, BigDecimal> getSumPriceOfCategories() {
        List<Item> items = itemRepository.findAll();
        Map<String, BigDecimal> totals = items.stream()
                .collect(Collectors.groupingBy(
                       Item::getCategory,
                       Collectors.reducing(
                               BigDecimal.ZERO,
                               Item::getPrice,
                               BigDecimal::add
                       )
                       // Collectors.summingDouble(Product::getPrice)  // if double
                ));
        return totals;
    }

    // ---------------------
    public List<ItemDto> searchItemByName(String name) {
        List<Item> items = itemRepository.findByNameContainingIgnoreCase(name);

        return items.stream()
                .map(this::mapToDto).toList();

    }

    public List<ItemDto> searchItems(BigDecimal price, String category) {
        List<Item> items = itemRepository.findByCategoryAndPriceLessThanEqual(category,price);

        return items.stream()
                .map(item -> new ItemDto(
                        item.getId().toString(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getImageUrl()
                )).toList();
    }

    private ItemDto mapToDto(Item item) {
        return new ItemDto(
                item.getId().toString(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getImageUrl()
        );
    }

}