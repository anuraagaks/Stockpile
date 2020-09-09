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
import com.aks.stockpile.models.dtos.HomeCardDto;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {

    private Context mContext;
    private List<HomeCardDto> data;

    public HomePageAdapter(Context mContext, List<HomeCardDto> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public HomePageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ViewHolder(inflater.inflate(R.layout.home_card_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final HomePageAdapter.ViewHolder holder, final int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.message.setText(data.get(position).getMessage());
        holder.image.setImageResource(data.get(position).getImageResource());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, data.get(position).getOnClickActivity());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, message;
        ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.home_card_title);
            message = itemView.findViewById(R.id.home_card_message);
            image = itemView.findViewById(R.id.home_card_image);
        }
    }
}