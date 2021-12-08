package com.example.pinillagarrido.trabajofintrimestre.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "playerteam",
        indices = {@Index(value = "name", unique = true)})
public class Team {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @Override
    public String toString() {
        return name;
    }
}