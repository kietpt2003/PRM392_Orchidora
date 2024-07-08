package com.example.prm391_orchidora.Screens.Cart;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_orchidora.Adapter.Cart.CartAdapter;
import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Models.CartItem;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Adapter.Orchid.OrchidAdapter;
import com.example.prm391_orchidora.Screens.Orchid.OrchidDetailScreen;
import com.example.prm391_orchidora.Services.CartService;
import com.example.prm391_orchidora.Utils.Database;
import com.example.prm391_orchidora.Utils.TokenManager;
import java.util.List;

public class CartScreen extends AppCompatActivity implements CartAdapter.OnQuantityChangedListener, OrchidController.OrchidGetCallback {

    private RecyclerView recyclerView;
    private AlertDialog.Builder alertDialog;
    private CardView cardView;
    private Database db;
    private CartAdapter adapter;
    private List<CartItem> cartItemList;
    private TextView emptyCartText;
    private RecyclerView recyclerViewMayLike;
    private OrchidController orchidController;
    private OrchidAdapter orchidAdapter;

    private ImageView backButton;
    private String token = "";
    private TextView cart_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.cart_layout);

        token = new TokenManager().getToken(this);

        handleDB();
        handleGetCart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar ));
        }

        handleBack();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleGetCartNumber();
        //You may like
        orchidController = new OrchidController((OrchidController.OrchidGetCallback) this, token);
        orchidController.fetchOrchids("", "ACTIVE");
    }

    private void showAlert(String title,String content){
        alertDialog = new AlertDialog.Builder(CartScreen.this);
        alertDialog.setTitle(title)
                .setMessage(content)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void handleGetCartNumber(){
        CartService cartService = new CartService(db, token);
        cart_text = findViewById(R.id.cart_text);
        cartService.getCarts(cartItems -> {
            if (cartItems.isEmpty()) {
                cart_text.setText("Cart("+0 + ")");
            } else {
                cart_text.setText("Cart("+cartItems.size() + ")");
            }
        });
    }

    private void handleDB(){
        db = new Database(this, "OrchidList.sqlite", null, 1);
        // Tạo bảng OrchidList nếu chưa tồn tại
        db.queryData("CREATE TABLE IF NOT EXISTS OrchidList(id NVARCHAR(200), quantity INTEGER, isSelected INTERGER)");
    }

    private void handleBack() {
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void handleGetCart() {
        CartService cartService = new CartService(db,token);
        recyclerView = findViewById(R.id.recycler_view);
        emptyCartText = findViewById(R.id.empty_cart_text);
        cardView = findViewById(R.id.CardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartService.getCarts(cartItems -> {
            cartItemList = cartItems;
            if (cartItemList.isEmpty()) {
                emptyCartText.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.GONE);
            } else {
                emptyCartText.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
                adapter = new CartAdapter(cartItemList, this,db, token);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onQuantityChangedSuccess(String content) {
        Toast.makeText(CartScreen.this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQuantityChangedDelete(String id) {
        db.queryData("DELETE FROM OrchidList WHERE id = '" + id+"'");
        Toast.makeText(CartScreen.this, "Remove item success", Toast.LENGTH_SHORT).show();
        handleGetCart();
    }

    @Override
    public void onQuantityChangedFail(String content) {
        showAlert("Warning", content);
    }

    @Override
    public void onOrchidSuccessGet(List<OrchidResponse> orchids) {
        recyclerViewMayLike = findViewById(R.id.recycler_view_you_may_like);
        recyclerViewMayLike.setLayoutManager(new LinearLayoutManager(this));
        orchidAdapter = new OrchidAdapter(orchids, this, token);
        recyclerViewMayLike.setAdapter(orchidAdapter);
    }

    @Override
    public void onOrchidErrorGet(ErrorResponse errorMessage) {
        Toast.makeText(this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
