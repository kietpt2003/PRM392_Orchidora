package com.example.prm391_orchidora.Screens.Orchid;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.R;

public class MngEditOrchidScreen extends AppCompatActivity {

    ImageView orchidIV, backIV, profileIV;
     @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mng_edit_orchid_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar ));
        }

        backIV = findViewById(R.id.backIV);
        profileIV = findViewById(R.id.profileIV);
        orchidIV = findViewById(R.id.orchidIV);

        backIV.setOnClickListener(v -> {
            finish();
        });

         Glide.with(this)
                    .load("https://hips.hearstapps.com/hmg-prod/images/cattleya-orchid-types-1587738446.jpg?crop=1xw:0.8974540311173974xh;center,top&resize=980:*")
                    .into(orchidIV);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
