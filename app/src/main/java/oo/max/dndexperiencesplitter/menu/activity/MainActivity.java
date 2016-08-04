package oo.max.dndexperiencesplitter.menu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.category.activity.CategoryListActivity;
import oo.max.dndexperiencesplitter.category.dao.CategoryDao;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.history.activity.HistoryResultsActivity;
import oo.max.dndexperiencesplitter.license.LicenseActivity;
import oo.max.dndexperiencesplitter.player.activity.PlayerListActivity;
import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.playerpicking.activity.PickPlayersActivity;

public class MainActivity extends AbstractBaseActivity {

    @Inject
    CategoryDao categoryDao;

    @Inject
    PlayerDao playerDao;

    @Bind(R.id.drawer_view)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.drawer_players:
                startPlayersActivity();
                break;
            case R.id.drawer_categories:
                startCategoriesActivity();
                break;
            case R.id.drawer_run_survey:
                startExpSplitting();
                break;
            case R.id.drawer_history:
                startHistoryResultsActivity();
                break;
            case R.id.drawer_license:
                startActivity(new Intent(this, LicenseActivity.class));
                break;
        }

        drawerLayout.closeDrawers();
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
        if (!canRunSurvey()) {
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
