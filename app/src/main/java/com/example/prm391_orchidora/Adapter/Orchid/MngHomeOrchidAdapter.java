package com.example.prm391_orchidora.Adapter.Orchid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Models.Orchid.MngHomeOrchid;
import com.example.prm391_orchidora.Models.Orchid.Orchid;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Orchid.MngOrchidDetailScreen;
import com.example.prm391_orchidora.Screens.Orchid.OrchidDetailScreen;

import java.util.List;

public class MngHomeOrchidAdapter extends RecyclerView.Adapter<MngHomeOrchidAdapter.ViewHolder>{

    private List<MngHomeOrchid> orchidList;
    private static Context orchidContext;

    public MngHomeOrchidAdapter(List<MngHomeOrchid> orchidList, Context orchidContext) {
        this.orchidList = orchidList;
        this.orchidContext = orchidContext;
    }

    @NonNull
    @Override
    public MngHomeOrchidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mng_home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MngHomeOrchid orchid = orchidList.get(position);
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
        private TextView textQuantity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageOrchid = itemView.findViewById(R.id.image_orchid);
            textName = itemView.findViewById(R.id.text_name);
            textCategory = itemView.findViewById(R.id.text_category);
            textPrice = itemView.findViewById(R.id.text_price);
            textQuantity = itemView.findViewById(R.id.text_quantity);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(orchidContext, MngOrchidDetailScreen.class);
                    orchidContext.startActivity(intent);
                }
            });
        }

        void bind(MngHomeOrchid orchid) {
            Glide.with(itemView.getContext())
                    .load(orchid.getImageUrl())
                    .into(imageOrchid);

            textQuantity.setText("("+orchid.getQuantity()+")");
            textName.setText(orchid.getName());
            textCategory.setText(orchid.getCategory());
            textPrice.setText(String.format("$%.2f", orchid.getPrice()));
        }
    }

}
