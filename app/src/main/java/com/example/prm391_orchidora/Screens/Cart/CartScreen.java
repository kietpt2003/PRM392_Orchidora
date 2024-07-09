package com.example.prm391_orchidora.Screens.Cart;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
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
import com.example.prm391_orchidora.Controller.PaymentController;
import com.example.prm391_orchidora.Models.CartItem;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.Models.Order.CreateOrderRequest;
import com.example.prm391_orchidora.Models.Order.OrderItemRequest;
import com.example.prm391_orchidora.Models.Order.OrderResponse;
import com.example.prm391_orchidora.Models.Payment.PaymentResponseData;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Adapter.Orchid.OrchidAdapter;
import com.example.prm391_orchidora.Screens.Orchid.OrchidDetailScreen;
import com.example.prm391_orchidora.Screens.Order.OrderDetailScreen;
import com.example.prm391_orchidora.Services.CartService;
import com.example.prm391_orchidora.Utils.Database;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.ArrayList;
import java.util.List;

public class CartScreen extends AppCompatActivity implements CartAdapter.OnQuantityChangedListener, OrchidController.OrchidGetCallback, PaymentController.PostPaymentCallBack, PaymentController.GetPaymentByIdCallBack {

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
    private CheckBox checkbox_all;
    private ImageView backButton;
    private String token = "";
    private TextView cart_text;
    private TextView text_price;
    private int total;
    private TextView buyBtn;
    private PaymentController paymentController;
    private boolean navigatedToBrowser = false;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.cart_layout);

        token = new TokenManager().getToken(this);

        text_price = findViewById(R.id.txt_price);
        buyBtn = findViewById(R.id.buyBtn);
        handlePayment();

        handleDB();
        handleGetCart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.cart_status_bar));
        }

        handleBack();
        handleClickCheckAll();

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
        orchidController = new OrchidController( this, token);
        orchidController.fetchOrchids("", "ACTIVE");
        handleGetCheckAll();
        if (navigatedToBrowser) {
            navigatedToBrowser = false; // Reset the flag

            // Handle your custom logic here, e.g., showing a message or updating the UI
            paymentController = new PaymentController((PaymentController.GetPaymentByIdCallBack) this, token);
            if(orderId != null){
                paymentController.fetchOrderById(orderId);
            }
        }
    }

    // Method to open a web page
    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            navigatedToBrowser = true; // Set the flag to true when the URL is opened
            startActivity(intent);
        }
    }

    private void handlePayment(){
        buyBtn.setOnClickListener(v->{
            CartService cartService = new CartService(db, token);
            cartService.getCarts(cartItems -> {
                if(cartItems.isEmpty()){
                    showAlert("Empty cart", "Your cart is empty!");
                } else {
                    List<OrderItemRequest> listItems = new ArrayList<>();
                    for (CartItem item: cartItems) {
                        if (item.isSelected()){
                            OrderItemRequest orderItemRequest = new OrderItemRequest(item.getOrchid().getId(), item.getQuantity());
                            listItems.add(orderItemRequest);
                        }
                    }
                    if (listItems.isEmpty()){
                        showAlert("Empty payment", "Please choose orchid(s) for paying!");
                    } else {
                        paymentController = new PaymentController((PaymentController.PostPaymentCallBack) this, token);
                        CreateOrderRequest createOrderRequest = new CreateOrderRequest(listItems);
                        paymentController.createOrder(createOrderRequest);
                    }
                }
            });

        });
    }

    private void showAlert(String title, String content) {
        alertDialog = new AlertDialog.Builder(CartScreen.this);
        alertDialog.setTitle(title)
                .setMessage(content)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void handleGetCartNumber() {
        CartService cartService = new CartService(db, token);
        cart_text = findViewById(R.id.cart_text);
        cartService.getCarts(cartItems -> {
            if (cartItems.isEmpty()) {
                cart_text.setText("Cart(" + 0 + ")");
            } else {
                cart_text.setText("Cart(" + cartItems.size() + ")");
            }
        });
    }

    private void handleGetCheckAll() {
        CartService cartService = new CartService(db, token);
        checkbox_all = findViewById(R.id.checkbox_all);
        cartService.getCarts(cartItems -> {
            if (!cartItems.isEmpty()) {
                boolean isCheckAll = true;
                total = 0;
                for (CartItem item : cartItems) {
                    if (!item.isSelected()) {
                        isCheckAll = false;
                    } else {
                        total += item.getOrchid().getPrice() * item.getQuantity();
                    }

                }
                checkbox_all.setChecked(isCheckAll);
                text_price.setText(total + " VND");
            } else {
                checkbox_all.setChecked(false);
                text_price.setText("0 VND");
            }
        });
    }

    private void handleDB() {
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

    private void handleClickCheckAll() {
        checkbox_all = findViewById(R.id.checkbox_all);
        checkbox_all.setOnClickListener(v -> {
            boolean isChecked = checkbox_all.isChecked();
            checkbox_all.setChecked(isChecked);
            db.queryData("UPDATE OrchidList SET isSelected = " + (isChecked ? 1 : 0));
            Toast.makeText(this, isChecked ? "Select all success" : "Deselect all success", Toast.LENGTH_SHORT).show();
            handleGetCart();
        });
    }

    private void handleGetCart() {
        CartService cartService = new CartService(db, token);
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
                 total = 0;
                for (CartItem item : cartItems) {
                    if (item.isSelected()) {
                        total += item.getOrchid().getPrice() * item.getQuantity();
                    }
                }
                text_price.setText(total + " VND");
                emptyCartText.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
                adapter = new CartAdapter(cartItemList, this, db, token);
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
        db.queryData("DELETE FROM OrchidList WHERE id = '" + id + "'");
        Toast.makeText(CartScreen.this, "Remove item success", Toast.LENGTH_SHORT).show();
        handleGetCart();
    }

    @Override
    public void onQuantityChangedFail(String content) {
        showAlert("Warning", content);
    }

    @Override
    public void onChangeSelect() {
        handleGetCheckAll();
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

    @Override
    public void onPostPaymentSuccess(PaymentResponseData paymentResponseData) {
        orderId = paymentResponseData.getId();
        openWebPage(paymentResponseData.getCheckoutUrl());
    }

    @Override
    public void onPostPaymentError(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetPaymentByIdSuccess(OrderResponse orderResponse) {
        Intent intent = new Intent(this, OrderDetailScreen.class);
        intent.putExtra("orderResponse", orderResponse);
        this.startActivity(intent);
    }

    @Override
    public void onGetPaymentByIdError(ErrorResponse errorResponse) {
        Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
