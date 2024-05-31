package com.example.prm391_orchidora.Screens.Cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Models.CartItem;
import com.example.prm391_orchidora.Models.Orchid;
import com.example.prm391_orchidora.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> cartItemList;
    private static OnQuantityChangedListener onQuantityChangedListener;

    public CartAdapter(List<CartItem> cartItemList, OnQuantityChangedListener onQuantityChangedListener) {
        this.cartItemList = cartItemList;
        this.onQuantityChangedListener = onQuantityChangedListener;
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
        holder.bind(cartItem);
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

        void bind(CartItem cartItem) {
            Orchid orchid = cartItem.getOrchid();
            Glide.with(itemView.getContext())
                    .load(orchid.getImageUrl())
                    .into(imageOrchid);

            textName.setText(orchid.getName());
            textCategory.setText(orchid.getCategory());
            textPrice.setText(String.format("$%.2f", orchid.getPrice()));
            textQuantity.setText(String.valueOf(cartItem.getQuantity()));
            checkboxSelect.setChecked(cartItem.isSelected());

            buttonDecreaseQuantity.setOnClickListener(v -> {
                int newQuantity = Math.max(cartItem.getQuantity() - 1, 1);
                cartItem.setQuantity(newQuantity);
                textQuantity.setText(String.valueOf(newQuantity));
                onQuantityChangedListener.onQuantityChanged();
            });

            buttonIncreaseQuantity.setOnClickListener(v -> {
                int newQuantity = cartItem.getQuantity() + 1;
                cartItem.setQuantity(newQuantity);
                textQuantity.setText(String.valueOf(newQuantity));
                onQuantityChangedListener.onQuantityChanged();
            });

            checkboxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                cartItem.setSelected(isChecked);
                onQuantityChangedListener.onQuantityChanged();
            });
        }
    }

    interface OnQuantityChangedListener {
        void onQuantityChanged();
    }
}