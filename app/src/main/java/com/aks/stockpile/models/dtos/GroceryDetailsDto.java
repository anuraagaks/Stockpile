package com.aks.stockpile.models.dtos;

import android.os.Parcel;
import android.os.Parcelable;

import com.aks.stockpile.models.entities.InventoryHistory;
import com.aks.stockpile.models.enums.QuantityType;
import com.aks.stockpile.utils.InventoryUtils;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GroceryDetailsDto extends CardDto implements Parcelable {

    private String quantityType;
    private Double quantityValue;
    private String category;
    private List<InventoryHistory> histories;
    private String description;

    protected GroceryDetailsDto(Parcel in) {
        quantityType = in.readString();
        if (in.readByte() == 0) {
            quantityValue = null;
        } else {
            quantityValue = in.readDouble();
        }
        category = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantityType);
        if (quantityValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(quantityValue);
        }
        dest.writeString(category);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public static GroceryDetailsDto of(AggregatedInventory entity) {
        GroceryDetailsDto dto = new GroceryDetailsDto();
        dto.setId(entity.getInventory().getId());
        dto.setCategory(entity.getCategory().getName());
        dto.setName(entity.getInventory().getName());
        QuantityType quantityType = InventoryUtils.getQuantityType(entity.getCategory(), entity.getArticle());
        if (quantityType == null) {
            quantityType = entity.getCategory().getQuantityType();
        }
        dto.setQuantityType(quantityType.getValue());
        dto.setQuantityValue(entity.getInventory().getQuantity());
        dto.setImageResourceId(entity.getCategory().getImageResource());
        dto.setHistories(entity.getInventory().getHistory());
        dto.setDescription(entity.getInventory().getDescription());
        return dto;
    }
}
