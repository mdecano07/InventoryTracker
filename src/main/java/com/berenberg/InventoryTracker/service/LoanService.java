package com.berenberg.InventoryTracker.service;

import com.berenberg.InventoryTracker.model.Loan;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoanService {
    @Autowired
    DataService dataService;

    //method to allow a user to borrow multiple items
    public void borrowItems(Set<Integer> uniqueItemIds, Integer userId) {
        final var loanStartDate = LocalDate.now();

        final var loansList = uniqueItemIds.stream()
                .map(uniqueItemId -> new Loan(userId, uniqueItemId, loanStartDate))
                .collect(Collectors.toList());

        dataService.addLoanData(loansList);
    }

    public void returnItems(Set<Integer> uniqueItemIds, Integer userId) {
        //get the existing loans for user and uniqueItemIds and remove them
        final var loansToRemove = dataService.getLoanData().stream()
                .filter(loan -> userId.equals(loan.getUserId()) && uniqueItemIds.contains(loan.getUniqueItemId()))
                .collect(Collectors.toList());

        dataService.removeLoanData(loansToRemove);
    }
}
