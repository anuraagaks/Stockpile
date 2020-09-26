package com.aks.stockpile.models.dtos;

import lombok.Data;

@Data
public class ExpenditureBreakdown {

    private String monthName;

    private Integer yearName;

    private Integer amountSpent;

}
