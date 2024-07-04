package com.example.prm391_orchidora.Screens.Category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.Cart.CategoryListAdapter;
import com.example.prm391_orchidora.Controller.CategoryController;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Home.MngHomeScreen;
import com.example.prm391_orchidora.Utils.TokenManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ManageCategoryScreen extends AppCompatActivity implements CategoryController.CategoryGetCallBack {
    private static final int EDIT_CATEGORY_REQUEST_CODE = 1;
    private static final int ADD_CATEGORY_REQUEST_CODE = 2;

    private RecyclerView category_list;
    private CategoryListAdapter adapter;
    private CategoryController categoryController;
    private String token = "";
    private FloatingActionButton floating_action_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_manage_layout);
        token = new TokenManager().getToken(this);
        categoryController = new CategoryController(this, token);

        category_list = findViewById(R.id.category_list);
        category_list.setLayoutManager(new LinearLayoutManager(this));

        // Manager Add Category Screen
        floating_action_button = findViewById(R.id.floating_action_button);
        floating_action_button.setOnClickListener(v -> {
            Intent intent = new Intent(ManageCategoryScreen.this, AddCategoryScreen.class); // Replace with your target activity
            startActivityForResult(intent, ADD_CATEGORY_REQUEST_CODE);
        });

        fetchCategories();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void fetchCategories() {
        categoryController.fetchCategories();
    }

    @Override
    public void onCategorySuccessGet(List<CategoryResponse> categories) {
        adapter = new CategoryListAdapter(categories, this, category -> {
            Intent intent = new Intent(this, EditCategoryActivity.class);
            intent.putExtra("CATEGORY_ID", category.getId());
            intent.putExtra("CATEGORY_NAME", category.getName());
            startActivityForResult(intent, EDIT_CATEGORY_REQUEST_CODE);
        });
        category_list.setAdapter(adapter);
    }

    @Override
    public void onCategoryErrorGet(ErrorResponse errorResponse) {
        Log.e("ManageCategoryScreen", "Error: " + errorResponse.getMessage());
        // Handle error scenario here
    }

    @Override
    public void onCategorySuccessPut(CategoryResponse category) {
        // Refresh category list after successful update
        fetchCategories();
    }

    @Override
    public void onCategorySuccessDelete() {
        fetchCategories(); // Refresh categories on successful delete
    }

    @Override
    public void onCategorySuccessPost(CategoryResponse category) {
        fetchCategories();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == EDIT_CATEGORY_REQUEST_CODE || requestCode == ADD_CATEGORY_REQUEST_CODE) && resultCode == RESULT_OK) {
            fetchCategories(); // Refresh categories on successful update
        }
    }
}
