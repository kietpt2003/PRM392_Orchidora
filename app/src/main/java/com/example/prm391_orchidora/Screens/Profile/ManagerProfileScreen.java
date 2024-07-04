package com.example.prm391_orchidora.Screens.Profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.prm391_orchidora.Controller.ProfileController;
import com.example.prm391_orchidora.Models.Account.AccountResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Auth.LoginScreen;
import com.example.prm391_orchidora.Screens.Category.ManageCategoryScreen;
import com.example.prm391_orchidora.Utils.TokenManager;

public class ManagerProfileScreen extends AppCompatActivity implements ProfileController.ProfileCallback {
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhoneNumber;
    private TextView tvAddress;
    private ProfileController profileController;
    private String token;
    private LinearLayout manageCategorySection;
    private LinearLayout logOut;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.manager_profile_layout);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvAddress = findViewById(R.id.tvAddress);
        // Manager Category Screen
        manageCategorySection = findViewById(R.id.manageCategorySection);
        manageCategorySection.setOnClickListener(v -> {
           Intent intent = new Intent(ManagerProfileScreen.this, ManageCategoryScreen.class); // Replace with your target activity
            startActivity(intent);
        });
        // Logout
        logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(v -> {
            Intent intent = new Intent(ManagerProfileScreen.this, LoginScreen.class); // Replace with your target activity
            startActivity(intent);
        });
        // Load the avatar image using Glide
        ImageView imageView = findViewById(R.id.avatar_manager_image);
        Glide.with(this).load("https://st3.depositphotos.com/3431221/13621/v/450/depositphotos_136216036-stock-illustration-man-avatar-icon-hipster-character.jpg").into(imageView);

        token = new TokenManager().getToken(this);
        profileController = new ProfileController(this, token);

        profileController.getProfile();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
        Toast.makeText(this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
