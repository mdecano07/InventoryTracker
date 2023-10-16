package com.berenberg.InventoryTracker.service;

import com.berenberg.InventoryTracker.model.Item;
import com.berenberg.InventoryTracker.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    private DataService dataService;

    private List<String> getCurrentInventory() {
        var inventoryData = dataService.getInventoryData();

        // Get the item IDs that are currently on loan
        var loanedItemIds = dataService.getLoanData()
                .stream()
                .map(Loan::getUniqueItemId)
                .collect(Collectors.toList());

        return inventoryData.values()
                .stream()
                .filter(item -> !loanedItemIds.contains(item.getUniqueItemId()))
                .map(Item::getTitle)
                .collect(Collectors.toList());
    }

    private boolean isItemAvailable(String title) {
        //get all the items unique id associated with the title
        // check if the unique ids are in the loans
        var itemIds = dataService.getInventoryData().entrySet()
                .stream()
                .filter(entry -> title.equals(entry.getValue().getTitle()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Check if the unique IDs are in the loans
        var loanedItemIds = dataService.getLoanData()
                .stream()
                .map(Loan::getUniqueItemId)
                .collect(Collectors.toList());

        // Check if any of the item IDs are in the loans
        return itemIds.stream().anyMatch(loanedItemIds::contains);
    }

    public List<String> getOverdueItems() {
        var sevenDaysAgo = LocalDate.now().minusDays(7);

        // Get all the uniqueItemsId that are overdue
        var overdueItemIds = dataService.getLoanData()
                .stream()
                .filter(loan -> loan.getLoanStartDate().isBefore(sevenDaysAgo))
                .map(Loan::getUniqueItemId)
                .collect(Collectors.toList());

        // Look up those IDs in the inventory
        var inventoryData = dataService.getInventoryData();
        return overdueItemIds.stream()
                .map(inventoryData::get)
                .filter(Objects::nonNull)
                .map(Item::getTitle)
                .collect(Collectors.toList());
    }

    public List<Item> getUserItems(Integer userId) {
        //go to loans and get itemid for this user
        //look for the

        var userItemIds = dataService.getLoanData()
                .stream()
                .map(Loan::getUserId)
                .filter(userId::equals)
                .collect(Collectors.toList());

        // Look up those IDs in the inventory
        var inventoryData = dataService.getInventoryData();
        return userItemIds.stream()
                .map(inventoryData::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

}
