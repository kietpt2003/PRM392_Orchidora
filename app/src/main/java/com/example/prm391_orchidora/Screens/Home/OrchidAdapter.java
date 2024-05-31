package com.example.prm391_orchidora.Screens.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Models.Orchid;
import com.example.prm391_orchidora.R;

import java.util.List;

public class OrchidAdapter extends RecyclerView.Adapter<OrchidAdapter.ViewHolder> {

    private List<Orchid> orchidList;

    public OrchidAdapter(List<Orchid> orchidList) {
        this.orchidList = orchidList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_orchid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orchid orchid = orchidList.get(position);
        holder.bind(orchid);
    }

    @Override
    public int getItemCount() {
        return orchidList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageOrchid;
        private TextView textName;
        private TextView textCategory;
        private TextView textPrice;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageOrchid = itemView.findViewById(R.id.image_orchid);
            textName = itemView.findViewById(R.id.text_name);
            textCategory = itemView.findViewById(R.id.text_category);
            textPrice = itemView.findViewById(R.id.text_price);
        }

        void bind(Orchid orchid) {
            Glide.with(itemView.getContext())
                    .load(orchid.getImageUrl())
                    .into(imageOrchid);

            textName.setText(orchid.getName());
            textCategory.setText(orchid.getCategory());
            textPrice.setText(String.format("$%.2f", orchid.getPrice()));
        }
    }
}