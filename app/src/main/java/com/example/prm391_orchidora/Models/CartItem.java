package com.example.prm391_orchidora.Models;

import com.example.prm391_orchidora.Models.Orchid.OrchidOld;

public class CartItem {

    private OrchidOld orchid;
    private boolean isSelected;
    private int quantity;

    public CartItem() {
    }

    public CartItem(OrchidOld orchid, boolean isSelected, int quantity) {
        this.orchid = orchid;
        this.isSelected = isSelected;
        this.quantity = quantity;
    }

    public OrchidOld getOrchid() {
        return orchid;
    }

    public void setOrchid(OrchidOld orchid) {
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
