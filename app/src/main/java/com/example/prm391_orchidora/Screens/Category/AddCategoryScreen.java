package com.example.prm391_orchidora.Screens.Category;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm391_orchidora.R;

public class AddCategoryScreen extends AppCompatActivity {
    private Button cancelBtn;
    private Button addBtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.category_create_layout);

        cancelBtn = findViewById(R.id.cancelBtn);
        addBtn = findViewById(R.id.addBtn);

        cancelBtn.setOnClickListener(view -> {
            finish();
        });

        addBtn.setOnClickListener(view -> {

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
