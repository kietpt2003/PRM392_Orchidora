package com.example.prm391_orchidora.Screens.Order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.Order.ManageOrderAdapter;
import com.example.prm391_orchidora.Controller.ManageOrderController;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Order.OrderResponse;
import com.example.prm391_orchidora.Models.TransactionHistory.TransactionHistoryResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Utils.TokenManager;

public class ManageOrderScreen extends AppCompatActivity implements ManageOrderController.ManageOrderCallback,
        ManageOrderAdapter.OnOrderClickListener {

    private ManageOrderController manageOrderController;
    private RecyclerView recyclerView;
    private ManageOrderAdapter adapter;
    private ImageView backBtn;

    private TextView confirmBtn, cancelBtn, successBtn;
    private String currentStatus = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_order_layout);

        String token = new TokenManager().getToken(this);
        manageOrderController = new ManageOrderController(this, token);

        // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar));
        }

        backBtn = findViewById(R.id.backBtn);
        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        confirmBtn = findViewById(R.id.confirmBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        successBtn = findViewById(R.id.successBtn);

        // Set up controller and initial data retrieval

        confirmBtn.setOnClickListener(v -> handleStatusFilter("CONFIRMING"));
        cancelBtn.setOnClickListener(v -> handleStatusFilter("CANCELLED"));
        successBtn.setOnClickListener(v -> handleStatusFilter("SUCCESSFUL"));

        handleBack(backBtn);

    }

    @Override
    protected void onResume() {
        super.onResume();
        manageOrderController.getAllOrders("");
    }
    private void handleBack( ImageView backBtn) {
        backBtn.setOnClickListener(v->{
            finish();
        });
    }
    private void handleStatusFilter(String status) {
        if (status.equals(currentStatus)) {
            currentStatus = "";
            resetButtonStyles();
            manageOrderController.getAllOrders("");
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
            manageOrderController.getAllOrders(status);
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
    public void onOrderClick(OrderResponse order) {
        Intent intent = new Intent(this, ManageOrderDetailScreen.class);
        intent.putExtra("orderResponse", order);
        startActivity(intent);
    }

    @Override
    public void onManageOrderSuccess(TransactionHistoryResponse transactionHistoryResponse) {
        // Initialize adapter with retrieved data
        adapter = new ManageOrderAdapter(transactionHistoryResponse.getData(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onManageOrderError(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
