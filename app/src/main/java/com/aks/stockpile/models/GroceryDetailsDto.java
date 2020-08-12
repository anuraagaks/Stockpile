package com.aks.stockpile.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.aks.stockpile.R;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroceryDetailsDto implements Parcelable {

    public static final Creator<GroceryDetailsDto> CREATOR = new Creator<GroceryDetailsDto>() {
        @Override
        public GroceryDetailsDto createFromParcel(Parcel in) {
            return new GroceryDetailsDto(in);
        }

        @Override
        public GroceryDetailsDto[] newArray(int size) {
            return new GroceryDetailsDto[size];
        }
    };
    private String id;
    private String subCategory;
    private String name;
    private String quantityType;
    private Double quantityValue;
    private Integer imageResourceId;

    protected GroceryDetailsDto(Parcel in) {
        id = in.readString();
        subCategory = in.readString();
        name = in.readString();
        quantityType = in.readString();
        if (in.readByte() == 0) {
            quantityValue = null;
        } else {
            quantityValue = in.readDouble();
        }
        if (in.readByte() == 0) {
            imageResourceId = null;
        } else {
            imageResourceId = in.readInt();
        }
    }

    public static ArrayList<GroceryDetailsDto> ofTestData() {
        GroceryDetailsDto dto = new GroceryDetailsDto();
        dto.setId("r134134134");
        dto.setSubCategory("Fruits & Vegetables");
        dto.setName("Onions");
        dto.setQuantityType("Kgs");
        dto.setQuantityValue(2.5);
        dto.setImageResourceId(R.drawable.onions);

        GroceryDetailsDto dto1 = new GroceryDetailsDto();
        dto1.setId("fg143f24f");
        dto1.setSubCategory("Fruits & Vegetables");
        dto1.setName("Tomatoes");
        dto1.setQuantityType("Kgs");
        dto1.setQuantityValue(1.0);
        dto1.setImageResourceId(R.drawable.tomatoes);

        GroceryDetailsDto dto2 = new GroceryDetailsDto();
        dto2.setId("r134134134");
        dto2.setSubCategory("Grains");
        dto2.setName("Rice");
        dto2.setQuantityType("Kgs");
        dto2.setQuantityValue(10.0);
        dto2.setImageResourceId(R.drawable.rice);

        GroceryDetailsDto dto3 = new GroceryDetailsDto();
        dto3.setId("r134134134");
        dto3.setSubCategory("Fruits & Vegetables");
        dto3.setName("Onions");
        dto3.setQuantityType("Kgs");
        dto3.setQuantityValue(2.5);
        dto3.setImageResourceId(R.drawable.onions);

        GroceryDetailsDto dto4 = new GroceryDetailsDto();
        dto4.setId("fg143f24f");
        dto4.setSubCategory("Fruits & Vegetables");
        dto4.setName("Tomatoes");
        dto4.setQuantityType("Kgs");
        dto4.setQuantityValue(1.0);
        dto4.setImageResourceId(R.drawable.tomatoes);

        GroceryDetailsDto dto5 = new GroceryDetailsDto();
        dto5.setId("r134134134");
        dto5.setSubCategory("Grains");
        dto5.setName("Rice");
        dto5.setQuantityType("Kgs");
        dto5.setQuantityValue(10.0);
        dto5.setImageResourceId(R.drawable.rice);

        GroceryDetailsDto dto6 = new GroceryDetailsDto();
        dto6.setId("r134134134");
        dto6.setSubCategory("Fruits & Vegetables");
        dto6.setName("Onions");
        dto6.setQuantityType("Kgs");
        dto6.setQuantityValue(2.5);
        dto6.setImageResourceId(R.drawable.onions);

        GroceryDetailsDto dto7 = new GroceryDetailsDto();
        dto7.setId("r134134134");
        dto7.setSubCategory("Fruits & Vegetables");
        dto7.setName("Onions");
        dto7.setQuantityType("Kgs");
        dto7.setQuantityValue(2.5);
        dto7.setImageResourceId(R.drawable.onions);

        return new ArrayList<>(Arrays.asList(dto, dto1, dto2, dto3, dto4, dto5, dto6, dto7));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(subCategory);
        parcel.writeString(name);
        parcel.writeString(quantityType);
        if (quantityValue == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(quantityValue);
        }
        if (imageResourceId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(imageResourceId);
        }
    }
}
