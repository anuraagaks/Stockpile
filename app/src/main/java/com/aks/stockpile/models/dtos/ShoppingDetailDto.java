package com.aks.stockpile.models.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShoppingDetailDto extends CardDto {

    private Integer categoryId;

    private String categoryName;

    private Double quantity;

    private String quantityType;

    private String source;

}
