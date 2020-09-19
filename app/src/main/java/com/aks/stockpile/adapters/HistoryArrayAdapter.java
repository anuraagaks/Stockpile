package com.aks.stockpile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aks.stockpile.R;
import com.aks.stockpile.models.entities.InventoryHistory;
import com.aks.stockpile.utils.DateUtils;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HistoryArrayAdapter extends ArrayAdapter<InventoryHistory> {

    public static final Integer ADD_IMAGE = R.drawable.add_inventroy;
    public static final Integer REDUCE_IMAGE = R.drawable.consumes;
    private Context mContext;

    List<InventoryHistory> histories;

    public HistoryArrayAdapter(Context context, List<InventoryHistory> histories) {
        super(context, 0, histories);
        this.mContext = context;
        this.histories = histories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InventoryHistory history = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_layout, parent, false);
        }
        ImageView icon = convertView.findViewById(R.id.history_icon);
        TextView timestamp = convertView.findViewById(R.id.history_timestamp);
        TextView text = convertView.findViewById(R.id.history_text);
        String endText = null;
        switch (history.getUpdateType()) {
            case ADDED:
                icon.setImageResource(ADD_IMAGE);
                endText = String.format("(from %s)\n\t\tPrice: %s%s", history.getGrocerySource(),
                        mContext.getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("CURRENCY_SYMBOL", "₹"),
                        history.getPrice().intValue());
                break;
            case CONSUMED:
                icon.setImageResource(REDUCE_IMAGE);
        }
        timestamp.setText(DateUtils.formatDate(history.getUpdatedAt()));
        StringBuilder finalText = new StringBuilder();
        finalText.append(history.getQuantity()).append(history.getQuantityType());
        if (endText != null) {
            finalText.append(" ").append(endText);
        }
        text.setText(finalText.toString());
        return convertView;
    }

}
