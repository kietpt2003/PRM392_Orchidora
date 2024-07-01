package com.example.prm391_orchidora.Screens.Orchid;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Adapter.Category.CategoryAdapter;
import com.example.prm391_orchidora.Models.Category.Category;
import com.example.prm391_orchidora.R;

import java.util.ArrayList;
import java.util.List;

public class MngEditOrchidScreen extends AppCompatActivity {

    ImageView orchidIV, backIV, profileIV;
    Spinner categorySpinner;
    CategoryAdapter categoryAdapter;

     @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mng_edit_orchid_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar ));
        }

        backIV = findViewById(R.id.backIV);
        profileIV = findViewById(R.id.profileIV);
        orchidIV = findViewById(R.id.orchidIV);
        categorySpinner = findViewById(R.id.categorySpinner);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("0123", "Dendrobium","This is test description"));
        categoryList.add(new Category("0124", "Cattleya","This is test description"));
        categoryList.add(new Category("0125", "Phalaenopsis","This is test description"));
        categoryList.add(new Category("0126", "Cymbidium","This is test description"));
        categoryList.add(new Category("0127", "Miltonia","This is test description"));
        categoryList.add(new Category("0128", "Oncidium","This is test description"));
        categoryAdapter = new CategoryAdapter(categoryList, this);
        categorySpinner.setAdapter(categoryAdapter);

        backIV.setOnClickListener(v -> {
            finish();
        });

         Glide.with(this)
                    .load("https://hips.hearstapps.com/hmg-prod/images/cattleya-orchid-types-1587738446.jpg?crop=1xw:0.8974540311173974xh;center,top&resize=980:*")
                    .into(orchidIV);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}