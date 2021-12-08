package com.example.pinillagarrido.trabajofintrimestre.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pinillagarrido.trabajofintrimestre.model.entity.Player;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.PlayerTeam;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Team;
import com.example.pinillagarrido.trabajofintrimestre.model.respository.PlayerRepository;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository repository;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        repository = new PlayerRepository(application);
    }

    public void insertPlayer(Player... players) {
        repository.insertPlayer(players);
    }

    public void updatePlayer(Player... players) {
        repository.updatePlayer(players);
    }

    public void deletePlayers(Player... players) {
        repository.deletePlayers(players);
    }

    public LiveData<List<Player>> getPlayers() {
        return repository.getPlayers();
    }

    public LiveData<Player> getPlayer(long id) {
        return repository.getPlayers(id);
    }

    public void insertPlayer(Player player, Team team) {
        repository.insertPlayer(player, team);
    }

    public LiveData<List<PlayerTeam>> getAllPlayer() {
        return repository.getAllPlayer();
    }

    public MutableLiveData<Long> getInsertResult() {
        return repository.getInsertResult();
    }

    public MutableLiveData<List<Long>> getInsertResults() {
        return repository.getInsertResults();
    }

    public void getKalos() {
        repository.getKalos();
    }

    public MutableLiveData<String> getKalosResult() {
        return repository.getKalosResult();
    }

    public String getUrl(String pokemonName) {
        return repository.getUrl(pokemonName);
    }
}
