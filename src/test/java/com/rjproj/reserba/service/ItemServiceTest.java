package com.rjproj.reserba.service;

import com.rjproj.reserba.dto.ItemDto;
import com.rjproj.reserba.model.Item;
import com.rjproj.reserba.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

// CORRECT IMPORTS
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void getItem_ShouldReturnDto_WhenIdExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        Item mockItem = new Item(id, "Test Name", "Description", BigDecimal.TEN, "url", "Category");
        when(itemRepository.findById(id)).thenReturn(Optional.of(mockItem));

        // Act
        ItemDto result = itemService.getItem(id);

        // Assert
        assertNotNull(result);
        assertEquals("Test Name", result.name());
        assertEquals(BigDecimal.TEN, result.price());

        // Mockito Verify (Ensures the DB was actually called)
        verify(itemRepository).findById(id);
    }

    @Test
    void getItem_ShouldThrowException_WhenIdDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            itemService.getItem(id);
        });

        // Optional: Check if the message is correct
        assertTrue(exception.getMessage().contains("Item does not exist"));
    }
}