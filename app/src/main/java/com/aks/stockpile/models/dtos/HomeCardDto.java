package com.aks.stockpile.models.dtos;

import androidx.appcompat.app.AppCompatActivity;

import com.aks.stockpile.R;
import com.aks.stockpile.activities.ErrandsActivity;
import com.aks.stockpile.activities.GroceryActivity;
import com.aks.stockpile.activities.MedicineActivity;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class HomeCardDto {

    private String message;

    private String title;

    private Integer imageResource;

    private Class<? extends AppCompatActivity> onClickActivity;

    public static List<HomeCardDto> ofData() {
        HomeCardDto dto1 = new HomeCardDto();
        dto1.setMessage(" • Manage your grocery and household items.\n • Add/Update your grocery regularly.\n • Manage the expenses for the grocery at monthly/weekly basis.");
        dto1.setTitle("Groceries");
        dto1.setImageResource(R.drawable.grocery);
        dto1.setOnClickActivity(GroceryActivity.class);

        HomeCardDto dto2 = new HomeCardDto();
        dto2.setMessage(" • 3 items Out of Stock.\n • 2 Items need to be Restocked.");
        dto2.setTitle("Medicines");
        dto2.setImageResource(R.drawable.medicine);
        dto2.setOnClickActivity(MedicineActivity.class);

        HomeCardDto dto3 = new HomeCardDto();
        dto3.setMessage(" • You have 4 errands to be completed");
        dto3.setTitle("Errands");
        dto3.setImageResource(R.drawable.errands);
        dto3.setOnClickActivity(ErrandsActivity.class);

        HomeCardDto dto4 = new HomeCardDto();
        dto4.setMessage(" • Check your ToDo list");
        dto4.setTitle("ToDo");
        dto4.setImageResource(R.drawable.todo);
        dto4.setOnClickActivity(ErrandsActivity.class);

        HomeCardDto dto5 = new HomeCardDto();
        dto5.setMessage(" • Add notes for Anything!");
        dto5.setTitle("Notes");
        dto5.setImageResource(R.drawable.notes);
        dto5.setOnClickActivity(ErrandsActivity.class);

        return Arrays.asList(dto1, dto2, dto3, dto4, dto5);
    }
}
