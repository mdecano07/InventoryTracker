package com.berenberg.InventoryTracker.utils;

import com.berenberg.InventoryTracker.model.Item;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CsvReader {
    private final Logger logger = LoggerFactory.getLogger(CsvReader.class);

    public List<Item> readItemsFromCsv(String fileName, String packageName) {
        List<Item> items = new ArrayList<>();

        try (FileReader reader = new FileReader(getResourcePath(fileName, packageName));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            var csvRecordStream = StreamSupport.stream(csvParser.spliterator(), false);
            items = csvRecordStream.map(record -> {
                Integer uniqueId = Integer.valueOf(record.get("UniqueID"));
                int id = Integer.parseInt(record.get("ItemID"));
                String type = record.get("Type");
                String title = record.get("Title");
                return new Item(uniqueId, id, type, title);
            }).collect(Collectors.toList());

        } catch (IOException e) {
            logger.error("An error occurred while processing the CSV file:  " + fileName, e);
        }
        return items;
    }

    private String getResourcePath(String fileName, String packageName) {
        var resourceDirectory = Paths.get("src", packageName, "resources");
        return resourceDirectory.resolve(fileName).toAbsolutePath().toString();
    }

}
