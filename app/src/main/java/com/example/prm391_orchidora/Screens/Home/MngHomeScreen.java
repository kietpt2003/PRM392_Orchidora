package com.example.prm391_orchidora.Screens.Home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prm391_orchidora.Adapter.Orchid.MngOrchidAdapter;
import com.example.prm391_orchidora.Controller.CategoryController;
import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Orchid.CreateOrchidScreen;
import com.example.prm391_orchidora.Screens.Profile.ManagerProfileScreen;
import com.example.prm391_orchidora.Utils.TokenManager;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MngHomeScreen extends AppCompatActivity implements OrchidController.OrchidGetCallback,OrchidController.OrchidByCateGetCallBack, CategoryController.CategoryGetCallBack {

    private RecyclerView recyclerView;
    private ImageView profileIcon;
    private FloatingActionButton floating_action_button;
    private static final int CREATE_ORCHID_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_ORCHID_DETAIL = 2;
    private OrchidController orchidController;
    private CategoryController categoryController;
    private String token = "";
    private String searchInput = "";
    private String categorySelectedId = "";
    private SearchView searchView;
    private MngOrchidAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mng_home_layout);

        token = new TokenManager().getToken(this);

        // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar));
        }

        //Orchid list
        orchidController = new OrchidController((OrchidController.OrchidGetCallback) this, token);
        orchidController.fetchOrchids("", "");

        //Manager Profile Screen
        profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MngHomeScreen.this, ManagerProfileScreen.class); // Replace with your target activity
            startActivity(intent);
        });

        //Manage Category Screen
        floating_action_button = findViewById(R.id.floating_action_button);
        floating_action_button.setOnClickListener(v -> {
            Intent intent = new Intent(MngHomeScreen.this, CreateOrchidScreen.class); // Replace with your target activity
            startActivityForResult(intent, CREATE_ORCHID_REQUEST_CODE);
        });

        //Cate list
        categoryController = new CategoryController(this, token);
        categoryController.fetchCategories();

        //SearchView
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearchInput(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handleSearchInput(newText);
                return false;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchInput = "";
        categorySelectedId ="";
        searchView.setQuery("", false);
        orchidController = new OrchidController((OrchidController.OrchidGetCallback) this, token);
        orchidController.fetchOrchids("", "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_ORCHID_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refresh data or update UI as needed
                searchInput = "";
                categorySelectedId ="";
                searchView.setQuery("", false);
                orchidController = new OrchidController((OrchidController.OrchidGetCallback) this, token);
                orchidController.fetchOrchids("", "");
            } else {
                // Handle other results
            }
        } else if(requestCode == REQUEST_CODE_ORCHID_DETAIL){
            if (resultCode == RESULT_OK) {
                // Refresh data or update UI as needed
                searchInput = "";
                categorySelectedId ="";
                searchView.setQuery("", false);
                orchidController = new OrchidController((OrchidController.OrchidGetCallback) this, token);
                orchidController.fetchOrchids("", "");
            } else {
                // Handle other results
            }
        }
    }

    private void handleSearchInput(String searchText) {
        searchInput = searchText;
        if (categorySelectedId.isEmpty()) {
            orchidController= new OrchidController((OrchidController.OrchidGetCallback) this, token);
            orchidController.fetchOrchids(searchText, "");
        } else {
            orchidController= new OrchidController((OrchidController.OrchidByCateGetCallBack) this, token);
            orchidController.fetchOrchidsByCate(categorySelectedId, searchInput,"");
        }
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

                if (categorySelectedId.isEmpty()) {
                    orchidController = new OrchidController((OrchidController.OrchidGetCallback) this, token);
                    orchidController.fetchOrchids(searchInput,"");
                } else {
                    orchidController = new OrchidController((OrchidController.OrchidByCateGetCallBack) this, token);
                    orchidController.fetchOrchidsByCate(categorySelectedId,searchInput,"");
                }
            });

            chipGroup.addView(chip);
        }
    }

    @Override
    public void onCategoryErrorGet(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrchidSuccessGet(List<OrchidResponse> orchids) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MngOrchidAdapter(orchids, this, token);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onOrchidErrorGet(ErrorResponse errorMessage) {
        Toast.makeText(this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrchidByCateSuccessGet(List<OrchidResponse> orchids) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MngOrchidAdapter(orchids, this, token);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onOrchidByCateErrorGet(ErrorResponse errorMessage) {
        Toast.makeText(this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
