package com.aks.stockpile.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.activities.UpsertInventoryActivity;
import com.aks.stockpile.models.dtos.ShoppingDetailDto;
import com.aks.stockpile.services.IShoppingRefresher;
import com.aks.stockpile.utils.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_INVENTORY_ID;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_NAME;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_IS_CONSUME;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_IS_UPDATE;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    private Context mContext;
    private List<ShoppingDetailDto> data;

    public ShoppingAdapter(Context mContext, List<ShoppingDetailDto> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ViewHolder(inflater.inflate(R.layout.shopping_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getName());
        holder.category.setText(data.get(position).getCategoryName());
        holder.quantity.setText(String.format("%s %s", Utilities.formatNumber(data.get(position).getQuantity()), data.get(position).getQuantityType()));
        holder.image.setImageResource(data.get(position).getImageResourceId());
        holder.source.setText(data.get(position).getSource());
        holder.purchased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upsertIntent = new Intent(mContext, UpsertInventoryActivity.class);
                upsertIntent.putExtra(GROCERY_UPSERT_IS_UPDATE, true);
                upsertIntent.putExtra(GROCERY_UPSERT_IS_CONSUME, false);
                upsertIntent.putExtra(GROCERY_NAME, data.get(position).getName());
                upsertIntent.putExtra(GROCERY_INVENTORY_ID, data.get(position).getId());
                ((Activity) mContext).startActivityForResult(upsertIntent, 112);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(mContext)
                        .setTitle("Are you sure?")
                        .setPositiveButton(R.string.positive_untrack, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteFromCart(data.get(position).getId());
                            }
                        })
                        .setNegativeButton(R.string.negetive_untrack, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setMessage(String.format(Locale.ENGLISH, "Are you sure to remove %s" +
                                " from cart?", holder.name.getText()))
                        .setCancelable(false)
                        .show();
            }
        });
    }

    private void deleteFromCart(Integer id) {
        SharedPreferences sp = mContext.getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        Set<String> ids = new HashSet<>(sp.getStringSet("Shopping_cart", new HashSet<String>()));
        SharedPreferences.Editor editor = sp.edit();
        ids.remove(id.toString());
        editor.putStringSet("Shopping_cart", ids);
        editor.apply();
        ((IShoppingRefresher) mContext).refreshRecycler();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView category, name, quantity, source;
        ImageView image;
        MaterialButton purchased, delete;
        MaterialCardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shopping_name);
            category = itemView.findViewById(R.id.shopping_category);
            quantity = itemView.findViewById(R.id.shopping_quantity);
            source = itemView.findViewById(R.id.shopping_source);
            image = itemView.findViewById(R.id.shopping_image);
            purchased = itemView.findViewById(R.id.shopping_purchased);
            delete = itemView.findViewById(R.id.shopping_delete);
            cardView = itemView.findViewById(R.id.shopping_main_card);
        }

        @Override
        public void onClick(View view) {
        }
    }

}
