package com.example.prm391_orchidora.Screens.Category;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.Cart.CategoryListAdapter;
import com.example.prm391_orchidora.Adapter.Category.CategoryAdapter;
import com.example.prm391_orchidora.Adapter.Orchid.OrchidAdapter;
import com.example.prm391_orchidora.Controller.CategoryController;
import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Models.Category.Category;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Orchid.CreateOrchidScreen;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class ManageCategoryScreen extends AppCompatActivity implements CategoryController.CategoryGetCallBack {
    private RecyclerView category_list;
    private CategoryListAdapter adapter;
    private CategoryController categoryController;

    private String token ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_manage_layout);
        token = TokenManager.getToken(ManageCategoryScreen.this);
        categoryController = new CategoryController(this, token);
        categoryController.fetchCategories();

        category_list = findViewById(R.id.category_list);
        category_list.setLayoutManager(new LinearLayoutManager(this));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onCategorySuccessGet(List<CategoryResponse> categories) {
        category_list = findViewById(R.id.category_list);
        category_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryListAdapter(categories, this, category -> {
            Intent intent = new Intent(this, EditCategoryActivity.class);
            intent.putExtra("CATEGORY_ID", category.getId());
            intent.putExtra("CATEGORY_NAME", category.getName());
            startActivity(intent);
        });
        category_list.setAdapter(adapter);
    }

    @Override
    public void onCategoryErrorGet(ErrorResponse errorResponse) {

    }

    @Override
    public void onCategorySuccessPut(CategoryResponse category) {

    }

}
