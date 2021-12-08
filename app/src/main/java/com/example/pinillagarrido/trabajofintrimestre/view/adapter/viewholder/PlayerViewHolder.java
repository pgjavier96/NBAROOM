package com.example.pinillagarrido.trabajofintrimestre.view.adapter.viewholder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinillagarrido.trabajofintrimestre.R;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Player;
import com.example.pinillagarrido.trabajofintrimestre.view.activity.CreateplayerFragment;


public class PlayerViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName, tvTeam, tvAltura, tvPeso, tvUrl;
    public ImageView ivPlayer;
    public Player player;
    public Bundle bundle;
    public PlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvTeam = itemView.findViewById(R.id.tvTeam);
        tvAltura = itemView.findViewById(R.id.tvAltura);
        tvPeso = itemView.findViewById(R.id.tvPeso);
        tvUrl = itemView.findViewById(R.id.tvURL);
        ivPlayer = itemView.findViewById(R.id.ivPlayer);
        itemView.setOnClickListener(v -> {
            Log.v("xyzyx", "onclick" + player.name);
            bundle = new Bundle();
            bundle.putParcelable("player",player);
            Navigation.findNavController(itemView).navigate(R.id.action_FirstFragment_to_editplayerFragment,bundle);


        });
    }
}