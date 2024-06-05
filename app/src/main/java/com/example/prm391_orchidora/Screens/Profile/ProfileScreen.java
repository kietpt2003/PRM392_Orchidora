package com.example.prm391_orchidora.Screens.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Transaction.TransactionHistoryScreen;

public class ProfileScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_layout);

        ImageView imageView = findViewById(R.id.avatar_image);
        Glide.with(this).load("https://st3.depositphotos.com/3431221/13621/v/450/depositphotos_136216036-stock-illustration-man-avatar-icon-hipster-character.jpg").into(imageView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set OnClickListener for Transaction History
        LinearLayout transactionHistorySection = findViewById(R.id.transactionHistorySection);
        transactionHistorySection.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileScreen.this, TransactionHistoryScreen.class);
            startActivity(intent);
        });
    }
}
