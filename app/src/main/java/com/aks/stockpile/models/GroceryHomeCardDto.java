package com.aks.stockpile.models;

import com.aks.stockpile.R;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class GroceryHomeCardDto {

    private Integer imageResource;

    private String subCategory;

    private String name;

    public static List<GroceryHomeCardDto> ofTest() {
        GroceryHomeCardDto dto = new GroceryHomeCardDto();
        dto.setImageResource(R.drawable.tomatoes);
        dto.setSubCategory("Fruits & Vegetables");
        dto.setName("Tomatoes");
        GroceryHomeCardDto dto1 = new GroceryHomeCardDto();
        dto1.setImageResource(R.drawable.onions);
        dto1.setSubCategory("Fruits & Vegetables");
        dto1.setName("Onions");
        GroceryHomeCardDto dto2 = new GroceryHomeCardDto();
        dto2.setImageResource(R.drawable.tomatoes);
        dto2.setSubCategory("Fruits & Vegetables");
        dto2.setName("Tomatoes");
        GroceryHomeCardDto dto3 = new GroceryHomeCardDto();
        dto3.setImageResource(R.drawable.onions);
        dto3.setSubCategory("Fruits & Vegetables");
        dto3.setName("Onions");
        return Arrays.asList(dto, dto1, dto2, dto3);
    }

}
