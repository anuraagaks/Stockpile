package com.aks.stockpile.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.aks.stockpile.R;
import com.aks.stockpile.activities.UpsertInventoryActivity;
import com.aks.stockpile.models.dtos.GroceryDetailsDto;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Locale;

public class GroceryDetailsAdapter extends RecyclerView.Adapter<GroceryDetailsAdapter.ViewHolder> {

    private Context mContext;
    private List<GroceryDetailsDto> data;

    public GroceryDetailsAdapter(Context mContext, List<GroceryDetailsDto> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ViewHolder(inflater.inflate(R.layout.grocery_details_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.subCategory.setText(data.get(position).getCategory());
        holder.quantity.setText(String.format("%s %s", data.get(position).getQuantityValue(), data.get(position).getQuantityType()));
        holder.image.setImageResource(data.get(position).getImageResourceId());
        holder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.expandableLayout.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.expandableLayout.setVisibility(View.VISIBLE);
                    holder.divider.setVisibility(View.VISIBLE);
                    holder.expand.setIconResource(R.drawable.outline_expand_less_white_18dp);
                } else {
                    TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                    holder.divider.setVisibility(View.GONE);
                    holder.expandableLayout.setVisibility(View.GONE);
                    holder.expand.setIconResource(R.drawable.outline_expand_more_white_18dp);
                }
            }
        });
        holder.untrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(mContext)
                        .setTitle(R.string.confirm_untrack)
                        .setPositiveButton(R.string.positive_untrack, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
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
                upsertIntent.putExtra("IS_UPDATE_REQUEST", false);
                mContext.startActivity(upsertIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, subCategory, quantity;
        ImageView image;
        MaterialButton update, untrack, addToCart, expand;
        LinearLayout expandableLayout;
        MaterialCardView cardView;
        View divider;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.grocery_details_name);
            subCategory = itemView.findViewById(R.id.grocery_details_sub_cat);
            quantity = itemView.findViewById(R.id.grocery_details_quantity_value);
            update = itemView.findViewById(R.id.grocery_details_update);
            untrack = itemView.findViewById(R.id.grocery_details_untrack);
            addToCart = itemView.findViewById(R.id.grocery_details_add_to_cart);
            expand = itemView.findViewById(R.id.grocery_details_expand_btn);
            image = itemView.findViewById(R.id.grocery_details_image);
            expandableLayout = itemView.findViewById(R.id.grocery_details_expandable_layout);
            cardView = itemView.findViewById(R.id.grocery_details_main_card);
            divider = itemView.findViewById(R.id.grocery_details_divider);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == update.getId()) {
                Toast.makeText(mContext, "Updating Value", Toast.LENGTH_SHORT).show();
            }
            if (id == untrack.getId()) {
                Toast.makeText(mContext, "Deleting Value", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
