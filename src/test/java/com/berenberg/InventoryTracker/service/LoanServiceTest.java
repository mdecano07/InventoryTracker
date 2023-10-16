package com.berenberg.InventoryTracker.service;

import com.berenberg.InventoryTracker.model.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class LoanServiceTest {
    private LoanService sut;
    @Mock
    private DataService dataService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        sut = new LoanService(dataService);
    }

    @Test
    public void testReturnItems() {
        // GIVEN
        var userId = 1;
        var uniqueItemIds = new HashSet<Integer>();
        uniqueItemIds.add(1);
        uniqueItemIds.add(2);

        var loanToReturn = List.of(new Loan(userId, 1, LocalDate.now()));

        // WHEN
        Mockito.when(dataService.getLoanData()).thenReturn(loanToReturn);
        sut.returnItems(uniqueItemIds, userId);

        // THEN
        // Verify that the DataService's removeLoanData was called with the loan to return
        Mockito.verify(dataService).removeLoanData(loanToReturn);
        //TODO Check if loan data size is reduced by 1
    }

}
