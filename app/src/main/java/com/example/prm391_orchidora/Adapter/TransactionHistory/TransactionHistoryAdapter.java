package com.example.prm391_orchidora.Adapter.TransactionHistory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.TextAppearanceInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Controller.PaymentController;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Order.OrderResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Orchid.OrchidDetailScreen;
import com.example.prm391_orchidora.Screens.Order.OrderDetailScreen;

import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> implements PaymentController.GetPaymentByIdCallBack {
    private List<OrderResponse> transactions;
    private static Context transactionHistoryContext;
    private PaymentController paymentController;
    private String token;

    public TransactionHistoryAdapter(List<OrderResponse> transactions, Context transactionHistoryContext, String token) {
        this.transactions = transactions;
        this.transactionHistoryContext = transactionHistoryContext;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderResponse transaction = transactions.get(position);

        holder.productNameTextView.setText(transaction.getItems().get(0).getName());
        holder.productQuantityTextView.setText("x" + transaction.getItems().get(0).getQuantity());
        holder.totalTextView.setText("Total: " + transaction.getOrderPayment().getAmount()+ "VND");
        holder.totalTextView.setTextColor(Color.parseColor("#BFA4DC"));
        holder.statusTextView.setText(transaction.getStatus());
        holder.detailBtn.setOnClickListener(v->{
            paymentController = new PaymentController(this, token);
            paymentController.fetchOrderById(transaction.getId());
        });

        if (position == transactions.size() - 1) {
            holder.separatorLayout.setVisibility(View.GONE);
        } else {
            holder.separatorLayout.setVisibility(View.VISIBLE);
        }


        int statusTextColor;
        switch (transaction.getStatus()) {
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
                .load(transaction.getItems().get(0).getOrchid().getImg())
                .into(holder.productImageView);

        int statusIconTintColor;
        switch (transaction.getStatus()) {
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
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    @Override
    public void onGetPaymentByIdSuccess(OrderResponse orderResponse) {
        Intent intent = new Intent(transactionHistoryContext, OrderDetailScreen.class);
        intent.putExtra("orderResponse", orderResponse);
        transactionHistoryContext.startActivity(intent);
    }

    @Override
    public void onGetPaymentByIdError(ErrorResponse errorResponse) {
        Toast.makeText(transactionHistoryContext, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView detailBtn;
        private ImageView productImageView;
        private TextView productNameTextView;
        private TextView productQuantityTextView;
        private TextView totalTextView;
        private TextView statusTextView;
        private ImageView statusIconImageView;
        private LinearLayout separatorLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            statusIconImageView = itemView.findViewById(R.id.statusIconImageView);
            separatorLayout = itemView.findViewById(R.id.separatorLayout);
            detailBtn = itemView.findViewById(R.id.detailBtn);
        }
    }
}
