package com.example.prm391_orchidora.Screens.Category;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm391_orchidora.Controller.CategoryController;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.List;

public class AddCategoryScreen extends AppCompatActivity implements CategoryController.CategoryPostCallBack {
    private Button cancelBtn;
    private Button addBtn;
    private ImageView backBtn;
    private EditText cateName;
    private String token = "";
    private CategoryController categoryController;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_create_layout);

        // Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar));
        }

        backBtn = findViewById(R.id.backBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        addBtn = findViewById(R.id.addBtn);
        cateName = findViewById(R.id.cateName);
        token = new TokenManager().getToken(this);
        categoryController = new CategoryController(this, token);


        addBtn.setOnClickListener(view -> {
            String categoryName = cateName.getText().toString();
            if (!categoryName.isEmpty()) {
                CategoryResponse newCategory = new CategoryResponse();
                newCategory.setName(categoryName);
                categoryController.createCategory(newCategory);
            } else {
                Toast.makeText(AddCategoryScreen.this, "Please enter a category name", Toast.LENGTH_SHORT).show();
            }
        });

        //Handle click cancel or back
        handleBack(backBtn, cancelBtn);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void handleBack( ImageView backBtn, Button cancelBtn) {
        backBtn.setOnClickListener(v->{
            finish();
        });
        cancelBtn.setOnClickListener(v->{
            finish();
        });
    }

    @Override
    public void onCategorySuccessPost(CategoryResponse category) {
        Toast.makeText(this, "Create Successfully", Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onCategoryErrorPost(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
