package com.aks.stockpile.models.dtos;

import com.aks.stockpile.models.enums.QuantityType;

import lombok.Data;

@Data
public class InventoryDto {

    private Integer id;
    private Integer categoryId;
    private Integer articleId;
    private String name;
    private QuantityType quantityType;
    private String quantityTypeName;
    private Double quantity;
    private String description;
    private String source;
    private Double price;

}
