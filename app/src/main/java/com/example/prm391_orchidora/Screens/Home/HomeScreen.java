package com.example.prm391_orchidora.Screens.Home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.Orchid.OrchidAdapter;
import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Cart.CartScreen;
import com.example.prm391_orchidora.Screens.Profile.ProfileScreen;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements OrchidController.OrchidGetCallback {

    private RecyclerView recyclerView;
    private OrchidAdapter adapter;
    private ConstraintLayout cartLayout;
    private ImageView profileIcon;
    private OrchidController orchidController;
    private TextView testAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_layout);


        orchidController = new OrchidController(this, "550e8400-e29b-41d4-a716-446655440000");
        orchidController.fetchOrchids();

//        ImageView imageView = findViewById(R.id.footballer_image);
//        Glide.with(this).load("https://upload.wikimedia.org/wikipedia/en/thumb/c/c3/Flag_of_France.svg/125px-Flag_of_France.svg.png").into(imageView);

        cartLayout = findViewById(R.id.cart);
        cartLayout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, CartScreen.class); // Replace with your target activity
            startActivity(intent);
        });

        profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, ProfileScreen.class); // Replace with your target activity
            startActivity(intent);
        });

        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        // Create a list of filters or chips
        List<String> filters = Arrays.asList("Dendrobium", "Cattleya", "Phalaenopsis", "Cymbidium", "Miltonia", "Oncidium");
        List<String> checkedChips = new ArrayList<>();

        for (String filter : filters) {
            Chip chip = new Chip(this);
            chip.setText(filter);
            chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
            chip.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
            chip.setChipStrokeWidth(4.6f);
            chip.setChipCornerRadius(40f);

            // Set click listener or other properties as needed

            chip.setOnClickListener(v -> {
                Chip selectedChip = (Chip) v;
                String selectedFilter = selectedChip.getText().toString();
                if (checkedChips.contains(selectedFilter)) {
                    chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
                    checkedChips.remove(selectedFilter);
                    selectedChip.setChecked(false);
                } else {
                    chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
                    chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    checkedChips.add(selectedFilter);
                    selectedChip.setChecked(true);
                }

            });

            chipGroup.addView(chip);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onSuccessGet(List<OrchidResponse> orchids) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrchidAdapter(orchids, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(ErrorResponse errorMessage) {
        Toast.makeText(this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
