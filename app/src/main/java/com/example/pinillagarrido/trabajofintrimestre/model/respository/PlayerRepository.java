package com.example.pinillagarrido.trabajofintrimestre.model.respository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pinillagarrido.trabajofintrimestre.model.api.PlayerList;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Player;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.PlayerTeam;
import com.example.pinillagarrido.trabajofintrimestre.model.entity.Team;
import com.example.pinillagarrido.trabajofintrimestre.model.room.PlayerDao;
import com.example.pinillagarrido.trabajofintrimestre.model.room.PlayerDatabase;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class PlayerRepository {

    private static final String INIT = "init";

    private HashMap<String, String> playerMap;
    private PlayerDao dao;
    private LiveData<List<PlayerTeam>> allPlayer;
    private LiveData<List<Player>> livePlayers;
    private LiveData<List<Team>> liveTeams;
    private LiveData<Player> livePlayer;
    private LiveData<Team> liveTeam;
    private MutableLiveData<Long> liveInsertResult;
    private MutableLiveData<List<Long>> liveInsertResults;
    private MutableLiveData<String> liveGetKalosResult;
    private SharedPreferences preferences;
    private PlayerList playerList;

    public PlayerRepository(Context context) {
        PlayerDatabase db = PlayerDatabase.getDatabase(context);
        playerList = new PlayerList();
        playerMap = new HashMap<>();
        dao = db.getDao();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        liveInsertResult = new MutableLiveData<>();
        liveInsertResults = new MutableLiveData<>();
        liveGetKalosResult = new MutableLiveData<>();
        if(!getInit()) {
            teamsPreload();
            setInit();
        }
    }

    public void insertPlayer(Player player, Team team) {
        Runnable r = () -> {
            player.idtype = insertTeamGetId(team);
            if(player.idtype > 0) {
                dao.insertPlayer(player);
            }
        };
        new Thread(r).start();
    }

    public MutableLiveData<Long> getInsertResult() {
        return liveInsertResult;
    }

    public MutableLiveData<String> getKalosResult() {
        return liveGetKalosResult;
    }

    public MutableLiveData<List<Long>> getInsertResults() {
        return liveInsertResults;
    }

    private long insertTeamGetId(Team type) {
        List<Long> ids = dao.insertTeam(type);
        if(ids.get(0) < 1) {
            return dao.getIdTeam(type.name);
        } else {
            return ids.get(0);
        }
    }

    public void insertPlayer(Player... players) {
        Runnable r = () -> {
            List<Long> resultList = dao.insertPlayer(players);
            liveInsertResult.postValue(resultList.get(0));
            liveInsertResults.postValue(resultList);
        };
        new Thread(r).start();
    }

    public void insertTeam(Team... teams) {
        Runnable r = () -> {
            dao.insertTeam(teams);
        };
        new Thread(r).start();
    }

    public void updatePlayer(Player... players) {
        Runnable r = () -> {
            dao.updatePlayer(players);
        };
        new Thread(r).start();
    }

    public void updateTeam(Team... teams) {
        Runnable r = () -> {
            dao.updateTeam(teams);
        };
        new Thread(r).start();
    }

    public void deletePlayers(Player... players) {
        Runnable r = () -> {
            dao.deletePlayers(players);
        };
        new Thread(r).start();
    }

    public void deleteTeam(Team... teams) {
        Runnable r = () -> {
            dao.deleteTeam(teams);
        };
        new Thread(r).start();
    }

    public LiveData<List<Player>> getPlayers() {
        if(livePlayers == null) {
            livePlayers = dao.getPlayers();
        }
        return livePlayers;
    }

    public LiveData<List<Team>> getTeams() {
        if(liveTeams == null) {
            liveTeams = dao.getTeams();
        }
        return liveTeams;
    }

    public LiveData<Player> getPlayers(long id) {
        if(livePlayer == null) {
            livePlayer = dao.getPokemon(id);
        }
        return livePlayer;
    }

    public LiveData<Team> getTeam(long id) {
        if(liveTeam == null) {
            liveTeam = dao.getTeam(id);
        }
        return liveTeam;
    }

    public LiveData<List<PlayerTeam>> getAllPlayer() {
        if(allPlayer == null) {
            allPlayer = dao.getAllPlayer();
        }
        return allPlayer;
    }

    public void teamsPreload() {
        String[] teamNames = new String[] {"Atlanta Hawks", "Boston Celtics", "Brooklyn Nets", "Charlotte Hornets", "Chicago Bulls",
                                           "Cleveland Cavaliers", "Dallas Mavericks", "Denver Nuggets", "Detroit Pistons",
                                           "Golden State Warriors","Houston Rockets", "Indiana Pacers", "Los Angeles Clippers",
                                           "Los Angeles Lakers", "Memphis Grizzlies", "Miami Heat", "Milwaukee Bucks", "Minnesota Timberwolves",
                                           "New Orleans Pelicans","New York Knicks","Oklahoma City Thunder","Orlando Magic","Philadelphia 76ers",
                                           "Phoenix Suns","Portland Trail Blazers","Sacramento Kings","San Antonio Spurs","Toronto Raptors",
                                           "Utah Jazz","Washington Wizards"};
        Team[] teams = new Team[teamNames.length];
        Team team;
        int cont = 0;
        for (String s: teamNames) {
            team = new Team();
            team.name = s;
            teams[cont] = team;
            cont++;
        }
        insertTeam(teams);
    }

    public void setInit() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(INIT, true);
        editor.commit();
    }

    public boolean getInit() {
        return preferences.getBoolean(INIT, false);
    }

    public void getKalos() {
        Runnable r = () -> {
            String result = playerList.getKalos("https://www.pokemon.com/es/api/pokedex/kalos");
            populateHashMap(result);
            liveGetKalosResult.postValue(result); // -> observer
            Log.v("xyzyx", result);
        };
        new Thread(r).start();
    }

    public String getUrl(String playerName) {
        String url = playerMap.get(playerName.toLowerCase());
        if(url == null) {
            url = "https://www.latercera.com/resizer/CBmGvvFEACkiaL4Diatt7wyUqlM=/900x600/smart/arc-anglerfish-arc2-prod-copesa.s3.amazonaws.com/public/LUOOHUM2OVEEXG7ZTRSNI6XWLY.png";
        }
        return url;
    }

    private void populateHashMap(String string) {
        String name, url;
        try {
            JSONArray jsonArray = new JSONArray(string);
            JSONObject jsonObject;
            for (int i = 0, size = jsonArray.length(); i < size; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                name = jsonObject.getString("name").toLowerCase();
                url = jsonObject.getString("ThumbnailImage");
                playerMap.put(name, url);
            }
        } catch (JSONException e) {
        }
    }
}