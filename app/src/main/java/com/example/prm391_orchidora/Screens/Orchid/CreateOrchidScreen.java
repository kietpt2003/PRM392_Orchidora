package com.example.prm391_orchidora.Screens.Orchid;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Adapter.Category.CategoryAdapter;
import com.example.prm391_orchidora.Models.Category.Category;
import com.example.prm391_orchidora.R;

import java.util.ArrayList;
import java.util.List;

public class CreateOrchidScreen extends AppCompatActivity {
    private Spinner orchidType;
    private CategoryAdapter categoryAdapter;
    private EditText orchidImgTxt;
    private ImageView orchidImg;
    private ImageView insertImgIcon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_orchid_layout);

        orchidType = findViewById(R.id.orchidType);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("0123", "Dendrobium","This is test description"));
        categoryList.add(new Category("0124", "Cattleya","This is test description"));
        categoryList.add(new Category("0125", "Phalaenopsis","This is test description"));
        categoryList.add(new Category("0126", "Cymbidium","This is test description"));
        categoryList.add(new Category("0127", "Miltonia","This is test description"));
        categoryList.add(new Category("0128", "Oncidium","This is test description"));
        categoryAdapter = new CategoryAdapter(categoryList, CreateOrchidScreen.this);
        orchidType.setAdapter(categoryAdapter);

        orchidImgTxt = findViewById(R.id.orchidImgTxt);
        orchidImg = findViewById(R.id.orchidImg);
        insertImgIcon = findViewById(R.id.insertImgIcon);

        orchidImgTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                String url = s.toString().trim();
                if (!url.isEmpty()) {
                    loadImage(url);
                    insertImgIcon.setVisibility(View.INVISIBLE);
                } else {
                    orchidImg.setImageDrawable(null);
                    insertImgIcon.setVisibility(View.VISIBLE);
                }
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadImage(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading_img) // Replace with your placeholder image resource
                .error(R.drawable.image_error) // Replace with your error image resource
                .into(orchidImg);
    }
}
