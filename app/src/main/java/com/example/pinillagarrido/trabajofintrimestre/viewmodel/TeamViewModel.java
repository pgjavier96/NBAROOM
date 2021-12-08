package com.example.pinillagarrido.trabajofintrimestre.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pinillagarrido.trabajofintrimestre.model.entity.Team;
import com.example.pinillagarrido.trabajofintrimestre.model.respository.PlayerRepository;

import java.util.List;

public class TeamViewModel extends AndroidViewModel {

    private PlayerRepository repository;

    public TeamViewModel(@NonNull Application application) {
        super(application);
        repository = new PlayerRepository(application);
    }

    public void insertTeam(Team... teams) {
        repository.insertTeam(teams);
    }

    public void updateTeam(Team... teams) {
        repository.updateTeam(teams);
    }

    public void deleteTeam(Team... teams) {
        repository.deleteTeam(teams);
    }

    public LiveData<List<Team>> getTeams() {
        return repository.getTeams();
    }

    public LiveData<Team> getTeam(long id) {
        return repository.getTeam(id);
    }
}
