package com.example.prm391_orchidora.Screens.Orchid;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.R;

public class MngOrchidDetailScreen extends AppCompatActivity {

    ImageView orchidIV, backIV, profileIV;
    TextView categoryTV, nameTV,quantityTV, descriptionTV,priceTV;
    View colorV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mng_orchid_detail_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar ));
        }

        backIV = findViewById(R.id.backIV);
        profileIV = findViewById(R.id.profileIV);
        orchidIV = findViewById(R.id.orchidIV);
        categoryTV = findViewById(R.id.categoryTV);
        nameTV = findViewById(R.id.nameTV);
        colorV = findViewById(R.id.colorV);
        quantityTV = findViewById(R.id.quantityTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        priceTV = findViewById(R.id.priceTV);


        backIV.setOnClickListener(v -> {
            finish();
        });

         Glide.with(this)
                    .load("https://hips.hearstapps.com/hmg-prod/images/cattleya-orchid-types-1587738446.jpg?crop=1xw:0.8974540311173974xh;center,top&resize=980:*")
                    .into(orchidIV);

         categoryTV.setText("Orchidaceae");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
