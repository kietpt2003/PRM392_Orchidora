package com.example.prm391_orchidora.Screens.Orchid;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Adapter.Category.CategoryAdapter;
import com.example.prm391_orchidora.Controller.CategoryController;
import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Models.Category.Category;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.CreateOrchidRequest;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class CreateOrchidScreen extends AppCompatActivity implements CategoryController.CategoryGetCallBack, OrchidController.OrchidPostCallback {
    private Spinner orchidType;
    private CategoryResponse selectedCategory;
    private CategoryAdapter categoryAdapter;
    private EditText orchidName;
    private EditText orchidImgTxt;
    private EditText orchidPrice;
    private EditText orchidColor;
    private EditText orchidDescription;
    private EditText orchidQuantity;
    private ImageView orchidImg;
    private ImageView insertImgIcon;
    private CategoryController categoryController;
    private String token = "";
    private Button addBtn;
    private Button cancelBtn;
    private ImageView backBtn;
    private OrchidController orchidController;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_orchid_layout);

        orchidType = findViewById(R.id.orchidType);
        orchidName = findViewById(R.id.orchidName);
        orchidImgTxt = findViewById(R.id.orchidImgTxt);
        orchidPrice = findViewById(R.id.orchidPrice);
        orchidColor = findViewById(R.id.orchidColor);
        orchidDescription = findViewById(R.id.orchidDescription);
        orchidQuantity = findViewById(R.id.orchidQuantity);
        orchidImg = findViewById(R.id.orchidImg);
        insertImgIcon = findViewById(R.id.insertImgIcon);
        addBtn = findViewById(R.id.addBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        backBtn = findViewById(R.id.backBtn);

        token = new TokenManager().getToken(this);

        //Fetch categories list for choose
        categoryController = new CategoryController(this, token);
        categoryController.fetchCategories();

        //For loading image to screen
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

        //Handle create orchid
        handleCreate(addBtn);

        //Handle click cancel or back
        handleCancel(cancelBtn, backBtn);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void handleSelectCategory(Spinner orchidType){
        orchidType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected CategoryResponse object
                selectedCategory = (CategoryResponse) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected, if needed
            }
        });
    }

    private void handleCreate(Button addBtn) {
        addBtn.setOnClickListener(view -> {
            String name = orchidName.getText().toString();
            String image = orchidImgTxt.getText().toString();
            int price = Integer.parseInt(orchidPrice.getText().toString());
            String color = orchidColor.getText().toString();
            String categoryId = selectedCategory.getId();
            String description = orchidDescription.getText().toString();
            int quantity = Integer.parseInt(orchidQuantity.getText().toString());
            CreateOrchidRequest createOrchidRequest = new CreateOrchidRequest(name,image,color,description,price,quantity,categoryId);
            orchidController = new OrchidController(this,  token);
            orchidController.createOrchid(createOrchidRequest);
        });
    }
    private void handleCancel(Button cancelBtn, ImageView backBtn) {
        cancelBtn.setOnClickListener(v ->{
            finish();
        });
        backBtn.setOnClickListener(v->{
            finish();
        });
    }

    private void loadImage(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading_img) // Replace with your placeholder image resource
                .error(R.drawable.image_error) // Replace with your error image resource
                .into(orchidImg);
    }

    @Override
    public void onCategorySuccessGet(List<CategoryResponse> categories) {
        categoryAdapter = new CategoryAdapter(categories, CreateOrchidScreen.this);
        orchidType.setAdapter(categoryAdapter);
        //Handle select category
        handleSelectCategory(orchidType);
    }

    @Override
    public void onCategoryErrorGet(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategorySuccessPut(CategoryResponse category) {

    }

    @Override
    public void onCategorySuccessDelete() {

    }

    @Override
    public void onCategorySuccessPost(CategoryResponse category) {

    }

    @Override
    public void onOrchidSuccessPost(OrchidResponse orchid) {
        Toast.makeText(this, "Create success", Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onOrchidErrorPost(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
