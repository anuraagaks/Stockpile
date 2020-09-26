package com.aks.stockpile.utils;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.aks.stockpile.R;
import com.aks.stockpile.models.entities.ArticleEntity;
import com.aks.stockpile.models.entities.CategoryEntity;
import com.aks.stockpile.models.enums.QuantityType;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class Utilities {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    private static final Map<Integer, Integer> imageMap;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");

    static {
        imageMap = new HashMap<>();
        imageMap.put(2131165306, R.drawable.fruits);
        imageMap.put(2131165390, R.drawable.vegetables);
        imageMap.put(2131165307, R.drawable.grains);
        imageMap.put(2131165365, R.drawable.spices);
        imageMap.put(2131165296, R.drawable.dairy);
        imageMap.put(2131165279, R.drawable.bakery);
        imageMap.put(2131165354, R.drawable.oils);
        imageMap.put(2131165285, R.drawable.beverages);
        imageMap.put(2131165295, R.drawable.cookies);
        imageMap.put(2131165359, R.drawable.personal);
        imageMap.put(2131165303, R.drawable.dry_fruits);
        imageMap.put(2131165294, R.drawable.cleaners);
        imageMap.put(2131165364, R.drawable.snacks);
        imageMap.put(2131165328, R.drawable.meat);
        imageMap.put(2131165367, R.drawable.stationary);
        imageMap.put(2131165355, R.drawable.others);
    }

    private Utilities() {
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String formatNumber(Double num) {
        return FORMAT.format(num);
    }

    public static QuantityType getQuantityType(CategoryEntity category, ArticleEntity article) {
        if (article != null && article.getQuantityType() != null) {
            return article.getQuantityType();
        }
        return category.getQuantityType();
    }

    public static Snackbar makeSnackbar(Context context, View view, String message) {
        return Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(context, R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(context, R.color.item_on_primary));
    }

    public static Snackbar makeSnackbarInfinite(Context context, View view, String message) {
        return Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(ContextCompat.getColor(context, R.color.colorAccent))
                .setTextColor(ContextCompat.getColor(context, R.color.item_on_primary));
    }

    public static Integer getImageResource(int val) {
        return imageMap.get(val);
    }

}
