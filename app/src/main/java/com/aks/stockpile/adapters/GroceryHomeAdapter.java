package com.aks.stockpile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aks.stockpile.R;
import com.aks.stockpile.models.GroceryHomeCardDto;

import java.util.List;

public class GroceryHomeAdapter extends RecyclerView.Adapter<GroceryHomeAdapter.ViewHolder> {

    private Context mContext;
    private List<GroceryHomeCardDto> data;

    public GroceryHomeAdapter(Context mContext, List<GroceryHomeCardDto> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ViewHolder(inflater.inflate(R.layout.grocery_home_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.subCategory.setText(data.get(position).getSubCategory());
        holder.image.setImageResource(data.get(position).getImageResource());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, subCategory;
        ImageView image;
        Button update, delete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.grocery_home_name);
            subCategory = itemView.findViewById(R.id.grocery_home_sub_cat);
            update = itemView.findViewById(R.id.grocery_home_update);
            delete = itemView.findViewById(R.id.grocery_home_untrack);
            image = itemView.findViewById(R.id.grocery_home_image);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == update.getId()) {
                Toast.makeText(mContext, "Updating Value", Toast.LENGTH_SHORT).show();
            }
            if (id == delete.getId()) {
                Toast.makeText(mContext, "Deleting Value", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
