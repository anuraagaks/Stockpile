package com.aks.stockpile.utils;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.aks.stockpile.R;
import com.google.android.material.snackbar.Snackbar;

public final class LayoutUtils {

    public static Snackbar makeSnackbar(Context context, View view, String message) {
        return Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(context, R.color.colorPrimary))
                .setTextColor(ContextCompat.getColor(context, R.color.primary_text));
    }

}
