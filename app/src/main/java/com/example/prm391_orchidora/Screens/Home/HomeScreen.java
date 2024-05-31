package com.example.prm391_orchidora.Screens.Home;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.prm391_orchidora.Models.Orchid;
import com.example.prm391_orchidora.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrchidAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_layout);
//        ImageView imageView = findViewById(R.id.footballer_image);
//        Glide.with(this).load("https://upload.wikimedia.org/wikipedia/en/thumb/c/c3/Flag_of_France.svg/125px-Flag_of_France.svg.png").into(imageView);

        ChipGroup chipGroup = findViewById(R.id.chipGroup);

        // Create a list of filters or chips
        List<String> filters = Arrays.asList("Dendrobium", "Cattleya", "Phalaenopsis", "Cymbidium", "Miltonia", "Oncidium");
        List<String> checkedChips = new ArrayList<>();

        for (String filter : filters) {
            Chip chip = new Chip(this);
            chip.setText(filter);
            chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
            chip.setChipStrokeColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
            chip.setChipStrokeWidth(4.6f);
            chip.setChipCornerRadius(40f);

            // Set click listener or other properties as needed

            chip.setOnClickListener(v -> {
                Chip selectedChip = (Chip) v;
                String selectedFilter = selectedChip.getText().toString();
                if (checkedChips.contains(selectedFilter)) {
                    chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
                    checkedChips.remove(selectedFilter);
                    selectedChip.setChecked(false);
                } else {
                    chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#BFA4DC")));
                    chip.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    checkedChips.add(selectedFilter);
                    selectedChip.setChecked(true);
                }

            });

            chipGroup.addView(chip);
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list of orchids
        List<Orchid> orchidList = new ArrayList<>();
        orchidList.add(new Orchid("https://www.thespruce.com/thmb/gA9XUhd0xBF-tLvADZLwYFCg9CU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/flower-orchid-brassavola-535480623-454a77fbd13d41509771c17de4c7bb10.jpg", "Moth Orchid", "Orchidaceae", 19.99));
        orchidList.add(new Orchid("https://www.thespruce.com/thmb/-_pfiR6xFXDv7A8kB3-bsHiE8Zk=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/CatasetumOrchid-c15ebf0079814aa193f806cf75f84c22.jpg", "Cattleya Orchids", "Cattleya", 24.99));
        orchidList.add(new Orchid("https://hips.hearstapps.com/hmg-prod/images/vanda-orchid-types-1587739024.jpg?crop=1.00xw:0.976xh;0,0.0242xh&resize=980:*", "Dendrobium Orchids", "Dendrobium", 24.99));
        orchidList.add(new Orchid("https://www.thespruce.com/thmb/gA9XUhd0xBF-tLvADZLwYFCg9CU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/flower-orchid-brassavola-535480623-454a77fbd13d41509771c17de4c7bb10.jpg", "Moth Orchid", "Orchidaceae", 19.99));
        orchidList.add(new Orchid("https://www.thespruce.com/thmb/-_pfiR6xFXDv7A8kB3-bsHiE8Zk=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/CatasetumOrchid-c15ebf0079814aa193f806cf75f84c22.jpg", "Cattleya Orchids", "Cattleya", 24.99));
        orchidList.add(new Orchid("https://hips.hearstapps.com/hmg-prod/images/vanda-orchid-types-1587739024.jpg?crop=1.00xw:0.976xh;0,0.0242xh&resize=980:*", "Dendrobium Orchids", "Dendrobium", 24.99));
        // Add more orchids as needed

        adapter = new OrchidAdapter(orchidList);
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
