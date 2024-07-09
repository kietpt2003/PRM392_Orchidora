package com.example.prm391_orchidora.Adapter.Order;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Models.Order.OrderResponse;
import com.example.prm391_orchidora.R;

import java.util.List;

public class ManageOrderAdapter extends RecyclerView.Adapter<ManageOrderAdapter.ViewHolder> {
    private List<OrderResponse> orders;
    private OnOrderClickListener onOrderClickListener; // Interface for click handling

    public ManageOrderAdapter(List<OrderResponse> orders, OnOrderClickListener onOrderClickListener) {
        this.orders = orders;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_order_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderResponse order = orders.get(position);

        holder.orchidNameTextView.setText(order.getItems().get(0).getName());
        holder.orchidQuantityTextView.setText("x" + order.getItems().get(0).getQuantity());
        holder.orchidPriceTextView.setText("Total: " + order.getOrderPayment().getAmount() + " VND");
        holder.orchidPriceTextView.setTextColor(Color.parseColor("#BFA4DC"));
        holder.statusTextView.setText(order.getStatus());

        if (position == orders.size() - 1) {
            holder.separatorLayout.setVisibility(View.GONE);
        } else {
            holder.separatorLayout.setVisibility(View.VISIBLE);
        }

        int statusTextColor;
        switch (order.getStatus()) {
            case "CONFIRMING":
                statusTextColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorConfirming);
                break;
            case "CANCELLED":
                statusTextColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorCancelled);
                break;
            case "SUCCESSFUL":
                statusTextColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorSuccessful);
                break;
            default:
                statusTextColor = Color.parseColor("#000000");
                break;
        }
        holder.statusTextView.setTextColor(statusTextColor);

        Glide.with(holder.itemView.getContext())
                .load(order.getItems().get(0).getOrchid().getImg())
                .into(holder.orchidImageView);

        int statusIconTintColor;
        switch (order.getStatus()) {
            case "CONFIRMING":
                statusIconTintColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorConfirming);
                break;
            case "CANCELLED":
                statusIconTintColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorCancelled);
                break;
            case "SUCCESSFUL":
                statusIconTintColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorSuccessful);
                break;
            default:
                statusIconTintColor = Color.parseColor("#000000");
                break;
        }
        holder.statusIconImageView.setColorFilter(statusIconTintColor);

        // Handle click event
        holder.itemView.setOnClickListener(view -> {
            if (onOrderClickListener != null) {
                onOrderClickListener.onOrderClick(order); // Pass clicked order to listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView orchidImageView;
        private TextView orchidNameTextView;
        private TextView orchidQuantityTextView;
        private TextView orchidPriceTextView;
        private TextView statusTextView;
        private ImageView statusIconImageView;
        private LinearLayout separatorLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orchidImageView = itemView.findViewById(R.id.orchidImageView);
            orchidNameTextView = itemView.findViewById(R.id.orchidNameTextView);
            orchidQuantityTextView = itemView.findViewById(R.id.orchidQuantityTextView);
            orchidPriceTextView = itemView.findViewById(R.id.orchidPriceTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            statusIconImageView = itemView.findViewById(R.id.statusIconImageView);
            separatorLayout = itemView.findViewById(R.id.separatorLayout);
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(OrderResponse order); // Method to handle click events
    }
}
