package com.example.prm391_orchidora.Adapter.OrderDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Models.Orchid.OrderDetailItem;
import com.example.prm391_orchidora.R;

import java.util.List;

public class OrchidAdapter extends RecyclerView.Adapter<OrchidAdapter.OrchidViewHolder> {

    private List<OrderDetailItem> orchidList;
    private Context context;

    public OrchidAdapter(Context context, List<OrderDetailItem> orchidList) {
        this.context = context;
        this.orchidList = orchidList;
    }

    @NonNull
    @Override
    public OrchidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new OrchidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrchidViewHolder holder, int position) {
        OrderDetailItem orchid = orchidList.get(position);

        Glide.with(context)
                .load(orchid.getImg())
                .into(holder.orchidImageView);

        holder.orchidNameTextView.setText(orchid.getName());
        holder.orchidCategoryTextView.setText(orchid.getCategory());
        holder.orchidQuantityTextView.setText("x" + orchid.getQuantity());
        holder.orchidPriceTextView.setText((orchid.getQuantity() * orchid.getPrice())+" VND");
    }

    @Override
    public int getItemCount() {
        return orchidList.size();
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (OrderDetailItem orchid : orchidList) {
            totalPrice += orchid.getQuantity() * orchid.getPrice();
        }
        return totalPrice;
    }


    static class OrchidViewHolder extends RecyclerView.ViewHolder {
        ImageView orchidImageView;
        TextView orchidNameTextView;
        TextView orchidCategoryTextView;
        TextView orchidQuantityTextView;
        TextView orchidPriceTextView;

        OrchidViewHolder(@NonNull View itemView) {
            super(itemView);
            orchidImageView = itemView.findViewById(R.id.orchidImageView);
            orchidNameTextView = itemView.findViewById(R.id.orchidNameTextView);
            orchidCategoryTextView = itemView.findViewById(R.id.orchidCategoryTextView);
            orchidQuantityTextView = itemView.findViewById(R.id.orchidQuantityTextView);
            orchidPriceTextView = itemView.findViewById(R.id.orchidPriceTextView);
        }
    }
}