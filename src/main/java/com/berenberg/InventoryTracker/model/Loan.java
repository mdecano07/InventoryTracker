package com.berenberg.InventoryTracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @NonNull
    private Integer userId;
    @NonNull
    private Integer uniqueItemId;
    private LocalDate loanStartDate;

}