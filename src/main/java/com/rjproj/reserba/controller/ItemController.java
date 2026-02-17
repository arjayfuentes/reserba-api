package com.rjproj.reserba.controller;

import com.rjproj.reserba.dto.ItemDto;
import com.rjproj.reserba.model.Item;
import com.rjproj.reserba.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular to connect
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems() {
        return ResponseEntity.ok(itemService.getItems());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(itemService.getCategories());
    }

    @GetMapping("/sumPriceCategory")
    public ResponseEntity<BigDecimal> getSumPricePerCategory(
            @RequestParam(name="category") String category
    ) {
        return ResponseEntity.ok(itemService.getSumPricePerCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(
            @RequestParam(name="minPrice", defaultValue = "0") BigDecimal minPrice,
            @RequestParam(name="category", required=false) String category
    ) {
        return ResponseEntity.ok(itemService.searchItems(minPrice, category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItem(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(itemService.getItem(id));
    }

    @GetMapping("/searchItemByName")
    public ResponseEntity<List<ItemDto>> searchItemByName(
        @RequestParam(name="name", required=false) String name
    ) {
        return ResponseEntity.ok(itemService.searchItemByName(name));
    }

    @PostMapping
    public ResponseEntity<ItemDto> saveItem(@RequestBody ItemDto itemDto) {
        return ResponseEntity.status(201).body(itemService.saveItem(itemDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable(value="id") UUID id, @RequestBody ItemDto itemDto) {
        return ResponseEntity.ok(itemService.updateItem(id, itemDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable(value="id") UUID id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}