package com.example.prm391_orchidora.Screens.Order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.Order.OrchidAdapter;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.Models.Orchid.OrderDetailItem;
import com.example.prm391_orchidora.Models.Order.OrderItemResponse;
import com.example.prm391_orchidora.Models.Order.OrderResponse;
import com.example.prm391_orchidora.R;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailScreen extends AppCompatActivity {

    private TextView tvName, tvPhone, tvAddress, tvTotalPrice, tvOrderCode, tvOrderTime, tvPaymentTime, tvOrderStatus;

    private RecyclerView orchidRecyclerView;
    private OrchidAdapter orchidAdapter;
    private List<OrchidResponse> orchidList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.order_detail_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar));
        }
        Intent intent = getIntent();
        OrderResponse orderResponse = intent.getParcelableExtra("orderResponse");

        tvName = findViewById(R.id.textViewName);
        tvPhone = findViewById(R.id.textViewPhoneNumber);
        tvAddress = findViewById(R.id.textViewAddress);

        tvName.setText(orderResponse.getAccountName());
        tvPhone.setText(orderResponse.getPhoneNumber());
        tvAddress.setText(orderResponse.getAddress());

        orchidRecyclerView = findViewById(R.id.orchidRecyclerView);
        orchidRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        orchidList = new ArrayList<>();
        for (int i = 0; i < orderResponse.getItems().size(); i++) {
            orchidList.add(new OrchidResponse(
                    orderResponse.getItems().get(i).getOrchid().getId(),
                    orderResponse.getItems().get(i).getPrice(),
                    orderResponse.getItems().get(i).getOrchid().getCategory(),
                    orderResponse.getItems().get(i).getOrchid().getColor(),
                    orderResponse.getItems().get(i).getOrchid().getDescription(),
                    orderResponse.getItems().get(i).getOrchid().getImg(),
                    orderResponse.getItems().get(i).getQuantity(),
                    orderResponse.getItems().get(i).getOrchid().getStatus(),
                    orderResponse.getItems().get(i).getName()
            ));
        }


        orchidAdapter = new OrchidAdapter(this, orchidList);
        orchidRecyclerView.setAdapter(orchidAdapter);

        tvTotalPrice = findViewById(R.id.totalPrice);
        tvTotalPrice.setText(orchidAdapter.getTotalPrice() + " VND");

        tvOrderCode = findViewById(R.id.orderCode);
        tvOrderTime = findViewById(R.id.orderTime);
        tvOrderStatus = findViewById(R.id.orderStatus);
        tvPaymentTime = findViewById(R.id.paymentTime);giit

        tvOrderCode.setText(orderResponse.getOrderPayment().getOrderCode()+"");
        tvOrderTime.setText(orderResponse.getCreatedAt());
        tvPaymentTime.setText(orderResponse.getOrderPayment().getPaidOn() == null? "Not paid yet":orderResponse.getOrderPayment().getPaidOn());
        tvOrderStatus.setText(orderResponse.getStatus());
        tvOrderStatus.setTextColor(Color.parseColor("#DCD173"));

    }
}
