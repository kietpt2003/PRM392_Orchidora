package com.example.prm391_orchidora.Models;

public class CartItem {

    private Orchid orchid;
    private boolean isSelected;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Orchid orchid, boolean isSelected, int quantity) {
        this.orchid = orchid;
        this.isSelected = isSelected;
        this.quantity = quantity;
    }

    public Orchid getOrchid() {
        return orchid;
    }

    public void setOrchid(Orchid orchid) {
        this.orchid = orchid;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
