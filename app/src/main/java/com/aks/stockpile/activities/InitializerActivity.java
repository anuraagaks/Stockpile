package com.aks.stockpile.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aks.stockpile.R;
import com.aks.stockpile.models.PreDataDto;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.services.impl.StockpileDaoServiceImpl;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InitializerActivity extends AppCompatActivity {

    private StockpileDaoService daoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFields();
        setLayoutFields();
        addPreDataInDb();
        updateFirstLaunchAndExit();
    }

    private void initializeFields() {
        daoService = new StockpileDaoServiceImpl(this);
    }

    private void updateFirstLaunchAndExit() {
        SharedPreferences.Editor editor = getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit();
        editor.putString("CURRENCY_SYMBOL", "₹");
        editor.putBoolean("IS_FIRST_LAUNCH", false);
        editor.apply();
        finish();
    }

    private void setLayoutFields() {
        setContentView(R.layout.activity_initializer);
    }

    private void addPreDataInDb() {
        try {
            InputStream stream = getAssets().open("predata.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            PreDataDto preDataDto = new Gson().fromJson(reader.readLine(), PreDataDto.class);
            daoService.addCategoryArticle(preDataDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}