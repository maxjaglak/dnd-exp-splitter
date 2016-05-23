package oo.max.dndexperiencesplitter.menu.activity;

import android.content.Intent;
import android.os.Bundle;

import butterknife.OnClick;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.category.activity.CategoryListActivity;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.history.activity.HistoryResultsActivity;
import oo.max.dndexperiencesplitter.player.activity.PlayerListActivity;
import oo.max.dndexperiencesplitter.playerpicking.activity.PickPlayersActivity;

public class MainActivity extends AbstractBaseActivity{

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
        startActivity(new Intent(this, PickPlayersActivity.class));
    }

    @OnClick(R.id.history)
    public void startHistoryResultsActivity() {
        startActivity(new Intent(this, HistoryResultsActivity.class));
    }
}
