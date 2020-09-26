package com.aks.stockpile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aks.stockpile.R;
import com.aks.stockpile.models.dtos.AggregatedExpenditure;
import com.aks.stockpile.utils.Utilities;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HistoryArrayAdapter extends ArrayAdapter<AggregatedExpenditure> {

    public static final Integer ADD_IMAGE = R.drawable.add_inventroy;
    public static final Integer REDUCE_IMAGE = R.drawable.consumes;
    private Context mContext;
    private List<AggregatedExpenditure> histories;

    public HistoryArrayAdapter(Context context, List<AggregatedExpenditure> histories) {
        super(context, 0, histories);
        this.mContext = context;
        this.histories = histories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AggregatedExpenditure expenditure = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_layout, parent, false);
        }
        ImageView icon = convertView.findViewById(R.id.history_icon);
        TextView timestamp = convertView.findViewById(R.id.history_timestamp);
        TextView text = convertView.findViewById(R.id.history_text);
        String endText = null;
        switch (expenditure.getExpenditure().getUpdateType()) {
            case ADDED:
                icon.setImageResource(ADD_IMAGE);
                endText = String.format("(from %s)\n\t\tPrice: %s%s", expenditure.getExpenditure().getSource(),
                        mContext.getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("CURRENCY_SYMBOL", "₹"),
                        expenditure.getExpenditure().getPrice());
                break;
            case CONSUMED:
                icon.setImageResource(REDUCE_IMAGE);
        }
        timestamp.setText(Utilities.formatDate(expenditure.getExpenditure().getEntryDate()));
        StringBuilder finalText = new StringBuilder();
        finalText.append(expenditure.getExpenditure().getQuantity()).append(expenditure.getExpenditure().getQuantityType());
        if (endText != null) {
            finalText.append(" ").append(endText);
        }
        text.setText(finalText.toString());
        return convertView;
    }

}
