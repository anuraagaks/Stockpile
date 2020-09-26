package com.aks.stockpile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aks.stockpile.R;
import com.aks.stockpile.models.dtos.ExpenditureBreakdown;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BreakdownArrayAdapter extends ArrayAdapter<ExpenditureBreakdown> {

    private Context mContext;

    public BreakdownArrayAdapter(Context context, List<ExpenditureBreakdown> breakdowns) {
        super(context, 0, breakdowns);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExpenditureBreakdown breakdown = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.breakdown_layout, parent, false);
        }
        TextView price = convertView.findViewById(R.id.breakdown_price);
        TextView text = convertView.findViewById(R.id.breakdown_text);
        text.setText(String.format("%s %d", breakdown.getMonthName(), breakdown.getYearName()));
        price.setText(String.format("%s %s", mContext.getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("CURRENCY_SYMBOL", "₹"), breakdown.getAmountSpent().toString()));
        return convertView;
    }

}
