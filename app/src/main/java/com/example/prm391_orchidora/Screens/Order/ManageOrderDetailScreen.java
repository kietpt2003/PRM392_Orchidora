package com.example.prm391_orchidora.Screens.Order;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.Order.ManageOrderDetailAdapter;
import com.example.prm391_orchidora.Models.ManageOrderDetail.ManageOrderDetail;
import com.example.prm391_orchidora.Models.Order.OrderResponse;
import com.example.prm391_orchidora.R;

import java.util.ArrayList;
import java.util.List;

public class ManageOrderDetailScreen extends AppCompatActivity {

    private static final String TAG = "ManageOrderDetailScreen";

    private TextView textViewName, textViewPhoneNumber, textViewAddress, totalPrice, orderCode, orderTime, paymentTime, orderStatus;

    private RecyclerView orchidRecyclerView;
    private ManageOrderDetailAdapter manageOrderDetailAdapter;
    private List<ManageOrderDetail> orchidList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_order_detail_layout);

        // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar));
        }

        // Initialize views
        textViewName = findViewById(R.id.textViewName);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        textViewAddress = findViewById(R.id.textViewAddress);
        orchidRecyclerView = findViewById(R.id.orchidRecyclerView);
        totalPrice = findViewById(R.id.totalPrice);
        orderCode = findViewById(R.id.orderCode);
        orderTime = findViewById(R.id.orderTime);
        paymentTime = findViewById(R.id.paymentTime);
        orderStatus = findViewById(R.id.orderStatus);

        // Retrieve order details from intent
        OrderResponse order = getIntent().getParcelableExtra("orderResponse");
        if (order != null) {
            try {
                // Set order details to views
                textViewName.setText(order.getAccountName());
                textViewPhoneNumber.setText(order.getPhoneNumber());
                textViewAddress.setText(order.getAddress());
                totalPrice.setText(order.getOrderPayment().getAmount() + " VND");
                orderCode.setText(order.getOrderPayment().getOrderCode()+"");
                orderTime.setText(order.getCreatedAt());
                paymentTime.setText(order.getOrderPayment().getPaidOn());
                orderStatus.setText(order.getStatus());

                // Set status text color based on status
                int statusTextColor;
                switch (order.getStatus()) {
                    case "CONFIRMING":
                        statusTextColor = ContextCompat.getColor(this, R.color.colorConfirming);
                        break;
                    case "CANCELLED":
                        statusTextColor = ContextCompat.getColor(this, R.color.colorCancelled);
                        break;
                    case "SUCCESSFUL":
                        statusTextColor = ContextCompat.getColor(this, R.color.colorSuccessful);
                        break;
                    default:
                        statusTextColor = Color.parseColor("#000000");
                        break;
                }
                orderStatus.setTextColor(statusTextColor);

                // Populate orchid list if applicable
                // orchidList.addAll(order.getItems());
                // manageOrderDetailAdapter.notifyDataSetChanged();
            } catch (NullPointerException e) {
                Log.e(TAG, "Error accessing order details: " + e.getMessage());
                Toast.makeText(this, "Error accessing order details", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(TAG, "OrderResponse object is null");
            Toast.makeText(this, "Order details not found", Toast.LENGTH_SHORT).show();
        }
    }
}
