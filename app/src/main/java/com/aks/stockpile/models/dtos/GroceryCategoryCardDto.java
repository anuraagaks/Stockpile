package com.aks.stockpile.models.dtos;

import com.aks.stockpile.models.entities.CategoryEntity;

import lombok.Data;

@Data
public class GroceryCategoryCardDto {

    private Integer id;

    private Integer imageResource;

    private String displayName;

    private String categoryName;

    public static GroceryCategoryCardDto of(CategoryEntity entity) {
        GroceryCategoryCardDto dto = new GroceryCategoryCardDto();
        dto.setId(entity.getId());
        dto.setImageResource(entity.getImageResource());
        dto.setDisplayName(entity.getDisplayName());
        dto.setCategoryName(entity.getName());
        return dto;
    }

//    public static List<GroceryCategoryCardDto> ofTest() {
//        GroceryCategoryCardDto dto = new GroceryCategoryCardDto();
//        dto.setImageResource(R.drawable.fruits);
//        dto.setDisplayName("Fruits");
//        dto.setCategoryName("fruits");
//
//        GroceryCategoryCardDto dto1 = new GroceryCategoryCardDto();
//        dto1.setImageResource(R.drawable.vegetables);
//        dto1.setDisplayName("Vegetables");
//        dto1.setCategoryName("vegetables");
//
//        GroceryCategoryCardDto dto2 = new GroceryCategoryCardDto();
//        dto2.setImageResource(R.drawable.grains);
//        dto2.setDisplayName("Grains & Cereals");
//        dto2.setCategoryName("grains");
//
//        GroceryCategoryCardDto dto3 = new GroceryCategoryCardDto();
//        dto3.setImageResource(R.drawable.spices);
//        dto3.setDisplayName("Spices");
//        dto3.setCategoryName("spices");
//
//        GroceryCategoryCardDto dto4 = new GroceryCategoryCardDto();
//        dto4.setImageResource(R.drawable.dairy);
//        dto4.setDisplayName("Dairy");
//        dto4.setCategoryName("dairy");
//
//        GroceryCategoryCardDto dto7 = new GroceryCategoryCardDto();
//        dto7.setImageResource(R.drawable.bakery);
//        dto7.setDisplayName("Bakery Products");
//        dto7.setCategoryName("bakery");
//
//        GroceryCategoryCardDto dto5 = new GroceryCategoryCardDto();
//        dto5.setImageResource(R.drawable.oils);
//        dto5.setDisplayName("Oils");
//        dto5.setCategoryName("oils");
//
//        GroceryCategoryCardDto dto8 = new GroceryCategoryCardDto();
//        dto8.setImageResource(R.drawable.beverages);
//        dto8.setDisplayName("Beverages");
//        dto8.setCategoryName("beverages");
//
//        GroceryCategoryCardDto dto18 = new GroceryCategoryCardDto();
//        dto18.setImageResource(R.drawable.cookies);
//        dto18.setDisplayName("Biscuits & Cookies");
//        dto18.setCategoryName("biscuits_and_cookies");
//
//        GroceryCategoryCardDto dto9 = new GroceryCategoryCardDto();
//        dto9.setImageResource(R.drawable.frozen);
//        dto9.setDisplayName("Frozen Food");
//        dto9.setCategoryName("frozen_food");
//
//        GroceryCategoryCardDto dto10 = new GroceryCategoryCardDto();
//        dto10.setImageResource(R.drawable.personal);
//        dto10.setDisplayName("Health & Personal Care");
//        dto10.setCategoryName("health_and_personal_care");
//
//        GroceryCategoryCardDto dto13 = new GroceryCategoryCardDto();
//        dto13.setImageResource(R.drawable.dry_fruits);
//        dto13.setDisplayName("Dry Fruits");
//        dto13.setCategoryName("dry_fruits");
//
//        GroceryCategoryCardDto dto14 = new GroceryCategoryCardDto();
//        dto14.setImageResource(R.drawable.cleaners);
//        dto14.setDisplayName("Cleaners");
//        dto14.setCategoryName("cleaners");
//
//        GroceryCategoryCardDto dto15 = new GroceryCategoryCardDto();
//        dto15.setImageResource(R.drawable.pickle);
//        dto15.setDisplayName("Packed Food");
//        dto15.setCategoryName("packed_food");
//
//        GroceryCategoryCardDto dto17 = new GroceryCategoryCardDto();
//        dto17.setImageResource(R.drawable.snacks);
//        dto17.setDisplayName("Snacks & Branded Food");
//        dto17.setCategoryName("snacks");
//
//        GroceryCategoryCardDto dto12 = new GroceryCategoryCardDto();
//        dto12.setImageResource(R.drawable.meat);
//        dto12.setDisplayName("Non-Veg");
//        dto12.setCategoryName("non_veg");
//
//        GroceryCategoryCardDto dto6 = new GroceryCategoryCardDto();
//        dto6.setImageResource(R.drawable.stationary);
//        dto6.setDisplayName("Office & Stationary");
//        dto6.setCategoryName("stationary");
//
//        GroceryCategoryCardDto dto16 = new GroceryCategoryCardDto();
//        dto16.setImageResource(R.drawable.others);
//        dto16.setDisplayName("Others");
//        dto16.setCategoryName("others");
//
//        return Arrays.asList(dto, dto1, dto2, dto3, dto4, dto7,
//                dto5, dto8, dto18, dto9, dto10, dto13,
//                dto14, dto15, dto17, dto12, dto6, dto16);
//    }

}
