package com.example.pinillagarrido.trabajofintrimestre.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinillagarrido.trabajofintrimestre.R;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Team;


public class TeamViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTeam;

    public TeamViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTeam = itemView.findViewById(R.id.tvTeam);
    }

    public TextView getTeamItemView() {
        return tvTeam;
    }
}