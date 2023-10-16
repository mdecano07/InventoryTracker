package com.berenberg.InventoryTracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @NonNull
    private Integer uniqueItemId;
    private int itemId;
    private String type;
    private String title;

}
