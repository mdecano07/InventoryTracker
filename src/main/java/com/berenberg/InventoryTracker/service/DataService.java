package com.berenberg.InventoryTracker.service;

import com.berenberg.InventoryTracker.model.Item;
import com.berenberg.InventoryTracker.model.Loan;
import com.berenberg.InventoryTracker.model.User;
import com.berenberg.InventoryTracker.utils.CsvReader;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataService {
    private final Logger logger = LoggerFactory.getLogger(DataService.class);
    @Autowired
    private final CsvReader csvReader;
    @Getter
    private final Map<Integer, Item> inventoryData;
    @Getter
    private final List<Loan> loanData = new ArrayList<>();
    @Getter
    private final Map<Integer, User> userData = new HashMap<>();

    public DataService(CsvReader csvReader) {
        this.csvReader = csvReader;
        this.inventoryData = initialiseInventoryData();

        // Initialise userData with dummy data
        userData.put(1, new User(1, "John", " Doe", "john.doe@gmail.com"));
        userData.put(2, new User(2, "Alice", "Smith", "alice.smith@gmail.com"));
        userData.put(3, new User(3, "Bob", " Johnson", "bob.johnson@gmail.com"));
    }

    private Map<Integer, Item> initialiseInventoryData() {
        var itemsList = csvReader.readItemsFromCsv("Items.csv", "main");
        //convert inventory data to map so we can do a look-up by uniqueItemId
        return itemsList.stream().collect(Collectors.toUnmodifiableMap(Item::getUniqueItemId, item -> item));
    }

    //return loan data
    public void addLoanData(List<Loan> loansList) {
        loanData.addAll(loansList);
    }

    public void removeLoanData(List<Loan> loansList) {
        loanData.removeAll(loansList);
    }

}
