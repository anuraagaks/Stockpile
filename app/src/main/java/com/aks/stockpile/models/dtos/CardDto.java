package com.aks.stockpile.models.dtos;

import com.aks.stockpile.utils.Utilities;

import lombok.Data;

@Data
public class CardDto {

    protected Integer id;

    protected String name;

    protected Integer imageResourceId;

    public Integer getImageResourceId() {
        return Utilities.getImageResource(imageResourceId);
    }
}
