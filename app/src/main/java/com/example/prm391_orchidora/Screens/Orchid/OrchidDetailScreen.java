package com.example.prm391_orchidora.Screens.Orchid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Home.HomeScreen;

public class OrchidDetailScreen extends AppCompatActivity {
    private int itemNum = 1;
    private TextView itemNumber;
    private AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.orchid_detail_layout);

        ImageView orchidImg = findViewById(R.id.orchidImg);
        Glide.with(this).load("data:image/jpeg;base64,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").into(orchidImg);

        ImageView upItem = findViewById(R.id.upItem);
        ImageView downItem = findViewById(R.id.downItem);
        ImageView backBtn = findViewById(R.id.backBtn);
        Button addToCartBtn = findViewById(R.id.addToCartBtn);

        upItem.setOnClickListener(view -> {
            increaseNumberItem();
        });

        downItem.setOnClickListener(view -> {
            decreaseNumberItem();
        });

        backBtn.setOnClickListener(view -> {
            this.finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void increaseNumberItem() {
        itemNum++;
        itemNumber = findViewById(R.id.itemNumber);
        itemNumber.setText(itemNum + "");
    }

    private void decreaseNumberItem() {
        if (itemNum > 1) {
            itemNum--;
        } else {
            alertDialog = new AlertDialog.Builder(OrchidDetailScreen.this);
            alertDialog.setTitle("Warning")
                    .setMessage("Number cannot lower than 0.")
                    .setPositiveButton("Ok", (dialog, id) -> {
                        dialog.dismiss();
                    });

            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }
        itemNumber = findViewById(R.id.itemNumber);
        itemNumber.setText(itemNum + "");
    }
}
