package oo.max.dndexperiencesplitter.expsplitting.action;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import oo.max.dndexperiencesplitter.category.dao.CategoryDao;
import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.core.action.AbstractLoadAction;
import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.player.model.Player;
import oo.max.dndexperiencesplitter.playerpicking.model.PickingRequest;

public class LoadPlayersAndCategoriesAction extends AbstractLoadAction {

    private PickingRequest pickingRequest;
    private OnLoaded callback;

    private final PlayerDao playerDao;
    private final CategoryDao categoryDao;
    private List<Player> players;
    private List<Category> categories;

    @Inject
    public LoadPlayersAndCategoriesAction(PlayerDao playerDao,
                                          CategoryDao categoryDao) {
        this.playerDao = playerDao;
        this.categoryDao = categoryDao;
    }

    @Override
    @Deprecated
    public void load(RecyclerView target) {
        super.load(target);
    }

    public void load(PickingRequest pickingRequest,
                     OnLoaded callback) {
        this.pickingRequest = pickingRequest;
        this.callback = callback;
        super.load(null);
    }

    @Override
    public void loadInBackground() {
        players = playerDao.get(pickingRequest);
        categories = categoryDao.get();
    }

    @Override
    public void after() {
        callback.onLoaded(players, categories);
    }

    public interface OnLoaded {
        void onLoaded(List<Player> players, List<Category> categories);
    }
}
