package oo.max.dndexperiencesplitter.playerpicking.action;

import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import oo.max.dndexperiencesplitter.core.action.AbstractLoadAction;
import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.player.model.Player;
import oo.max.dndexperiencesplitter.playerpicking.adapter.PlayersPickingAdapter;

public class LoadPlayersForPickingAction extends AbstractLoadAction {

    private final PlayerDao playerDao;
    private List<Player> players;

    @Inject
    public LoadPlayersForPickingAction(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public void loadInBackground() {
        players = playerDao.get();
    }

    @Override
    public void after() {
        target.setAdapter(new PlayersPickingAdapter(target.getContext(), players));
        target.setLayoutManager(new LinearLayoutManager(target.getContext()));
    }
}
