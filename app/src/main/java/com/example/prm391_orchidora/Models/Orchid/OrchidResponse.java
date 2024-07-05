package com.example.prm391_orchidora.Models.Orchid;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.prm391_orchidora.Models.Category.Category;

import java.io.Serializable;

public class OrchidResponse implements Parcelable {
    private String id;
    private int price;
    private Category category;
    private String color;
    private String description;
    private String img;
    private int quantity;
    private String status;
    private String name;

    public OrchidResponse(String id, int price, Category category, String color, String description, String img, int quantity, String status, String name) {
        this.id = id;
        this.price = price;
        this.category = category;
        this.color = color;
        this.description = description;
        this.img = img;
        this.quantity = quantity;
        this.status = status;
        this.name = name;
    }

    protected OrchidResponse(Parcel in) {
        id = in.readString();
        price = in.readInt();
        category = in.readParcelable(Category.class.getClassLoader());
        color = in.readString();
        description = in.readString();
        img = in.readString();
        quantity = in.readInt();
        status = in.readString();
        name = in.readString();
    }

    public static final Creator<OrchidResponse> CREATOR = new Creator<OrchidResponse>() {
        @Override
        public OrchidResponse createFromParcel(Parcel in) {
            return new OrchidResponse(in);
        }

        @Override
        public OrchidResponse[] newArray(int size) {
            return new OrchidResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeInt(price);
        parcel.writeParcelable(category, i);
        parcel.writeString(color);
        parcel.writeString(description);
        parcel.writeString(img);
        parcel.writeInt(quantity);
        parcel.writeString(status);
        parcel.writeString(name);
    }
}
