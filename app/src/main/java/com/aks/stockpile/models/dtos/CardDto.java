package com.aks.stockpile.models.dtos;

import com.aks.stockpile.dao.ImageResourceResolver;

import lombok.Data;

@Data
public class CardDto {

    protected Integer id;

    protected String name;

    protected Integer imageResourceId;

    public Integer getImageResourceId() {
        return ImageResourceResolver.getImageResource(imageResourceId);
    }
}
