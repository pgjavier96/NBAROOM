package com.example.pinillagarrido.trabajofintrimestre.model.entity;

import androidx.room.Embedded;

public class PlayerTeam {

    @Embedded
    public Player player;

    @Embedded(prefix="team_")
    public Team team;
}