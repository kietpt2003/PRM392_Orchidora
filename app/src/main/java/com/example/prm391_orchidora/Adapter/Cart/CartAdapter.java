package com.example.prm391_orchidora.Adapter.Cart;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Models.CartItem;
import com.example.prm391_orchidora.Models.Orchid.OrchidOld;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Orchid.OrchidDetailScreen;
import com.example.prm391_orchidora.Services.CartService;
import com.example.prm391_orchidora.Utils.Database;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> cartItemList;
    private static OnQuantityChangedListener onQuantityChangedListener;
    private AlertDialog.Builder alertDialog;
    private Database db;
    private String token;

    public CartAdapter(List<CartItem> cartItemList, OnQuantityChangedListener onQuantityChangedListener, Database db, String token) {
        this.cartItemList = cartItemList;
        this.onQuantityChangedListener = onQuantityChangedListener;
        this.db = db;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_orchid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.bind(cartItem, db, token);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkboxSelect;
        private ImageView imageOrchid;
        private TextView textName;
        private TextView textCategory;
        private TextView textPrice;
        private TextView textQuantity;
        private ImageView buttonDecreaseQuantity;
        private ImageView buttonIncreaseQuantity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkboxSelect = itemView.findViewById(R.id.checkbox_select);
            imageOrchid = itemView.findViewById(R.id.image_orchid);
            textName = itemView.findViewById(R.id.text_name);
            textCategory = itemView.findViewById(R.id.text_category);
            textPrice = itemView.findViewById(R.id.text_price);
            textQuantity = itemView.findViewById(R.id.text_quantity);
            buttonDecreaseQuantity = itemView.findViewById(R.id.button_decrease_quantity);
            buttonIncreaseQuantity = itemView.findViewById(R.id.button_increase_quantity);
        }

        void bind(CartItem cartItem, Database db, String token) {
            OrchidResponse orchid = cartItem.getOrchid();
            Glide.with(itemView.getContext())
                    .load(orchid.getImg())
                    .into(imageOrchid);

            textName.setText(orchid.getName());
            textCategory.setText(orchid.getCategory().getName());
            textPrice.setText(orchid.getPrice()+" VND");
            textQuantity.setText(cartItem.getQuantity()+"");
            checkboxSelect.setChecked(cartItem.isSelected());

            CartService cartService = new CartService(db,token);

            buttonDecreaseQuantity.setOnClickListener(v -> {
                int newQuantity = cartItem.getQuantity() - 1;
                if (newQuantity >= 1) {
                    cartItem.setQuantity(newQuantity);
                    textQuantity.setText(String.valueOf(newQuantity));
                    db.queryData("UPDATE OrchidList SET quantity = " + newQuantity + " WHERE id = '" + orchid.getId() + "'");
                    onQuantityChangedListener.onQuantityChangedSuccess("Decrease Success");
                } else {
                    onQuantityChangedListener.onQuantityChangedDelete(orchid.getId());
                }
            });

            buttonIncreaseQuantity.setOnClickListener(v -> {
                int newQuantity = cartItem.getQuantity() + 1;
                if (newQuantity <= orchid.getQuantity()){
                    cartItem.setQuantity(newQuantity);
                    textQuantity.setText(String.valueOf(newQuantity));
                    db.queryData("UPDATE OrchidList SET quantity = " + newQuantity + " WHERE id = '" + orchid.getId() + "'");
                    onQuantityChangedListener.onQuantityChangedSuccess("Increase Success");
                } else {
                    onQuantityChangedListener.onQuantityChangedFail("Quantity exceeds available stock.");
                }
            });

            checkboxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                cartItem.setSelected(isChecked);
                db.queryData("UPDATE OrchidList SET isSelected = " + (isChecked ? 1 : 0) + " WHERE id = '" + orchid.getId() + "'");
                onQuantityChangedListener.onQuantityChangedSuccess(isChecked ?"Select success":"Unselect success");
            });
        }
    }


    public interface OnQuantityChangedListener {
        void onQuantityChangedSuccess(String content);
        void onQuantityChangedDelete(String id);
        void onQuantityChangedFail(String content);
    }
}