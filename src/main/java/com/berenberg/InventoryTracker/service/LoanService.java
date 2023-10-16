package com.berenberg.InventoryTracker.service;

import com.berenberg.InventoryTracker.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    DataService dataService;

    public void borrowItem(List<Integer> uniqueItemIds, Integer userId) {
        var loanStartDate = LocalDate.now();

        var loansList = uniqueItemIds.stream()
                .map(uniqueItemId -> new Loan(userId, uniqueItemId, loanStartDate))
                .collect(Collectors.toList());

        dataService.addLoanData(loansList);
    }

    public void returnItem(List<Integer> uniqueItemIds, Integer userId) {
        // use the item id and user id to look up
        var loanStartDate = LocalDate.now();

        var loansList = uniqueItemIds.stream()
                .map(uniqueItemId -> new Loan(userId, uniqueItemId, loanStartDate))
                .collect(Collectors.toList());

        dataService.removeLoanData(loansList);
    }
}
