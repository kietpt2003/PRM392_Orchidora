package com.example.prm391_orchidora.Screens.Category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm391_orchidora.Controller.CategoryController;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.List;

public class EditCategoryActivity extends AppCompatActivity implements CategoryController.CategoryGetCallBack {
    private EditText editCategoryName;
    private Button btnSaveCategory;
    private CategoryController categoryController;
    private String categoryId;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_manage_edit_layout);

        editCategoryName = findViewById(R.id.edit_category_name);
        btnSaveCategory = findViewById(R.id.btn_save_category);
        token = new TokenManager().getToken(this);
        categoryController = new CategoryController(this, token);

        // Retrieve category details from intent
        categoryId = getIntent().getStringExtra("CATEGORY_ID");
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        editCategoryName.setText(categoryName);

        // Log Category ID
        Log.d("EditCategoryActivity", "Category ID: " + categoryId);

        if (categoryId == null) {
            Log.e("EditCategoryActivity", "CATEGORY_ID is null");
        }

        btnSaveCategory.setOnClickListener(v -> {
            String newCategoryName = editCategoryName.getText().toString();
            Log.d("EditCategoryActivity", "Saving category with ID: " + categoryId + " and new name: " + newCategoryName);
            if (categoryId != null && !categoryId.isEmpty()) {
                CategoryResponse updatedCategory = new CategoryResponse();
                updatedCategory.setId(categoryId); // Ensure ID is set here
                updatedCategory.setName(newCategoryName);
                categoryController.updateCategory(categoryId, updatedCategory);
            } else {
                Toast.makeText(EditCategoryActivity.this, "Category ID is missing", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onCategorySuccessGet(List<CategoryResponse> categories) {
        // This method is not used in EditCategoryActivity
    }

    @Override
    public void onCategoryErrorGet(ErrorResponse errorResponse) {
        Toast.makeText(this, "Error: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategorySuccessPut(CategoryResponse category) {
        Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onCategorySuccessDelete() {

    }

    @Override
    public void onCategorySuccessPost(CategoryResponse category) {

    }
}
