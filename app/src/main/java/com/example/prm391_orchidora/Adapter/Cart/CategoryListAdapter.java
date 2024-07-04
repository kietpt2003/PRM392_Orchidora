package com.example.prm391_orchidora.Adapter.Cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Models.Category.Category;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.R;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private List<CategoryResponse> categoryList;
    private Context context;
    private OnCategoryEditListener editListener;
    public CategoryListAdapter(List<CategoryResponse> categoryList, Context context,OnCategoryEditListener onCategoryEditListener) {
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
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        public View editBtn;
        //        private ImageView editBtn;
//        private ImageView deleteBtn;
        TextView cateName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            editBtn = itemView.findViewById(R.id.editBtn);
//            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            cateName = itemView.findViewById(R.id.cateName);
            editBtn = itemView.findViewById(R.id.editBtn);
//            deleteBtn = itemView.findViewById(R.id.deleteBtn);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        Intent intent = new Intent(context, OrchidDetailScreen.class);
//                        context.startActivity(intent);
//                    }
//                }
//            });
        }
    }
    // Interface for edit callbacks
    public interface OnCategoryEditListener {
        void onEdit(CategoryResponse category);
    }
}
