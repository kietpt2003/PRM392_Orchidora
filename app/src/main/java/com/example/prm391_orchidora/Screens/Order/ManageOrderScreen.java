package com.example.prm391_orchidora.Screens.Order;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.Order.ManageOrderAdapter;
import com.example.prm391_orchidora.Models.Order.ManageOrder;
import com.example.prm391_orchidora.R;
import java.util.ArrayList;
import java.util.List;

public class ManageOrderScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ManageOrderAdapter adapter;
    private List<ManageOrder> manageOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_order_layout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(view -> {
            this.finish();
        });

        // Initialize your transaction history list here
        manageOrderList = new ArrayList<>();
        manageOrderList.add(new ManageOrder(R.drawable.home_cart_icon, "GHN 11 22 33", "https://www.thespruce.com/thmb/gA9XUhd0xBF-tLvADZLwYFCg9CU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/flower-orchid-brassavola-535480623-454a77fbd13d41509771c17de4c7bb10.jpg", "Moth Orchid", "Orchidaceae", 2, "50 VND", R.drawable.transaction_history_local_shipping_icon, "Pending"));
        manageOrderList.add(new ManageOrder(R.drawable.home_cart_icon, "HNSG 145 21", "https://hips.hearstapps.com/hmg-prod/images/vanda-orchid-types-1587739024.jpg?crop=1.00xw:0.976xh;0,0.0242xh&resize=980:*", "Dendrobium Orchids", "Dendrobium", 1, "200 VND", R.drawable.transaction_history_local_shipping_icon, "Cancel"));
        adapter = new ManageOrderAdapter(this, manageOrderList);
        recyclerView.setAdapter(adapter);
    }
}
