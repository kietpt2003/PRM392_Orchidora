package com.example.prm391_orchidora.Screens.Category;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm391_orchidora.Controller.CategoryController;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.List;

public class EditCategoryActivity extends AppCompatActivity {
    private EditText editCategoryName;
    private Button btnSaveCategory;
    private CategoryController categoryController;
    private String categoryId;
    private String token ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_manage_edit_layout);

        editCategoryName = findViewById(R.id.edit_category_name);
        btnSaveCategory = findViewById(R.id.btn_save_category);
        token = TokenManager.getToken(EditCategoryActivity.this);
        categoryController = new CategoryController((CategoryController.CategoryGetCallBack) this, token);
        categoryController.fetchCategories();

        // Retrieve category details from intent
        categoryId = getIntent().getStringExtra("CATEGORY_ID");
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        editCategoryName.setText(categoryName);

        categoryController = new CategoryController(new CategoryController.CategoryGetCallBack() {
            @Override
            public void onCategorySuccessGet(List<CategoryResponse> categories) {
                // Not used in this context
            }

            @Override
            public void onCategoryErrorGet(ErrorResponse errorResponse) {
                // Handle error
            }

            @Override
            public void onCategorySuccessPut(CategoryResponse category) {
                finish();
            }

            private void finish() {
            }
        }, token);

        btnSaveCategory.setOnClickListener(v -> {
            String newCategoryName = editCategoryName.getText().toString();
            CategoryResponse updatedCategory = new CategoryResponse();
            updatedCategory.setName(newCategoryName);

            categoryController.updateCategory(categoryId, updatedCategory);
        });
    }
}
