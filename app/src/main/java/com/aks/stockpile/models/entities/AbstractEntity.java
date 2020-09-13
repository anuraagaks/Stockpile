package com.aks.stockpile.models.entities;

import androidx.room.PrimaryKey;

import lombok.Data;

@Data
public class AbstractEntity {

    @PrimaryKey
    private Integer id;

    private String name;

}
