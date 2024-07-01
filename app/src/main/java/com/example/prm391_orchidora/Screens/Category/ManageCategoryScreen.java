package com.example.prm391_orchidora.Screens.Category;

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
import com.example.prm391_orchidora.Models.Category.Category;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Orchid.CreateOrchidScreen;

import java.util.ArrayList;
import java.util.List;

public class ManageCategoryScreen extends AppCompatActivity  {
    private RecyclerView category_list;
    private CategoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_manage_layout);

        category_list = findViewById(R.id.category_list);
        category_list.setLayoutManager(new LinearLayoutManager(this));

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("0123", "Dendrobium","This is test description"));
        categoryList.add(new Category("0124", "Cattleya","This is test description"));
        categoryList.add(new Category("0125", "Phalaenopsis","This is test description"));
        categoryList.add(new Category("0126", "Cymbidium","This is test description"));
        categoryList.add(new Category("0127", "Miltonia","This is test description"));
        categoryList.add(new Category("0128", "Oncidium","This is test description"));
        adapter = new CategoryListAdapter(categoryList, ManageCategoryScreen.this);
        category_list.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
