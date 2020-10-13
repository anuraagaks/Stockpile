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
import com.aks.stockpile.models.dtos.AggregatedExpenditure;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.aks.stockpile.services.IGroceryRefresher;
import com.aks.stockpile.services.StockpileDaoService;
import com.aks.stockpile.utils.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_INVENTORY_ID;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_NAME;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_AVAILABLE_QUANTITY;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_AVAILABLE_QUANTITY_TYPE;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_IS_CONSUME;
import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_UPSERT_IS_UPDATE;

public class GroceryDetailsAdapter extends RecyclerView.Adapter<GroceryDetailsAdapter.ViewHolder> {

    private Context mContext;
    private List<GroceryDetailsDto> data;
    private StockpileDaoService daoService;

    public GroceryDetailsAdapter(Context mContext, List<GroceryDetailsDto> data,
                                 StockpileDaoService daoService) {
        this.mContext = mContext;
        this.data = data;
        this.daoService = daoService;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ViewHolder(inflater.inflate(R.layout.grocery_details_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getName());
        holder.subCategory.setText(data.get(position).getCategory());
        if (data.get(position).getDescription() != null)
            holder.description.setText(data.get(position).getDescription());
        else
            holder.description.setVisibility(View.GONE);
        holder.quantity.setText(String.format("%s %s", Utilities.formatNumber(data.get(position).getQuantityValue()), data.get(position).getQuantityType()));
        holder.image.setImageResource(data.get(position).getImageResourceId());
        holder.untrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(mContext)
                        .setTitle(R.string.confirm_untrack)
                        .setPositiveButton(R.string.positive_untrack, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                daoService.deleteInventory(data.get(position).getId());
                                ((IGroceryRefresher) mContext).refreshRecycler();
                            }
                        })
                        .setNegativeButton(R.string.negetive_untrack, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setMessage(String.format(Locale.ENGLISH, "Are you sure you want to untrack %s?" +
                                "\nYou can add this to your inventory later.", holder.name.getText()))
                        .setCancelable(false)
                        .show();
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
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
        holder.consume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent upsertIntent = new Intent(mContext, UpsertInventoryActivity.class);
                upsertIntent.putExtra(GROCERY_UPSERT_IS_UPDATE, false);
                upsertIntent.putExtra(GROCERY_UPSERT_IS_CONSUME, true);
                upsertIntent.putExtra(GROCERY_NAME, data.get(position).getName());
                upsertIntent.putExtra(GROCERY_UPSERT_AVAILABLE_QUANTITY, data.get(position).getQuantityValue());
                upsertIntent.putExtra(GROCERY_UPSERT_AVAILABLE_QUANTITY_TYPE, data.get(position).getQuantityType());
                upsertIntent.putExtra(GROCERY_INVENTORY_ID, data.get(position).getId());
                ((Activity) mContext).startActivityForResult(upsertIntent, 112);
            }
        });
        holder.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHistory(data.get(position).getName(), data.get(position).getId());
            }
        });
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = mContext.getSharedPreferences("PREFERENCE", MODE_PRIVATE);
                Set<String> ids = new HashSet<>(sp.getStringSet("Shopping_cart", new HashSet<String>()));
                SharedPreferences.Editor editor = sp.edit();
                ids.add(data.get(position).getId().toString());
                editor.putStringSet("Shopping_cart", ids);
                editor.apply();
                Utilities.makeSnackbar(mContext, holder.cardView, data.get(position).getName() + " added to cart.", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void showHistory(String name, Integer inventoryId) {
        List<AggregatedExpenditure> expenditures = daoService.getExpenditureByInventoryId(inventoryId);
        new MaterialAlertDialogBuilder(mContext)
                .setTitle("Purchase and Consume history for " + name)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setAdapter(new HistoryArrayAdapter(mContext, expenditures, false), new DialogInterface.OnClickListener() {
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

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, subCategory, quantity, description;
        ImageView image;
        MaterialButton update, untrack, addToCart, history, consume;
        MaterialCardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.grocery_details_name);
            description = itemView.findViewById(R.id.grocery_details_description);
            subCategory = itemView.findViewById(R.id.grocery_details_sub_cat);
            quantity = itemView.findViewById(R.id.grocery_details_quantity_value);
            update = itemView.findViewById(R.id.grocery_details_update);
            consume = itemView.findViewById(R.id.grocery_details_consume);
            history = itemView.findViewById(R.id.grocery_details_history_btn);
            untrack = itemView.findViewById(R.id.grocery_details_untrack);
            addToCart = itemView.findViewById(R.id.grocery_details_add_to_cart);
            image = itemView.findViewById(R.id.grocery_details_image);
            cardView = itemView.findViewById(R.id.grocery_details_main_card);
        }

        @Override
        public void onClick(View view) {
        }
    }

}
