package com.example.prm391_orchidora.Screens.Cart;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.Cart.CartAdapter;
import com.example.prm391_orchidora.Models.CartItem;
import com.example.prm391_orchidora.Models.Category.Category;
import com.example.prm391_orchidora.Models.Orchid.OrchidOld;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Adapter.Orchid.OrchidAdapter;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class CartScreen extends AppCompatActivity implements CartAdapter.OnQuantityChangedListener {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<CartItem> cartItemList;

    private RecyclerView recyclerViewMayLike;
    private OrchidAdapter orchidAdapter;

    private ImageView backButton;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.cart_layout);

        token = new TokenManager().getToken(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar ));
        }

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            finish();
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItemList = new ArrayList<>();
        // Sample data
        cartItemList.add(new CartItem(new OrchidOld("https://www.thespruce.com/thmb/gA9XUhd0xBF-tLvADZLwYFCg9CU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/flower-orchid-brassavola-535480623-454a77fbd13d41509771c17de4c7bb10.jpg", "Moth Orchid", "Orchidaceae", 19.99), false, 1));
        cartItemList.add(new CartItem(new OrchidOld("https://www.thespruce.com/thmb/-_pfiR6xFXDv7A8kB3-bsHiE8Zk=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/CatasetumOrchid-c15ebf0079814aa193f806cf75f84c22.jpg", "Cattleya Orchids", "Cattleya", 24.99), true, 2));
        cartItemList.add(new CartItem(new OrchidOld("https://hips.hearstapps.com/hmg-prod/images/vanda-orchid-types-1587739024.jpg?crop=1.00xw:0.976xh;0,0.0242xh&resize=980:*", "Dendrobium Orchids", "Dendrobium", 24.99), false, 3));

        adapter = new CartAdapter(cartItemList, this);
        recyclerView.setAdapter(adapter);

        recyclerViewMayLike = findViewById(R.id.recycler_view_you_may_like);
        recyclerViewMayLike.setLayoutManager(new LinearLayoutManager(this));

        List<OrchidResponse> orchidList = new ArrayList<>();
        orchidList.add(new OrchidResponse("1", 19, new Category("1","Orchidaceae"), "Red", "So Beautiful Flower", "https://www.thespruce.com/thmb/gA9XUhd0xBF-tLvADZLwYFCg9CU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/flower-orchid-brassavola-535480623-454a77fbd13d41509771c17de4c7bb10.jpg", 6, "available", "Moth Orchid"));
        orchidList.add(new OrchidResponse("2", 24, new Category("1","Cattleya"), "Purple", "So Beautiful Flower", "https://www.thespruce.com/thmb/-_pfiR6xFXDv7A8kB3-bsHiE8Zk=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/CatasetumOrchid-c15ebf0079814aa193f806cf75f84c22.jpg", 1, "unavailable", "Cattleya Orchids"));
        orchidList.add(new OrchidResponse("3", 30, new Category("1","Dendrobium"), "Orange", "So Beautiful Flower", "https://hips.hearstapps.com/hmg-prod/images/vanda-orchid-types-1587739024.jpg?crop=1.00xw:0.976xh;0,0.0242xh&resize=980:*", 2, "available", "Dendrobium Orchids"));
        orchidList.add(new OrchidResponse("4", 29, new Category("1","Orchidaceae"), "Blue", "So Beautiful Flower", "https://www.thespruce.com/thmb/gA9XUhd0xBF-tLvADZLwYFCg9CU=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/flower-orchid-brassavola-535480623-454a77fbd13d41509771c17de4c7bb10.jpg", 8, "available", "Moth Orchid"));
        orchidList.add(new OrchidResponse("5", 25, new Category("1","Cattleya"), "Gray", "So Beautiful Flower", "https://www.thespruce.com/thmb/-_pfiR6xFXDv7A8kB3-bsHiE8Zk=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/CatasetumOrchid-c15ebf0079814aa193f806cf75f84c22.jpg", 10, "available", "Cattleya Orchids"));
        orchidList.add(new OrchidResponse("6", 14, new Category("1","Dendrobium"), "Yellow", "So Beautiful Flower", "https://hips.hearstapps.com/hmg-prod/images/vanda-orchid-types-1587739024.jpg?crop=1.00xw:0.976xh;0,0.0242xh&resize=980:*", 5, "unavailable", "Dendrobium Orchids"));


        orchidAdapter = new OrchidAdapter(orchidList, this, token);
        recyclerViewMayLike.setAdapter(orchidAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onQuantityChanged() {

    }
}
