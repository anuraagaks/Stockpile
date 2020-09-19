package com.aks.stockpile.models.dtos;

import com.aks.stockpile.models.entities.CategoryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GroceryCategoryCardDto extends CardDto {

    public static GroceryCategoryCardDto of(CategoryEntity entity) {
        GroceryCategoryCardDto dto = new GroceryCategoryCardDto();
        dto.setId(entity.getId());
        dto.setImageResourceId(entity.getImageResource());
        dto.setName(entity.getName());
        return dto;
    }

}
