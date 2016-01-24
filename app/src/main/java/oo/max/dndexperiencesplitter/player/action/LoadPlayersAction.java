package oo.max.dndexperiencesplitter.player.action;

import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import oo.max.dndexperiencesplitter.core.action.AbstractLoadAction;
import oo.max.dndexperiencesplitter.player.adapter.PlayerAdapter;
import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.player.model.Player;

@Singleton
public class LoadPlayersAction extends AbstractLoadAction {

    private final PlayerDao playerDao;

    private List<Player> players;

    @Inject
    public LoadPlayersAction(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void loadInBackground() {
        players = playerDao.get();
    }

    public void after() {
        PlayerAdapter playerAdapter = new PlayerAdapter(target.getContext(), players);
        target.setAdapter(playerAdapter);
        target.setLayoutManager(new LinearLayoutManager(target.getContext()));
    }

}