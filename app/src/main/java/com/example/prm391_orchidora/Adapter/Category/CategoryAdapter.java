package com.example.prm391_orchidora.Adapter.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prm391_orchidora.Models.Category.Category;
import com.example.prm391_orchidora.Models.Category.CategoryResponse;
import com.example.prm391_orchidora.R;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private List<CategoryResponse> categoryList;
    private Context context;

    public CategoryAdapter(List<CategoryResponse> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    @Override
    public CategoryResponse getItem(int position) {
        return categoryList != null ? categoryList.get(position) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.orchid_type_item, viewGroup, false);
        TextView cateName = rootView.findViewById(R.id.cateName);
        cateName.setText(categoryList.get(i).getName());
        return rootView;
    }
}
