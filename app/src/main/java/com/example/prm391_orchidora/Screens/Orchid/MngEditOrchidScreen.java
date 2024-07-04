package com.example.prm391_orchidora.Screens.Orchid;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import com.example.prm391_orchidora.Screens.Profile.ManagerProfileScreen;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class MngEditOrchidScreen extends AppCompatActivity implements CategoryController.CategoryGetCallBack, OrchidController.OrchidPutCallback {

    private ImageView orchidIV, backIV, profileIV, insertImgIcon;
    private Spinner categorySpinner;
    private CategoryResponse selectedCategory;
    private CategoryAdapter categoryAdapter;
    private EditText imgUrlET, nameET,priceET,colorET,descriptionET,quantityET;
    private String token = "";
    private CategoryController categoryController;
    private OrchidController orchidController;
    private TextView cancelTV,saveTV;
    private String currentCateName="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mng_edit_orchid_layout);

        token = new TokenManager().getToken(this);

        Intent intent = getIntent();
        OrchidResponse orchid = (OrchidResponse) intent.getSerializableExtra("currentOrchid");
        currentCateName = orchid.getCategory();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar));
        }

        nameET = findViewById(R.id.nameET);
        nameET.setText(orchid.getName());

        priceET = findViewById(R.id.priceET);
        priceET.setText(orchid.getPrice()+"");

        colorET = findViewById(R.id.colorET);
        colorET.setText(orchid.getColor());

        descriptionET = findViewById(R.id.descriptionET);
        descriptionET.setText(orchid.getDescription());

        quantityET = findViewById(R.id.quantityET);
        quantityET.setText(orchid.getQuantity()+"");

        orchidController = new OrchidController(this, token);

        //Fetch categories list for choose
        categorySpinner = findViewById(R.id.categorySpinner);
        categoryController = new CategoryController(this, token);
        categoryController.fetchCategories();

        //For loading image to screen
        imgUrlET = findViewById(R.id.imgUrlET);
        insertImgIcon = findViewById(R.id.insertImgIcon);
        orchidIV = findViewById(R.id.orchidIV);
        imgUrlET.addTextChangedListener(new TextWatcher() {
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
                    orchidIV.setImageDrawable(null);
                    insertImgIcon.setVisibility(View.VISIBLE);
                }
            }
        });
        imgUrlET.setText(orchid.getImg());


        //Handle click cancel or back or profile click
        backIV = findViewById(R.id.backIV);
        profileIV = findViewById(R.id.profileIV);
        cancelTV = findViewById(R.id.cancelTV);
        handleCancel(cancelTV, backIV, profileIV);

        //Handle update orchid
        saveTV = findViewById(R.id.saveTV);
        handleUpdate(saveTV, orchid.getId());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void handleUpdate(TextView saveTV, String id) {
        saveTV.setOnClickListener(view -> {
            String name = nameET.getText().toString();
            String image = imgUrlET.getText().toString();
            int price = Integer.parseInt(priceET.getText().toString());
            String color = colorET.getText().toString();
            String categoryId = selectedCategory.getId();
            String description = descriptionET.getText().toString();
            int quantity = Integer.parseInt(quantityET.getText().toString());
            CreateOrchidRequest createOrchidRequest = new CreateOrchidRequest(name,image,color,description,price,quantity,categoryId);
            orchidController = new OrchidController(this,  token);
            orchidController.updateOrchid(id,createOrchidRequest);
        });
    }

    private void handleCancel(TextView cancelBtn, ImageView backBtn,ImageView profileIV) {
        cancelBtn.setOnClickListener(v ->{
            finish();
        });
        backBtn.setOnClickListener(v->{
            finish();
        });
        profileIV.setOnClickListener(v->{
            Intent intentNav = new Intent(this, ManagerProfileScreen.class);
            this.startActivity(intentNav);
        });
    }

    private void loadImage(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading_img) // Replace with your placeholder image resource
                .error(R.drawable.image_error) // Replace with your error image resource
                .into(orchidIV);
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

    @Override
    public void onCategorySuccessGet(List<CategoryResponse> categories) {
        categoryAdapter = new CategoryAdapter(categories, MngEditOrchidScreen.this);
        categorySpinner.setAdapter(categoryAdapter);

        for(int i = 0; i<categories.size(); i++) {
            if(categories.get(i).getName().equals(currentCateName)) {
                categorySpinner.setSelection(i);
            }
        }
        //Handle select category
        handleSelectCategory(categorySpinner);
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
    public void onOrchidSuccessPut(OrchidResponse orchid) {
        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("id",orchid.getId());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onOrchidErrorPut(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
