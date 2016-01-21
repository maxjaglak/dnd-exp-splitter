package oo.max.dndexperiencesplitter.player.action;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import oo.max.dndexperiencesplitter.player.adapter.PlayerAdapter;
import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.player.model.Player;

@Singleton
public class LoadPlayersAction {

    private final PlayerDao playerDao;
    private LoadAsyncTask task;
    private List<Player> players;
    private RecyclerView target;

    @Inject
    public LoadPlayersAction(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void loadPlayers(RecyclerView target) {
        this.target = target;
        task = new LoadAsyncTask();
        task.execute();
    }

    void loadInBackground() {
        players = playerDao.get();
    }

    void after() {
        PlayerAdapter playerAdapter = new PlayerAdapter(target.getContext(), players);
        target.setAdapter(playerAdapter);
        target.setLayoutManager(new LinearLayoutManager(target.getContext()));
    }

    class LoadAsyncTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            loadInBackground();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            after();
        }
    }
}
