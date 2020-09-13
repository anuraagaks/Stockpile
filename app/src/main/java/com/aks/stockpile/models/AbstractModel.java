package com.aks.stockpile.models;

import com.aks.stockpile.models.dtos.CardDto;

import lombok.Data;

@Data
public class AbstractModel {

    private Integer id;

    private String name;

    public AbstractModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static AbstractModel of(CardDto dto) {
        return new AbstractModel(dto.getId(), dto.getName());
    }

}
