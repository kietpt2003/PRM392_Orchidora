package com.example.prm391_orchidora.Adapter.Orchid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_orchidora.Controller.OrchidController;
import com.example.prm391_orchidora.Models.ErrorResponse;
import com.example.prm391_orchidora.Models.Orchid.OrchidResponse;
import com.example.prm391_orchidora.R;
import com.example.prm391_orchidora.Screens.Orchid.MngOrchidDetailScreen;

import java.util.List;

public class MngOrchidAdapter extends RecyclerView.Adapter<MngOrchidAdapter.ViewHolder>implements OrchidController.OrchidGetByIdCallback{

    private final List<OrchidResponse> orchidList;
    private final Context orchidContext;
    private OrchidController orchidController;
    private final String token;
    private static final int REQUEST_CODE_ORCHID_DETAIL = 2;

    public MngOrchidAdapter(List<OrchidResponse> orchidList, Context orchidContext, String token) {
        this.orchidList = orchidList;
        this.orchidContext = orchidContext;
        this.token = token;
    }

    @NonNull
    @Override
    public MngOrchidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mng_home_item, parent, false);
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return orchidList.size();
    }

    @Override
    public void onOrchidByIdSuccessGet(OrchidResponse orchid) {
        Intent intent = new Intent(orchidContext, MngOrchidDetailScreen.class);
        intent.putExtra("currentOrchid", orchid);
//        orchidContext.startActivity(intent);
        ((Activity) orchidContext).startActivityForResult(intent, REQUEST_CODE_ORCHID_DETAIL);
    }

    @Override
    public void onOrchidByIdErrorGet(ErrorResponse errorMessage) {
        Toast.makeText(orchidContext, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageOrchid;
        private TextView textName;
        private TextView textCategory;
        private TextView textPrice;
        private TextView textQuantity;
        private TextView textStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageOrchid = itemView.findViewById(R.id.image_orchid);
            textName = itemView.findViewById(R.id.text_name);
            textCategory = itemView.findViewById(R.id.text_category);
            textPrice = itemView.findViewById(R.id.text_price);
            textQuantity = itemView.findViewById(R.id.text_quantity);
            textStatus = itemView.findViewById(R.id.text_status);
        }

        void bind(OrchidResponse orchid) {
            Glide.with(itemView.getContext())
                    .load(orchid.getImg())
                    .into(imageOrchid);

            textQuantity.setText("("+orchid.getQuantity()+")");
            textName.setText(orchid.getName());
            textCategory.setText(orchid.getCategory().getName());
            textPrice.setText("$"+orchid.getPrice());
            if (orchid.getStatus().equals("ACTIVE")){
                textStatus.setText("AVAILABLE");
                textStatus.setTextColor(Color.parseColor("#4CAF50")); //Green
            } else {
                textStatus.setText("UNAVAILABLE");
                textStatus.setTextColor(Color.parseColor("#F44336")); //Red
            }
        }
    }

}
