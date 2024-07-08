package com.example.prm391_orchidora.Screens.Orchid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Adapter.Cart.CartAdapter;
import com.example.prm391_orchidora.Models.CartItem;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Home.HomeScreen;
import com.example.prm391_orchidora.Screens.Profile.ProfileScreen;
import com.example.prm391_orchidora.Services.CartService;
import com.example.prm391_orchidora.Utils.Database;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.List;

public class OrchidDetailScreen extends AppCompatActivity {
    private Database db;
    private int itemNum = 1;
    private TextView itemNumber;
    private AlertDialog.Builder alertDialog;
    private TextView orchidCate;
    private TextView orchidName;
    private TextView orchidDescription;
    private TextView orchidPrice;
    private TextView orchidQuantity;
    private View orchidColor;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.orchid_detail_layout);

        token = new TokenManager().getToken(this);

        Intent intent = getIntent();

        OrchidResponse orchid = intent.getParcelableExtra("currentOrchid");
        handleSetOrchid(orchid);

        handleDB();

        handleClickUp(orchid);
        handleClickDown();

        handleAddToCart(orchid);

        handleNavigate();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void handleAddToCart(OrchidResponse orchid){
        Button addToCartBtn = findViewById(R.id.addToCartBtn);
        CartService cartService = new CartService(db,token);
        addToCartBtn.setOnClickListener(l -> {
            if (orchid.getQuantity() > 0) {
                cartService.getCarts(cartItemList -> {
                    boolean itemExists = false;
                    int currentOrchidQuantity = 0;

                    for (CartItem item : cartItemList) {
                        if (item.getOrchid().getId().equals(orchid.getId())) {
                            itemExists = true;
                            currentOrchidQuantity += item.getQuantity();
                            break;
                        }
                    }
                    currentOrchidQuantity += itemNum;
                    if (itemExists) {
                        if (currentOrchidQuantity <= orchid.getQuantity()) {
                            db.queryData("UPDATE OrchidList SET quantity = " + currentOrchidQuantity + " WHERE id = '" + orchid.getId() + "'");
                            Toast.makeText(this, "Add to cart success", Toast.LENGTH_SHORT).show();
                        } else {
                            showAlert("Warning", "Quantity exceeds available stock.");
                        }
                    } else {
                        if (currentOrchidQuantity <= orchid.getQuantity()) {
                            db.queryData("INSERT INTO OrchidList VALUES('" + orchid.getId() + "', " + itemNum + ", 1)");
                            Toast.makeText(this, "Add to cart success", Toast.LENGTH_SHORT).show();
                        } else {
                            showAlert("Warning", "Quantity exceeds available stock.");
                        }
                    }
                });
            } else {
                showAlert("Warning","This orchid is out of stock.");
            }

        });
    }
    private void showAlert(String title,String content){
        alertDialog = new AlertDialog.Builder(OrchidDetailScreen.this);
        alertDialog.setTitle(title)
                .setMessage(content)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void handleDB(){
        db = new Database(this, "OrchidList.sqlite", null, 1);
        // Tạo bảng OrchidList nếu chưa tồn tại
        db.queryData("CREATE TABLE IF NOT EXISTS OrchidList(id NVARCHAR(200), quantity INTEGER, isSelected INTERGER)");
    }

    private void handleSetOrchid(OrchidResponse orchid){
        ImageView orchidImg = findViewById(R.id.orchidImg);
        Glide.with(this).load(orchid.getImg()).into(orchidImg);

        orchidCate = findViewById(R.id.orchidCate);
        orchidCate.setText(orchid.getCategory().getName());

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
    }

    private void handleClickUp(OrchidResponse orchidResponse){
        ImageView upItem = findViewById(R.id.upItem);
        upItem.setOnClickListener(view -> {
            increaseNumberItem(orchidResponse);
        });
    }

    private void handleClickDown(){
        ImageView downItem = findViewById(R.id.downItem);
        downItem.setOnClickListener(view -> {
            decreaseNumberItem();
        });
    }

    private void handleNavigate(){
        ImageView backBtn = findViewById(R.id.backBtn);
        ImageView profileIV = findViewById(R.id.profileIV);
        backBtn.setOnClickListener(view -> {
            this.finish();
        });

        profileIV.setOnClickListener(l ->{
            Intent intentNav = new Intent(OrchidDetailScreen.this, ProfileScreen.class);
            startActivity(intentNav);
        });
    }

    private void increaseNumberItem(OrchidResponse orchidResponse) {

        if (itemNum < orchidResponse.getQuantity()){
            itemNum++;
        } else {
            showAlert("Warning","Number cannot greater than " + orchidResponse.getQuantity()+".");
        }
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
