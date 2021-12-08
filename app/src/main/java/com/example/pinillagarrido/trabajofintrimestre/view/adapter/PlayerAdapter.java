package com.example.pinillagarrido.trabajofintrimestre.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.pinillagarrido.trabajofintrimestre.R;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Player;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.PlayerTeam;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Team;
import com.example.pinillagarrido.trabajofintrimestre.view.adapter.viewholder.PlayerViewHolder;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

    private List<PlayerTeam> playerList;
    private Context context;

    public PlayerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        /*view.setOnClickListener(v -> {
            Log.v("xyzyx", "onclick create ");
            Pokemon p = (Pokemon) view.getTag();
            Log.v("xyzyx", p.name);
        });*/
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        PlayerTeam playerTeam = playerList.get(position);
        Player player = playerTeam.player;
        holder.player = player;
        Team team = playerTeam.team;
        holder.tvUrl.setText(player.url);
        holder.tvPeso.setText(player.weight + " " + context.getString(R.string.weight_unit));
        holder.tvAltura.setText(player.height + " " + context.getString(R.string.height_unit));
       holder.tvTeam.setText(team.name );
        holder.tvName.setText(player.name);
        //Picasso.get().load(player.url).into(holder.ivPokemon);
        Glide.with(context).load(player.url).into(holder.ivPlayer);
    }

    @Override
    public int getItemCount() {
        if(playerList == null) {
            return 0;
        }
        return playerList.size();
    }

    public void setPokemonList(List<PlayerTeam> playerList) {
        /*if(this.pokemonList == null) {
            this.pokemonList = pokemonList;
        }*/
        this.playerList = playerList;
        notifyDataSetChanged();
    }
}
