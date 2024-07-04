package com.example.prm391_orchidora.Screens.Orchid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Home.HomeScreen;

public class OrchidDetailScreen extends AppCompatActivity {
    private int itemNum = 1;
    private TextView itemNumber;
    private AlertDialog.Builder alertDialog;
    private TextView orchidCate;
    private TextView orchidName;
    private TextView orchidDescription;
    private TextView orchidPrice;
    private TextView orchidQuantity;
    private View orchidColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.orchid_detail_layout);

        Intent intent = getIntent();
        OrchidResponse orchid = (OrchidResponse) intent.getSerializableExtra("currentOrchid");

        ImageView orchidImg = findViewById(R.id.orchidImg);
        Glide.with(this).load(orchid.getImg()).into(orchidImg);

        orchidCate = findViewById(R.id.orchidCate);
        orchidCate.setText(orchid.getCategory());

        orchidName = findViewById(R.id.orchidName);
        orchidName.setText(orchid.getName());

        orchidColor = findViewById(R.id.orchidColor);
        GradientDrawable border = new GradientDrawable();
        border.setColor(Color.parseColor(orchid.getColor())); //background color
        border.setStroke(4, Color.parseColor("#BFA4DC")); // 4px width, #BFA4DC border
        border.setShape(GradientDrawable.OVAL);
        orchidColor.setBackground(border);

        orchidQuantity = findViewById(R.id.orchidQuantity);
        orchidQuantity.setText("(" + orchid.getQuantity() + ")");

        orchidDescription = findViewById(R.id.orchidDescription);
        orchidDescription.setText(orchid.getDescription());

        orchidPrice = findViewById(R.id.orchidPrice);
        orchidPrice.setText("$"+orchid.getPrice());

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
