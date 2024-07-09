package com.example.prm391_orchidora.Screens.Orchid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Profile.ManagerProfileScreen;
import com.example.prm391_orchidora.Utils.TokenManager;

public class MngOrchidDetailScreen extends AppCompatActivity implements OrchidController.OrchidActivateCallback, OrchidController.OrchidDeactivateCallback, OrchidController.OrchidGetByIdCallback {

    private ImageView orchidIV, backIV, profileIV;
    private TextView categoryTV, nameTV, quantityTV, descriptionTV, priceTV, edit_button, hide_button;
    private View colorV;
    private OrchidController orchidController;
    private String token = "";
    private static final int EDIT_ORCHID_REQUEST_CODE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mng_orchid_detail_layout);

        token = new TokenManager().getToken(this);

        Intent intent = getIntent();
        OrchidResponse orchid = (OrchidResponse) intent.getParcelableExtra("currentOrchid");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar));
        }

        orchidIV = findViewById(R.id.orchidIV);
        Glide.with(this)
                .load(orchid.getImg())
                .into(orchidIV);

        categoryTV = findViewById(R.id.categoryTV);
        categoryTV.setText(orchid.getCategory().getName());

        nameTV = findViewById(R.id.nameTV);
        nameTV.setText(orchid.getName());

        colorV = findViewById(R.id.colorV);
        GradientDrawable border = new GradientDrawable();
        border.setColor(Color.parseColor(orchid.getColor())); //background color
        border.setStroke(4, Color.parseColor("#BFA4DC")); // 4px width, #BFA4DC border
        border.setShape(GradientDrawable.OVAL);
        colorV.setBackground(border);

        quantityTV = findViewById(R.id.quantityTV);
        quantityTV.setText("(" + orchid.getQuantity() + ")");

        descriptionTV = findViewById(R.id.descriptionTV);
        descriptionTV.setText(orchid.getDescription());

        priceTV = findViewById(R.id.priceTV);
        priceTV.setText(orchid.getPrice()+" VND");

        //Back button and Navigate to Manager Profile Screen
        backIV = findViewById(R.id.backIV);
        profileIV = findViewById(R.id.profileIV);
        handleNavigate(backIV, profileIV);

        //Navigate to edit orchid screen
        edit_button = findViewById(R.id.edit_button);
        handleClickEdit(edit_button, orchid);

        //Activate/Deactivate orchid
        hide_button = findViewById(R.id.hide_button);
        if (orchid.getStatus().equals("ACTIVE")) {
            hide_button.setText("Hide");
        } else {
            hide_button.setText("Show");
        }
        handleClickActive(hide_button, orchid.getId());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void handleClickActive(TextView hide_button, String id) {
        String currentStatus = hide_button.getText().toString();
        hide_button.setOnClickListener(l -> {
            if (currentStatus.equals("Hide")) {
                orchidController = new OrchidController((OrchidController.OrchidDeactivateCallback) this, token);
                orchidController.deactivationOrchid(id);
                hide_button.setText("Show");
            } else {
                orchidController = new OrchidController((OrchidController.OrchidActivateCallback) this, token);
                orchidController.activationOrchid(id);
                hide_button.setText("Hide");
            }
        });
    }

    private void handleClickEdit(TextView edit_button, OrchidResponse orchid) {
        edit_button.setOnClickListener(l -> {
            Intent intentNav = new Intent(MngOrchidDetailScreen.this, MngEditOrchidScreen.class);
            intentNav.putExtra("currentOrchid", orchid);
            startActivityForResult(intentNav, EDIT_ORCHID_REQUEST_CODE);
        });
    }

    private void handleNavigate(ImageView backIV, ImageView profileIV) {
        backIV.setOnClickListener(v -> {
            finish();
        });
        profileIV.setOnClickListener(v -> {
            Intent intentNav = new Intent(this, ManagerProfileScreen.class);
            this.startActivity(intentNav);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_ORCHID_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refresh data or update UI as needed
                orchidController = new OrchidController((OrchidController.OrchidGetByIdCallback) this, token);
                orchidController.fetchOrchidById(data.getStringExtra("id"));
            } else {
                // Handle other results
            }
        }
    }

    @Override
    public void onOrchidSuccessActivate() {
        Toast.makeText(this, "Activate Success", Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onOrchidErrorActivate(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrchidSuccessDeactivate() {
        Toast.makeText(this, "Deactivate Success", Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onOrchidErrorDeactivate(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrchidByIdSuccessGet(OrchidResponse orchid) {
        Glide.with(this)
                .load(orchid.getImg())
                .into(orchidIV);
        categoryTV.setText(orchid.getCategory().getName());
        nameTV.setText(orchid.getName());
        GradientDrawable border = new GradientDrawable();
        border.setColor(Color.parseColor(orchid.getColor())); //background color
        border.setStroke(4, Color.parseColor("#BFA4DC")); // 4px width, #BFA4DC border
        border.setShape(GradientDrawable.OVAL);
        colorV.setBackground(border);
        quantityTV.setText("(" + orchid.getQuantity() + ")");
        descriptionTV.setText(orchid.getDescription());
        priceTV.setText("$" + orchid.getPrice());
    }

    @Override
    public void onOrchidByIdErrorGet(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
