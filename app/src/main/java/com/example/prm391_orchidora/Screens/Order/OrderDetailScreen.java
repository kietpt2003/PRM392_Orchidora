package com.example.prm391_orchidora.Screens.Order;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.OrderDetail.OrchidAdapter;
import com.example.prm391_orchidora.Models.Orchid.OrderDetailItem;
import com.example.prm391_orchidora.R;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailScreen extends AppCompatActivity {

    private TextView tvName, tvPhone, tvAddress, tvTotalPrice, tvOrderCode, tvOrderTime, tvPaymentTime, tvOrderStatus;

    private RecyclerView orchidRecyclerView;
    private OrchidAdapter orchidAdapter;
    private List<OrderDetailItem> orchidList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.order_detail_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar ));
        }

        tvName = findViewById(R.id.textViewName);
        tvPhone = findViewById(R.id.textViewPhoneNumber);
        tvAddress = findViewById(R.id.textViewAddress);

        tvName.setText("Phạm Tuấn Kiệt");
        tvPhone.setText("(+84) 388 415 317");
        tvAddress.setText("Park View Residence, Số 152, Đường Điện Biên Phủ, Căn Hộ 100, TP. Hồ Chí Minh");

        orchidRecyclerView = findViewById(R.id.orchidRecyclerView);
        orchidRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        orchidList = new ArrayList<>();
        // Add sample data
        orchidList.add(new OrderDetailItem("https://www.thespruce.com/thmb/gA9XUhd0xBF-tLvADZLwYFCg9CU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/flower-orchid-brassavola-535480623-454a77fbd13d41509771c17de4c7bb10.jpg", "Moth Orchid", "Orchidaceae", 2, 19.99));
        orchidList.add(new OrderDetailItem("https://www.thespruce.com/thmb/-_pfiR6xFXDv7A8kB3-bsHiE8Zk=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/CatasetumOrchid-c15ebf0079814aa193f806cf75f84c22.jpg", "Cattleya Orchids", "Cattleya", 1, 24.99));
        orchidList.add(new OrderDetailItem("https://www.thespruce.com/thmb/gA9XUhd0xBF-tLvADZLwYFCg9CU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/flower-orchid-brassavola-535480623-454a77fbd13d41509771c17de4c7bb10.jpg", "Moth Orchid", "Orchidaceae", 2, 19.99));


        orchidList.add(new OrderDetailItem("https://www.thespruce.com/thmb/-_pfiR6xFXDv7A8kB3-bsHiE8Zk=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/CatasetumOrchid-c15ebf0079814aa193f806cf75f84c22.jpg", "Cattleya Orchids", "Cattleya", 1, 24.99));
        orchidList.add(new OrderDetailItem("https://www.thespruce.com/thmb/gA9XUhd0xBF-tLvADZLwYFCg9CU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/flower-orchid-brassavola-535480623-454a77fbd13d41509771c17de4c7bb10.jpg", "Moth Orchid", "Orchidaceae", 2, 19.99));

        orchidList.add(new OrderDetailItem("https://www.thespruce.com/thmb/-_pfiR6xFXDv7A8kB3-bsHiE8Zk=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/CatasetumOrchid-c15ebf0079814aa193f806cf75f84c22.jpg", "Cattleya Orchids", "Cattleya", 1, 24.99));


        orchidAdapter = new OrchidAdapter(this, orchidList);
        orchidRecyclerView.setAdapter(orchidAdapter);

        tvTotalPrice = findViewById(R.id.totalPrice);
        tvTotalPrice.setText(orchidAdapter.getTotalPrice()+ " VND");

        tvOrderCode = findViewById(R.id.orderCode);
        tvOrderTime = findViewById(R.id.orderTime);
        tvOrderStatus = findViewById(R.id.orderStatus);
        tvPaymentTime = findViewById(R.id.paymentTime);

        tvOrderCode.setText("240508RMCVN");
        tvOrderTime.setText("08-05-2024 12:49");
        tvPaymentTime.setText("08-05-2024 12:55");
        tvOrderStatus.setText("PENDING");
        tvOrderStatus.setTextColor(Color.parseColor("#DCD173"));

    }
}
