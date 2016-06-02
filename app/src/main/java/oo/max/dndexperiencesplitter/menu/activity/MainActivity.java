package oo.max.dndexperiencesplitter.menu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.OnClick;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.category.activity.CategoryListActivity;
import oo.max.dndexperiencesplitter.category.dao.CategoryDao;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.history.activity.HistoryResultsActivity;
import oo.max.dndexperiencesplitter.player.activity.PlayerListActivity;
import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.playerpicking.activity.PickPlayersActivity;

public class MainActivity extends AbstractBaseActivity {

    @Inject
    CategoryDao categoryDao;

    @Inject
    PlayerDao playerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.players)
    public void startPlayersActivity() {
        startActivity(new Intent(this, PlayerListActivity.class));
    }

    @OnClick(R.id.categories)
    public void startCategoriesActivity() {
        startActivity(new Intent(this, CategoryListActivity.class));
    }

    @OnClick(R.id.run)
    public void startExpSplitting() {
        if(!canRunSurvey()) {
            showPlayersOrCategoriesMissingToast();
            return;
        }
        startActivity(new Intent(this, PickPlayersActivity.class));
    }

    private boolean canRunSurvey() {
        return !playerDao.get().isEmpty() && !categoryDao.get().isEmpty();
    }

    private void showPlayersOrCategoriesMissingToast() {
        Toast.makeText(this, R.string.player_and_categories_required, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.history)
    public void startHistoryResultsActivity() {
        startActivity(new Intent(this, HistoryResultsActivity.class));
    }
}
