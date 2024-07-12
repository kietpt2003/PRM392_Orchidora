package com.example.prm391_orchidora.Screens.Order;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.Order.ManageOrderDetailAdapter;
import com.example.prm391_orchidora.Controller.PaymentController;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Order.OrderResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ManageOrderDetailScreen extends AppCompatActivity implements PaymentController.CancelPaymentCallBack, PaymentController.GetPaymentByIdCallBack, PaymentController.AcceptPaymentCallBack {

    private static final String TAG = "ManageOrderDetailScreen";

    private TextView textViewName, textViewPhoneNumber, textViewAddress, totalPrice, orderCode, orderTime, paymentTime, orderStatus;
    private RecyclerView orchidRecyclerView;
    private ManageOrderDetailAdapter manageOrderDetailAdapter;
    private ImageView back_button;
    private LinearLayout bottomFixedContent;
    private OrderResponse order;
    private TextView cancelButton, acceptButton;
    private PaymentController paymentController;
    private String token;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_order_detail_layout);

        token = new TokenManager().getToken(this);

        // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar));
        }

        back_button = findViewById(R.id.back_button);
        textViewName = findViewById(R.id.textViewName);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        textViewAddress = findViewById(R.id.textViewAddress);
        orchidRecyclerView = findViewById(R.id.orchidRecyclerView);
        totalPrice = findViewById(R.id.totalPrice);
        orderCode = findViewById(R.id.orderCode);
        orderTime = findViewById(R.id.orderTime);
        paymentTime = findViewById(R.id.paymentTime);
        orderStatus = findViewById(R.id.orderStatus);
        bottomFixedContent = findViewById(R.id.bottom_fixed_content);
        cancelButton = findViewById(R.id.cancel_button);
        acceptButton = findViewById(R.id.accept_button);

        // Retrieve order details from intent
        order = getIntent().getParcelableExtra("orderResponse");
        setUI();

        showHideButton();
        handleClickCancel();
        handleClickAccept();

        handleBack(back_button);
    }

    private void handleClickCancel(){
        cancelButton.setOnClickListener(v->{
            paymentController = new PaymentController((PaymentController.CancelPaymentCallBack) this, token);
            paymentController.fetchCancelOrder(order.getId());
        });
    }

    private void handleClickAccept(){
        acceptButton.setOnClickListener(v->{
            paymentController = new PaymentController((PaymentController.AcceptPaymentCallBack) this, token);
            paymentController.fetchAcceptOrder(order.getId());
        });
    }

    private void showHideButton() {
        if (order.getStatus().equals("CONFIRMING")) {
            bottomFixedContent.setVisibility(View.VISIBLE);
        } else {
            bottomFixedContent.setVisibility(View.GONE);
        }
    }

    public static String convertTimestampToDate(String timestampStr) {
        try {
            // Parse the string to a long
            long timestamp = Long.parseLong(timestampStr);

            // Create a Date object from the timestamp
            Date date = new Date(timestamp);

            // Create a SimpleDateFormat to format the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Format the date into a readable string
            return dateFormat.format(date);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Cannot format";
        }
    }

    public void setUI() {
        if (order != null) {
            try {
                // Set order details to views
                textViewName.setText(order.getAccountName());
                textViewPhoneNumber.setText(order.getPhoneNumber());
                textViewAddress.setText(order.getAddress());
                totalPrice.setText(order.getOrderPayment().getAmount() + " VND");
                orderCode.setText(order.getOrderPayment().getOrderCode() + "");
                orderTime.setText(convertTimestampToDate(order.getCreatedAt()));
                paymentTime.setText(order.getOrderPayment().getPaidOn() == null ? "Not paid yet" : convertTimestampToDate(order.getOrderPayment().getPaidOn()));
                orderStatus.setText(order.getStatus());

                // Set up RecyclerView
                orchidRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                manageOrderDetailAdapter = new ManageOrderDetailAdapter(this, order.getItems());
                orchidRecyclerView.setAdapter(manageOrderDetailAdapter);

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

            } catch (NullPointerException e) {
                Log.e(TAG, "Error accessing order details: " + e.getMessage());
                Toast.makeText(this, "Error accessing order details", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(TAG, "OrderResponse object is null");
            Toast.makeText(this, "Order details not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleBack(ImageView back_button) {
        back_button.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void onCancelPaymentSuccess() {
        bottomFixedContent.setVisibility(View.GONE);
        paymentController = new PaymentController((PaymentController.GetPaymentByIdCallBack) this, token);
        paymentController.fetchOrderById(order.getId());
        Toast.makeText(this, "Reject success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelPaymentError(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetPaymentByIdSuccess(OrderResponse orderResponse) {
        order = orderResponse;
        setUI();
    }

    @Override
    public void onGetPaymentByIdError(ErrorResponse errorResponse) {
        Log.d("GetOrderByIdErr", errorResponse.getMessage());
    }

    @Override
    public void onAcceptPaymentSuccess() {
        bottomFixedContent.setVisibility(View.GONE);
        paymentController = new PaymentController((PaymentController.GetPaymentByIdCallBack) this, token);
        paymentController.fetchOrderById(order.getId());
        Toast.makeText(this, "Reject success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAcceptPaymentError(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
