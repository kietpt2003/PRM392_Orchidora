package com.example.prm391_orchidora.Screens.Transaction;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_history_layout);

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            this.finish();
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String token = new TokenManager().getToken(this);
        transactionHistoryController = new TransactionHistoryController(this, token);

        transactionHistoryController.getTransactionHistory("");
    }

    @Override
    public void onTransactionHistorySuccess(TransactionHistoryResponse transactionHistoryResponse) {
        adapter = new TransactionHistoryAdapter(transactionHistoryResponse.getData());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTransactionHistoryError(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
