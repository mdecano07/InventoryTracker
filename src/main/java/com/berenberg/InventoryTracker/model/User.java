package com.berenberg.InventoryTracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NonNull
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
}
