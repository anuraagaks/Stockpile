package com.aks.stockpile.dao;

import com.aks.stockpile.R;

import java.util.HashMap;
import java.util.Map;

public class ImageResourceResolver {

    private static final Map<Integer, Integer> imageMap;

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

    public static Integer getImageResource(int val) {
        return imageMap.get(val);
    }

}
