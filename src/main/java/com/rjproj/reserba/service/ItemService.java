package com.rjproj.reserba.service;

import com.rjproj.reserba.dto.ItemDto;
import com.rjproj.reserba.model.Item;
import com.rjproj.reserba.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

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
                .orElseThrow(() -> new RuntimeException("Item does not exist " + id));

        return new ItemDto(
                item.getId().toString(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getImageUrl()
        );
    }

    public List<String> getCategories() {
        return itemRepository.findAllCategories();
    }

    public BigDecimal getSumPricePerCategory(String category) {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .filter(item -> category.equals(item.getCategory()))
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

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

    public ItemDto updateItem(UUID id, ItemDto itemDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("item does not exist" + id));

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
                .orElseThrow(() -> new RuntimeException("You cannot delete unexisting item " + id));
        itemRepository.delete(item);
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


    public List<ItemDto> searchItemByName(String name) {
        List<Item> items = itemRepository.findByNameContainingIgnoreCase(name);

        return items.stream()
                .map(item -> new ItemDto(
                        item.getId().toString(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getImageUrl()
                )).toList();

    }

}