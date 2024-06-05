package com.example.prm391_orchidora.Screens.Transaction;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm391_orchidora.Adapter.TransactionHistory.TransactionHistoryAdapter;
import com.example.prm391_orchidora.Models.TransactionHistory.TransactionHistory;
import com.example.prm391_orchidora.R;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransactionHistoryAdapter adapter;
    private List<TransactionHistory> transactionHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_history_layout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            this.finish();
        });

        // Initialize your transaction history list here
        transactionHistoryList = new ArrayList<>();
        transactionHistoryList.add(new TransactionHistory(R.drawable.home_cart_icon, "GHN 11 22 33", "https://www.thespruce.com/thmb/gA9XUhd0xBF-tLvADZLwYFCg9CU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/flower-orchid-brassavola-535480623-454a77fbd13d41509771c17de4c7bb10.jpg", "Moth Orchid", "Orchidaceae", 2, "$50", R.drawable.transaction_history_local_shipping_icon, "Pending"));
        transactionHistoryList.add(new TransactionHistory(R.drawable.home_cart_icon, "GHTK 995 B", "https://www.thespruce.com/thmb/-_pfiR6xFXDv7A8kB3-bsHiE8Zk=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/CatasetumOrchid-c15ebf0079814aa193f806cf75f84c22.jpg", "Cattleya Orchids", "Cattleya", 5, "$120", R.drawable.transaction_history_local_shipping_icon, "Wait For Pay"));
        transactionHistoryList.add(new TransactionHistory(R.drawable.home_cart_icon, "HNSG 145 21", "https://hips.hearstapps.com/hmg-prod/images/vanda-orchid-types-1587739024.jpg?crop=1.00xw:0.976xh;0,0.0242xh&resize=980:*", "Dendrobium Orchids", "Dendrobium", 1, "$200", R.drawable.transaction_history_local_shipping_icon, "Cancel"));
        transactionHistoryList.add(new TransactionHistory(R.drawable.home_cart_icon, "Store Bailo", "https://hips.hearstapps.com/hmg-prod/images/vanda-orchid-types-1587739024.jpg?crop=1.00xw:0.976xh;0,0.0242xh&resize=980:*", "Dendrobium Orchids", "Dendrobium", 3, "$420", R.drawable.transaction_history_local_shipping_icon, "Success"));
        adapter = new TransactionHistoryAdapter(this, transactionHistoryList);
        recyclerView.setAdapter(adapter);
    }
}
