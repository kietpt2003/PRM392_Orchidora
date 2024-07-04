package com.example.prm391_orchidora.Adapter.Orchid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Orchid.OrchidDetailScreen;
import com.example.prm391_orchidora.Utils.TokenManager;

import java.util.List;

public class OrchidAdapter extends RecyclerView.Adapter<OrchidAdapter.ViewHolder> implements OrchidController.OrchidGetByIdCallback {

    private List<OrchidResponse> orchidList;
    private static Context orchidContext;
    private OrchidController orchidController;
    private String token = "";

    public OrchidAdapter(List<OrchidResponse> orchidList, Context orchidContext, String token) {
        this.orchidList = orchidList;
        this.orchidContext = orchidContext;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_orchid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrchidResponse orchid = orchidList.get(position);
        holder.bind(orchid);
        holder.itemView.setOnClickListener(v -> {
            if (position != RecyclerView.NO_POSITION) {
                orchidController = new OrchidController(this, token);
                orchidController.fetchOrchidById(orchid.getId());
//                onOrchidClickListener.onOrchidClick(orchid);
//                Intent intent = new Intent(orchidContext, OrchidDetailScreen.class);
//                orchidContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orchidList.size();
    }

    @Override
    public void onOrchidByIdSuccessGet(OrchidResponse orchid) {
        Intent intent = new Intent(orchidContext, OrchidDetailScreen.class);
        intent.putExtra("currentOrchid", orchid);
        orchidContext.startActivity(intent);
    }

    @Override
    public void onOrchidByIdErrorGet(ErrorResponse errorMessage) {

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
//            itemView.setOnClickListener(v -> {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        Intent intent = new Intent(orchidContext, OrchidDetailScreen.class);
//                        orchidContext.startActivity(intent);
//                    }
//            });
        }

        void bind(OrchidResponse orchid) {
            Glide.with(itemView.getContext())
                    .load(orchid.getImg())
                    .into(imageOrchid);

            textName.setText(orchid.getName());
            textCategory.setText(orchid.getCategory());
            textPrice.setText(orchid.getPrice()+"");
        }
    }
}
