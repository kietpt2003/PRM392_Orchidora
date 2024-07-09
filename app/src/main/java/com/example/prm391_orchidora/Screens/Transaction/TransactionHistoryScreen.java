package com.example.prm391_orchidora.Screens.Transaction;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.TransactionHistory.TransactionHistoryAdapter;
import com.example.prm391_orchidora.Controller.TransactionHistoryController;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.TransactionHistory.TransactionHistoryResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Utils.TokenManager;

public class TransactionHistoryScreen extends AppCompatActivity implements TransactionHistoryController.TransactionHistoryCallback {
    private TransactionHistoryController transactionHistoryController;
    private RecyclerView recyclerView;
    private TransactionHistoryAdapter adapter;

    private TextView confirmBtn, cancelBtn, successBtn;
    private String currentStatus = "";
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_history_layout);

        token = new TokenManager().getToken(this);

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> this.finish());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        confirmBtn = findViewById(R.id.confirmBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        successBtn = findViewById(R.id.successBtn);

        String token = new TokenManager().getToken(this);
        transactionHistoryController = new TransactionHistoryController(this, token);

        confirmBtn.setOnClickListener(v -> handleStatusFilter("CONFIRMING"));
        cancelBtn.setOnClickListener(v -> handleStatusFilter("CANCELLED"));
        successBtn.setOnClickListener(v -> handleStatusFilter("SUCCESSFUL"));

        transactionHistoryController.getTransactionHistory("");
    }

    private void handleStatusFilter(String status) {
        if (status.equals(currentStatus)) {
            currentStatus = "";
            resetButtonStyles();
            transactionHistoryController.getTransactionHistory("");
        } else {
            currentStatus = status;
            resetButtonStyles();
            switch (status) {
                case "CONFIRMING":
                    setSelectedStyle(confirmBtn);
                    break;
                case "CANCELLED":
                    setSelectedStyle(cancelBtn);
                    break;
                case "SUCCESSFUL":
                    setSelectedStyle(successBtn);
                    break;
            }
            transactionHistoryController.getTransactionHistory(status);
        }
    }

    private void resetButtonStyles() {
        setDefaultStyle(confirmBtn);
        setDefaultStyle(cancelBtn);
        setDefaultStyle(successBtn);
    }

    private void setDefaultStyle(TextView button) {
        button.setBackground(ContextCompat.getDrawable(this, R.drawable.transaction_history_select_status_background));
        button.setTextColor(Color.parseColor("#BFA4DC"));
    }

    private void setSelectedStyle(TextView button) {
        button.setBackgroundColor(Color.parseColor("#BFA4DC"));
        button.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onTransactionHistorySuccess(TransactionHistoryResponse transactionHistoryResponse) {
        adapter = new TransactionHistoryAdapter(transactionHistoryResponse.getData(), this, token);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTransactionHistoryError(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
