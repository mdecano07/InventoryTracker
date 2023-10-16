package com.berenberg.InventoryTracker.utils;

import com.berenberg.InventoryTracker.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CsvReaderTest {
    private CsvReader csvReader;


    @BeforeEach
    public void init() {
        csvReader = new CsvReader(); // Create an instance of CsvReader
    }

    @Test
    public void testReadItemsFromCsvWithActualFile() {
        // GIVEN
        var fileName = "items_test.csv";
        var expectedItems = List.of(
                new Item(1, 5, "DVD", "Pi"),
                new Item(2, 6, "VHS", "Pi"),
                new Item(3, 1, "Book", "The Art Of Computer Programming Volumes 1-6"),
                new Item(4, 2, "Book", "The Pragmatic Programmer"),
                new Item(5, 4, "Book", "Introduction to Algorithms"),
                new Item(6, 4, "Book", "Introduction to Algorithms"),
                new Item(7, 4, "Book", "Introduction to Algorithms"),
                new Item(8, 5, "DVD", "Pi")
        );

        //WHEN
        var itemsResult = csvReader.readItemsFromCsv(fileName, "test");

        //THEN
        assertNotNull(itemsResult);
        assertEquals(8, itemsResult.size());
        assertTrue(itemsResult.containsAll(expectedItems));
    }

}
