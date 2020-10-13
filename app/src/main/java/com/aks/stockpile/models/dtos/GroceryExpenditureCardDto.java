package com.aks.stockpile.models.dtos;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GroceryExpenditureCardDto extends CardDto {

    private Integer monthlyAmount;

    private Integer totalAmount;

    private List<ExpenditureBreakdown> breakdown;

    private boolean isCategory;

}
