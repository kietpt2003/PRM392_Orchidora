package com.example.prm391_orchidora.Adapter.Cart;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Controller.CategoryController;
import com.example.prm391_orchidora.Models.Category.Category;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Category.ManageCategoryScreen;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private List<CategoryResponse> categoryList;
    private Context context;
    private OnCategoryEditListener editListener;

    public CategoryListAdapter(List<CategoryResponse> categoryList, Context context, OnCategoryEditListener onCategoryEditListener) {
        this.context = context;
        this.categoryList = categoryList;
        this.editListener = onCategoryEditListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_manage_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryResponse category = categoryList.get(position);
        holder.cateName.setText(category.getName());

        // Handle edit button click
        holder.editBtn.setOnClickListener(v -> editListener.onEdit(category));

        // Handle delete button click
        holder.deleteBtn.setOnClickListener(v -> {
            Log.d("CategoryListAdapter", "Delete button clicked for category: " + category.getName());
            showDeleteConfirmationDialog(category.getId());
        });
    }

    private void showDeleteConfirmationDialog(String categoryId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Category");
        builder.setMessage("Are you sure you want to delete this category?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Call API to delete the category
            CategoryController categoryController = new CategoryController(new CategoryController.CategoryGetCallBack() {
                @Override
                public void onCategorySuccessGet(List<CategoryResponse> categories) {
                    // Not used in this context
                }

                @Override
                public void onCategoryErrorGet(ErrorResponse errorResponse) {
                    Toast.makeText(context, "Error: " + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCategorySuccessPut(CategoryResponse category) {
                    // Not used in this context
                }

                @Override
                public void onCategorySuccessDelete() {
                    // Refresh category list after successful delete
                    if (context instanceof ManageCategoryScreen) {
                        ((ManageCategoryScreen) context).fetchCategories();
                    }
                }

                @Override
                public void onCategorySuccessPost(CategoryResponse category) {

                }
            }, new TokenManager().getToken(context));
            categoryController.deleteCategory(categoryId);
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cateName;
        ImageView editBtn;
        ImageView deleteBtn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cateName = itemView.findViewById(R.id.cateName);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    // Interface for edit callbacks
    public interface OnCategoryEditListener {
        void onEdit(CategoryResponse category);
    }
}

