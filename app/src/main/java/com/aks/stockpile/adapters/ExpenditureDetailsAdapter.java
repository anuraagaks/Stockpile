package com.aks.stockpile.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.models.dtos.ExpenditureBreakdown;
import com.aks.stockpile.models.dtos.GroceryExpenditureCardDto;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ExpenditureDetailsAdapter extends RecyclerView.Adapter<ExpenditureDetailsAdapter.ViewHolder> {

    private Context mContext;
    private List<GroceryExpenditureCardDto> data;

    public ExpenditureDetailsAdapter(Context mContext, List<GroceryExpenditureCardDto> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ExpenditureDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ExpenditureDetailsAdapter.ViewHolder(inflater.inflate(R.layout.grocery_expenditure_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenditureDetailsAdapter.ViewHolder holder, final int position) {
        GroceryExpenditureCardDto cardDto = data.get(position);
        String symbol = mContext.getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("CURRENCY_SYMBOL", "₹");
        holder.weekly.setText(String.format("Weekly Average: %s %d", symbol, cardDto.getWeeklyAmount()));
        holder.monthly.setText(String.format("Monthly average: %s %d", symbol, cardDto.getMonthlyAmount()));
        holder.total.setText(String.format("Total Spent: %s %d", symbol, cardDto.getTotalAmount()));
        holder.name.setText(cardDto.getName());
        holder.image.setImageResource(cardDto.getImageResourceId());
        holder.breakdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBreakdown(data.get(position).getBreakdown());
            }
        });

    }

    private void showBreakdown(List<ExpenditureBreakdown> breakdown) {
        new MaterialAlertDialogBuilder(mContext)
                .setTitle("Monthly expenditure")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setAdapter(new BreakdownArrayAdapter(mContext, breakdown), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView name, weekly, monthly, total;
        MaterialButton breakdown;
        ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.expenditure_rv_title);
            weekly = itemView.findViewById(R.id.expenditure_rv_week);
            monthly = itemView.findViewById(R.id.expenditure_rv_month);
            total = itemView.findViewById(R.id.expenditure_rv_total);
            breakdown = itemView.findViewById(R.id.expenditure_rv_breakdown_btn);
            image = itemView.findViewById(R.id.expenditure_image);
        }
    }
}
