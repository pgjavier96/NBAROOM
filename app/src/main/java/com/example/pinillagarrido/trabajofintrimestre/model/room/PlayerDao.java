package com.example.pinillagarrido.trabajofintrimestre.model.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pinillagarrido.trabajofintrimestre.model.entity.Player;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.PlayerTeam;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Team;


import java.util.List;

@Dao
public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertPlayer(Player... players);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Player player);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertTeam(Team... types);

    @Update
    int updatePlayer(Player... players);

    @Update
    int updateTeam(Team... types);

    @Delete
    int deletePlayers(Player... players);

    @Delete
    int deleteTeam(Team... types);

    @Query("delete from playerTeam")
    int deleteAllTeams();

    @Query("delete from player")
    int deleteAllPlayer();

    @Query("select * from player order by name asc")
    LiveData<List<Player>> getPlayers();

    @Query("select p.*, pt.id team_id, pt.name team_name from player p join playerteam pt on p.idtype = pt.id order by name asc")
    LiveData<List<PlayerTeam>> getAllPlayer();

    @Query("select * from playerteam order by name asc")
    LiveData<List<Team>> getTeams();

    @Query("select * from player where id = :id")
    LiveData<Player> getPokemon(long id);

    @Query("select * from playerteam where id = :id")
    LiveData<Team> getTeam(long id);

    @Query("select id from playerteam where name = :name")
    long getIdTeam(String name);

    @Query("select * from playerteam where name = :name")
    Team getTeam(String name);

}