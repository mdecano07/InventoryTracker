package com.berenberg.InventoryTracker.service;

import com.berenberg.InventoryTracker.model.Item;
import com.berenberg.InventoryTracker.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    private DataService dataService;

    private List<String> getCurrentInventory() {
        final var inventoryData = dataService.getInventoryData();

        //get the uniqueItemIds that are currently on loan
        final var loanedItemIds = dataService.getLoanData()
                .stream()
                .map(Loan::getUniqueItemId)
                .collect(Collectors.toList());

        //return the items in the inventory data where the uniqueItemId is not in the loan data
        return inventoryData.values()
                .stream()
                .filter(item -> !loanedItemIds.contains(item.getUniqueItemId()))
                .map(Item::getTitle)
                .collect(Collectors.toList());
    }

    private boolean isTitleAvailable(String title) {
        //get the title's uniqueItemIds in the inventory
        final var inventoryIds = dataService.getInventoryData().values()
                .stream()
                .filter(item -> title.equals(item.getTitle()))
                .map(Item::getUniqueItemId)
                .collect(Collectors.toSet());

        //get the uniqueItemIds in the loans
        final var loanedIds = dataService.getLoanData()
                .stream()
                .map(Loan::getUniqueItemId)
                .collect(Collectors.toSet());

        //count uniqueItemIds in the inventory are in the loans
        final double countOfUniqueItemsInLoans = inventoryIds.stream()
                .filter(loanedIds::contains)
                .count();

        //the number of ids in loan data should always be less than the inventory for an available item
        return countOfUniqueItemsInLoans < inventoryIds.size();
    }

    public List<String> getOverdueItems() {
        final var sevenDaysAgo = LocalDate.now().minusDays(7);

        //get the uniqueItemIds that are overdue
        final var overdueItemIds = dataService.getLoanData()
                .stream()
                .filter(loan -> loan.getLoanStartDate().isBefore(sevenDaysAgo))
                .map(Loan::getUniqueItemId)
                .collect(Collectors.toList());

        //look up those uniqueItemIds in the inventory
        final var inventoryData = dataService.getInventoryData();
        return overdueItemIds.stream()
                .map(inventoryData::get)
                .filter(Objects::nonNull)
                .map(Item::getTitle)
                .collect(Collectors.toList());
    }

    public List<Item> getUserItems(Integer userId) {
        //get loan data for user
        final var loanDataForUser = dataService.getLoanData()
                .stream()
                .filter(loan -> userId.equals(loan.getUserId()))
                .collect(Collectors.toList());

        //get uniqueItemIds for the user
        final var userUniqueItemIds = loanDataForUser
                .stream()
                .map(Loan::getUniqueItemId)
                .collect(Collectors.toList());

        //look up items associated with the uniqueItemIds
        final var inventoryData = dataService.getInventoryData();
        return userUniqueItemIds.stream()
                .map(inventoryData::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
