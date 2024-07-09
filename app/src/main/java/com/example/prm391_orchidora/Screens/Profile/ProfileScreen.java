package com.example.prm391_orchidora.Screens.Profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Controller.ProfileController;
import com.example.prm391_orchidora.Models.Account.AccountResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Auth.LoginScreen;
import com.example.prm391_orchidora.Screens.Category.ManageCategoryScreen;
import com.example.prm391_orchidora.Screens.Transaction.TransactionHistoryScreen;
import com.example.prm391_orchidora.Services.CartService;
import com.example.prm391_orchidora.Utils.Database;
import com.example.prm391_orchidora.Utils.TokenManager;

public class ProfileScreen extends AppCompatActivity implements ProfileController.ProfileGetCallback {
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhoneNumber;
    private TextView tvAddress;
    private ProfileController profileController;
    private LinearLayout transactionHistorySection;
    private String token;
    private LinearLayout logOut;
    private FrameLayout cartBtn;
    private Database db;
    private TextView tvCartCount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_layout);

        token = new TokenManager().getToken(this);
        handleDB();

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);
        cartBtn = findViewById(R.id.cartBtn);
        tvCartCount = findViewById(R.id.tvCartCount);

        // Manager Order Screen
        transactionHistorySection = findViewById(R.id.transactionHistorySection);
        transactionHistorySection.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileScreen.this, TransactionHistoryScreen.class); // Replace with your target activity
            startActivity(intent);
        });

        // Logout and clearToken
        logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(v -> {
            TokenManager tokenManager = new TokenManager();
            tokenManager.clearToken(ProfileScreen.this);
            Intent intent = new Intent(ProfileScreen.this, LoginScreen.class); // Replace with your target activity
            startActivity(intent);
        });
        // Load the avatar image using Glide
        ImageView imageView = findViewById(R.id.avatar_image);
        Glide.with(this).load("https://st3.depositphotos.com/3431221/13621/v/450/depositphotos_136216036-stock-illustration-man-avatar-icon-hipster-character.jpg").into(imageView);

        profileController = new ProfileController(this, token);

        profileController.getProfile();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleGetCartNumber();
    }

    private void handleGetCartNumber(){
        CartService cartService = new CartService(db, token);
        tvCartCount = findViewById(R.id.tvCartCount);
        cartService.getCarts(cartItems -> {
            if (cartItems.isEmpty()) {
                tvCartCount.setText(0 + "");
            } else {
                tvCartCount.setText(cartItems.size() + "");
            }
        });
    }

    private void handleDB(){
        db = new Database(this, "OrchidList.sqlite", null, 1);
        // Tạo bảng OrchidList nếu chưa tồn tại
        db.queryData("CREATE TABLE IF NOT EXISTS OrchidList(id NVARCHAR(200), quantity INTEGER, isSelected INTERGER)");
    }

    @Override
    public void onProfileSuccess(AccountResponse accountResponse) {
        tvName.setText(accountResponse.getName());
        tvEmail.setText(accountResponse.getEmail());
        tvPhoneNumber.setText(accountResponse.getPhoneNumber());
        tvAddress.setText(accountResponse.getAddress());
    }

    @Override
    public void onProfileError(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
