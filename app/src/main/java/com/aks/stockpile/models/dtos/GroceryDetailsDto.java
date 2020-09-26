package com.aks.stockpile.models.dtos;

import com.aks.stockpile.models.enums.QuantityType;
import com.aks.stockpile.utils.Utilities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GroceryDetailsDto extends CardDto {

    private String quantityType;
    private String quantityValue;
    private String category;
    private String description;

    public static GroceryDetailsDto of(AggregatedInventory entity) {
        GroceryDetailsDto dto = new GroceryDetailsDto();
        dto.setId(entity.getInventory().getId());
        dto.setCategory(entity.getCategory().getName());
        dto.setName(entity.getInventory().getName());
        QuantityType quantityType = Utilities.getQuantityType(entity.getCategory(), entity.getArticle());
        if (quantityType == null) {
            quantityType = entity.getCategory().getQuantityType();
        }
        dto.setQuantityType(quantityType.getValue());
        dto.setQuantityValue(Utilities.formatNumber(entity.getInventory().getQuantity()));
        dto.setImageResourceId(entity.getCategory().getImageResource());
        dto.setDescription(entity.getInventory().getDescription());
        return dto;
    }
}
