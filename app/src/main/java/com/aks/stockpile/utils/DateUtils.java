package com.aks.stockpile.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");

    private DateUtils() {
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

}
