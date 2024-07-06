package com.example.prm391_orchidora.Screens.Orchid;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.CreateOrchidRequest;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Profile.ManagerProfileScreen;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.List;

public class MngEditOrchidScreen extends AppCompatActivity implements CategoryController.CategoryGetCallBack, OrchidController.OrchidPutCallback {

    private ImageView orchidIV;
    private ImageView insertImgIcon;
    private Spinner categorySpinner;
    private CategoryResponse selectedCategory;
    private EditText imgUrlET, nameET,priceET,colorET,descriptionET,quantityET;
    private String token = "";
    private OrchidController orchidController;
    private String currentCateId="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mng_edit_orchid_layout);

        token = new TokenManager().getToken(this);

        Intent intent = getIntent();
        OrchidResponse orchid = (OrchidResponse) intent.getParcelableExtra("currentOrchid");
        currentCateId = orchid != null ? orchid.getCategory().getId() : "";

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar));

        nameET = findViewById(R.id.nameET);
        nameET.setText(orchid != null ? orchid.getName() : "");

        priceET = findViewById(R.id.priceET);
        priceET.setText((orchid != null ? orchid.getPrice() : "") +"");

        colorET = findViewById(R.id.colorET);
        colorET.setText(orchid != null ? orchid.getColor() : "");

        descriptionET = findViewById(R.id.descriptionET);
        descriptionET.setText(orchid != null ? orchid.getDescription() : "");

        quantityET = findViewById(R.id.quantityET);
        quantityET.setText((orchid != null ? orchid.getQuantity() : "") +"");

        orchidController = new OrchidController(this, token);

        //Fetch categories list for choose
        categorySpinner = findViewById(R.id.categorySpinner);
        CategoryController categoryController = new CategoryController(this, token);
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
        imgUrlET.setText(orchid != null ? orchid.getImg() : "");


        //Handle click cancel or back or profile click
        ImageView backIV = findViewById(R.id.backIV);
        ImageView profileIV = findViewById(R.id.profileIV);
        TextView cancelTV = findViewById(R.id.cancelTV);
        handleCancel(cancelTV, backIV, profileIV);

        //Handle update orchid
        TextView saveTV = findViewById(R.id.saveTV);
        handleUpdate(saveTV, orchid != null ? orchid.getId() : "");

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
        cancelBtn.setOnClickListener(l -> finish());
        backBtn.setOnClickListener(l-> finish());
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
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, MngEditOrchidScreen.this);
        categorySpinner.setAdapter(categoryAdapter);

        for(int i = 0; i<categories.size(); i++) {
            if(categories.get(i).getId().equals(currentCateId)) {
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
