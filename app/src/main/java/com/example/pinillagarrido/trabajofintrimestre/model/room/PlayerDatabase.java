package com.example.pinillagarrido.trabajofintrimestre.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pinillagarrido.trabajofintrimestre.model.entity.Player;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Team;


@Database(entities = {Player.class, Team.class}, version = 1, exportSchema = false)
public abstract class PlayerDatabase extends RoomDatabase {

    public abstract PlayerDao getDao();

    private static volatile PlayerDatabase INSTANCE;

    /* versión simplificada */
    public static PlayerDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PlayerDatabase.class, "player").build();
        }
        return INSTANCE;
    }

}