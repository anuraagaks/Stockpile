package com.aks.stockpile.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.activities.GroceryCategoryActivity;
import com.aks.stockpile.models.dtos.GroceryCategoryCardDto;

import java.util.List;

import static com.aks.stockpile.constants.IntentExtrasConstants.GROCERY_CATEGORY_ACTIVITY_CATEGORY;

public class GroceryCategoryAdapter extends RecyclerView.Adapter<GroceryCategoryAdapter.ViewHolder> {

    private Context mContext;
    private List<GroceryCategoryCardDto> data;

    public GroceryCategoryAdapter(Context mContext, List<GroceryCategoryCardDto> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ViewHolder(inflater.inflate(R.layout.grocery_category_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getName());
        holder.image.setImageResource(data.get(position).getImageResourceId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(mContext, GroceryCategoryActivity.class);
                categoryIntent.putExtra(GROCERY_CATEGORY_ACTIVITY_CATEGORY, data.get(position).getId());
                mContext.startActivity(categoryIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.grocery_home_name);
            image = itemView.findViewById(R.id.grocery_home_image);
        }
    }

}
