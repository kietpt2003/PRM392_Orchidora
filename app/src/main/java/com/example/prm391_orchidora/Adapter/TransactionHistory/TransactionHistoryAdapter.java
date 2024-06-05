package com.example.prm391_orchidora.Adapter.TransactionHistory;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Models.TransactionHistory.TransactionHistory;
import com.example.prm391_orchidora.R;
import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {
    private Context context;
    private List<TransactionHistory> transactionHistoryList;

    public TransactionHistoryAdapter(Context context, List<TransactionHistory> transactionHistoryList) {
        this.context = context;
        this.transactionHistoryList = transactionHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionHistory transactionHistory = transactionHistoryList.get(position);

        holder.storeIconImageView.setImageResource(transactionHistory.getStoreIcon());
        holder.storeNameTextView.setText(transactionHistory.getStoreId());
        Glide.with(context)
                .load(transactionHistory.getImageUrl())
                .into(holder.productImageView);
        holder.productNameTextView.setText(transactionHistory.getOrchidName());
        holder.productDescriptionTextView.setText(transactionHistory.getOrchidCategory());
        holder.productQuantityTextView.setText("x" + String.valueOf(transactionHistory.getOrchidQuantity()));
        String totalText = "Total: $" + transactionHistory.getTotal();
        holder.totalTextView.setText(totalText);
        SpannableString spannableTotal = new SpannableString(totalText);
        spannableTotal.setSpan(new ForegroundColorSpan(Color.parseColor("#8E8E8E")), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableTotal.setSpan(new ForegroundColorSpan(Color.parseColor("#BFA4DC")), 7, totalText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.totalTextView.setText(spannableTotal);
        holder.statusTextTextView.setText(transactionHistory.getStatusText());
        int statusTextColor;
        switch (transactionHistory.getStatusText()) {
            case "Pending":
                statusTextColor = Color.parseColor("#FFC700");
                break;
            case "Wait For Pay":
                statusTextColor = Color.parseColor("#FF8A00");
                break;
            case "Cancel":
                statusTextColor = Color.parseColor("#CD2121");
                break;
            case "Success":
                statusTextColor = Color.parseColor("#599832");
                break;
            default:
                statusTextColor = Color.parseColor("#000000");
                break;
        }
        // Set text color for status text view
        holder.statusTextTextView.setTextColor(statusTextColor);

        // Set color filter for status icon image view
        holder.statusIconImageView.setColorFilter(statusTextColor);
    }

    @Override
    public int getItemCount() {
        return transactionHistoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView storeIconImageView;
        TextView storeNameTextView;
        ImageView productImageView;
        TextView productNameTextView;
        TextView productDescriptionTextView;
        TextView productQuantityTextView;
        TextView totalTextView;
        ImageView statusIconImageView;
        TextView statusTextTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeIconImageView = itemView.findViewById(R.id.storeIconImageView);
            storeNameTextView = itemView.findViewById(R.id.storeNameTextView);
            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productDescriptionTextView = itemView.findViewById(R.id.productDescriptionTextView);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            statusIconImageView = itemView.findViewById(R.id.statusIconImageView);
            statusTextTextView = itemView.findViewById(R.id.statusTextTextView);
        }
    }
}