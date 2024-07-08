package com.example.prm391_orchidora.Services;

import android.database.Cursor;
import android.util.Log;

import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Models.CartItem;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.Utils.Database;

import java.util.ArrayList;
import java.util.List;

public class CartService implements OrchidController.OrchidGetByIdCartCallback {
    private Database db;
    private OrchidController orchidController;
    private String token;
    private List<CartItem> cartItemList;
    private CartServiceCallback callback;
    private int pendingRequests;

    public CartService(Database db, String token) {
        this.db = db;
        this.token = token;
        this.cartItemList = new ArrayList<>();
    }

    public void getCarts(CartServiceCallback callback) {
        this.callback = callback;
        Cursor cursor = db.getData("SELECT * FROM OrchidList");
        pendingRequests = cursor.getCount();

        if (pendingRequests == 0) {
            callback.onCartItemsLoaded(cartItemList);
            return;
        }

        while (cursor.moveToNext()) {
            String orchidId = cursor.getString(0);
            int quantity = cursor.getInt(1);
            int isSelected = cursor.getInt(2);
            orchidController = new OrchidController(this, token);
            orchidController.fetchOrchidByIdCart(orchidId, quantity, isSelected == 1);
        }
    }

    @Override
    public void onOrchidByIdCartSuccessGet(OrchidResponse orchid, int quantity, boolean isSelected) {
        CartItem cartItem = new CartItem(orchid, isSelected, quantity);
        cartItemList.add(cartItem);
        checkAllRequestsCompleted();
    }

    @Override
    public void onOrchidByIdCartErrorGet(ErrorResponse errorMessage) {
        Log.e("CartService", errorMessage.getMessage());
        checkAllRequestsCompleted();
    }

    private void checkAllRequestsCompleted() {
        pendingRequests--;
        if (pendingRequests <= 0 && callback != null) {
            callback.onCartItemsLoaded(cartItemList);
        }
    }

    public interface CartServiceCallback {
        void onCartItemsLoaded(List<CartItem> cartItems);
    }
}
