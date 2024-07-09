package com.example.prm391_orchidora.Adapter.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Models.Order.OrderItemResponse;
import com.example.prm391_orchidora.R;

import java.util.List;

public class ManageOrderDetailAdapter extends RecyclerView.Adapter<ManageOrderDetailAdapter.OrchidViewHolder> {

    private List<OrderItemResponse> orchidList;
    private Context context;

    public ManageOrderDetailAdapter(Context context, List<OrderItemResponse> items) {
        this.context = context;
        this.orchidList = items;
    }

    @NonNull
    @Override
    public OrchidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_order_detail_item, parent, false);
        return new OrchidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrchidViewHolder holder, int position) {
        OrderItemResponse orchid = orchidList.get(position);

        Glide.with(context)
                .load(orchid.getOrchid().getImg())
                .into(holder.productImageView);

        holder.productNameTextView.setText(orchid.getName());
        holder.productQuantityTextView.setText("x" + orchid.getQuantity());
        holder.productPriceTextView.setText(orchid.getPrice() + " VND");
    }

    @Override
    public int getItemCount() {
        return orchidList.size();
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (OrderItemResponse orchid : orchidList) {
            totalPrice += orchid.getQuantity() * orchid.getPrice();
        }
        return totalPrice;
    }

    static class OrchidViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productQuantityTextView;
        TextView productPriceTextView;

        OrchidViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
        }
    }
}
