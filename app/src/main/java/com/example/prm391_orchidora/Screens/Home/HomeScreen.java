package com.example.prm391_orchidora.Screens.Home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.example.prm391_orchidora.Controller.CategoryController;
import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Cart.CartScreen;
import com.example.prm391_orchidora.Screens.Profile.ProfileScreen;
import com.example.prm391_orchidora.Utils.TokenManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements OrchidController.OrchidGetCallback,OrchidController.OrchidByCateGetCallBack, CategoryController.CategoryGetCallBack {

    private RecyclerView recyclerView;
    private OrchidAdapter adapter;
    private ConstraintLayout cartLayout;
    private ImageView profileIcon;
    private OrchidController orchidController;
    private CategoryController categoryController;
    private String token = "";
    private String searchInput = "";
    private String categorySelectedId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_layout);

        token = new TokenManager().getToken(this);
        orchidController = new OrchidController((OrchidController.OrchidGetCallback) this, token);
        orchidController.fetchOrchids("", "ACTIVE");

//        ImageView imageView = findViewById(R.id.footballer_image);
//        Glide.with(this).load("https://upload.wikimedia.org/wikipedia/en/thumb/c/c3/Flag_of_France.svg/125px-Flag_of_France.svg.png").into(imageView);

        //Cart Screen
        cartLayout = findViewById(R.id.cart);
        cartLayout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, CartScreen.class); // Replace with your target activity
            startActivity(intent);
        });

        //Profile Screen
        profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, ProfileScreen.class); // Replace with your target activity
            startActivity(intent);
        });

        //Cate list
        categoryController = new CategoryController(this, token);
        categoryController.fetchCategories();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onOrchidSuccessGet(List<OrchidResponse> orchids) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrchidAdapter(orchids, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onOrchidErrorGet(ErrorResponse errorMessage) {
        Toast.makeText(this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategorySuccessGet(List<CategoryResponse> categories) {
        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        // Create a list of filters or chips
        List<CategoryResponse> checkedChips = new ArrayList<>();

        for (CategoryResponse category : categories) {
            Chip chip = new Chip(this);
            chip.setText(category.getName());
            chip.setTag(category.getId()); // Set the category id as tag
            chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
            chip.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
            chip.setChipStrokeWidth(4.6f);
            chip.setChipCornerRadius(40f);

            // Set click listener or other properties as needed
            chip.setOnClickListener(v -> {
                Chip selectedChip = (Chip) v;
                String selectedCategoryId = selectedChip.getTag().toString(); // Get the category id
                String selectedCategoryName = selectedChip.getText().toString(); // Get the category name

                CategoryResponse currentSelectedCate = new CategoryResponse(selectedCategoryId, selectedCategoryName);

                if (!checkedChips.isEmpty() && checkedChips.get(0).getId().equals(selectedCategoryId)) {
                    // If the chip is already selected, unselect it
                    selectedChip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    selectedChip.setTextColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
                    checkedChips.clear();
                    selectedChip.setChecked(false);
                    Log.d("UnSelectedCategory", "ID: " + selectedCategoryId + ", Name: " + selectedCategoryName);
                    categorySelectedId = "";
                } else {
                    // If the chip is not selected, clear all selections and select the clicked chip
                    for (int i = 0; i < chipGroup.getChildCount(); i++) {
                        Chip chipInGroup = (Chip) chipGroup.getChildAt(i);
                        chipInGroup.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                        chipInGroup.setTextColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
                        chipInGroup.setChecked(false);
                    }

                    chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
                    chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    checkedChips.clear();
                    checkedChips.add(currentSelectedCate);
                    selectedChip.setChecked(true);
                    Log.d("SelectedCategory", "ID: " + selectedCategoryId + ", Name: " + selectedCategoryName);
                    categorySelectedId = selectedCategoryId;
                }


                // orchidController.fetchOrchidsByCate(selectedCategoryId);
            });

            chipGroup.addView(chip);
        }
    }

    @Override
    public void onCategoryErrorGet(ErrorResponse errorMessage) {
        Toast.makeText(this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategorySuccessPut(CategoryResponse category) {

    }

    @Override
    public void onOrchidByCateSuccessGet(List<OrchidResponse> orchids) {

    }

    @Override
    public void onOrchidByCateErrorGet(ErrorResponse errorMessage) {
        Toast.makeText(this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
