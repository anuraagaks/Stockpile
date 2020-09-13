package com.aks.stockpile.models.dtos;

import android.os.Parcel;
import android.os.Parcelable;

import com.aks.stockpile.models.entities.QuantitySplitUp;
import com.aks.stockpile.models.enums.QuantityType;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GroceryDetailsDto extends CardDto implements Parcelable {

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
    private String quantityType;
    private Double quantityValue;
    private String category;
    private List<QuantitySplitUp> quantitySplitUps;

    protected GroceryDetailsDto(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        category = in.readString();
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

    public static GroceryDetailsDto of(AggregatedInventory entity) {
        GroceryDetailsDto dto = new GroceryDetailsDto();
        dto.setId(entity.getInventory().getId());
        dto.setCategory(entity.getCategory().getName());
        dto.setName(entity.getArticle().getName());
        QuantityType quantityType = entity.getArticle().getQuantityType();
        if (quantityType == null) {
            quantityType = entity.getCategory().getQuantityType();
        }
        dto.setQuantityType(quantityType.getValue());
        dto.setQuantityValue(entity.getInventory().getQuantity());
        dto.setImageResourceId(entity.getCategory().getImageResource());
        dto.setQuantitySplitUps(entity.getInventory().getSplitUp());
        return dto;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(category);
        dest.writeString(name);
        dest.writeString(quantityType);
        if (quantityValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(quantityValue);
        }
        if (imageResourceId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imageResourceId);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
