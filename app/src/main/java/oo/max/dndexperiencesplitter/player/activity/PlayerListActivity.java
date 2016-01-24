package oo.max.dndexperiencesplitter.player.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.player.action.LoadPlayersAction;
import oo.max.dndexperiencesplitter.player.action.PlayerSwipeRemoveAction;
import oo.max.dndexperiencesplitter.player.dialog.AddPlayerFragment;
import oo.max.dndexperiencesplitter.player.event.PlayerUpdatedEvent;

public class PlayerListActivity extends AbstractBaseActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.add)
    FloatingActionButton add;

    @Inject
    LoadPlayersAction loadPlayersAction;

    @Inject
    PlayerSwipeRemoveAction playerSwipeRemoveAction;

    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        loadPlayersAction.loadPlayers(recyclerView);
        itemTouchHelper = new ItemTouchHelper(playerSwipeRemoveAction);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @OnClick(R.id.add)
    public void addNewPlayer() {
        AddPlayerFragment.show(getSupportFragmentManager());
    }

    public void onEventMainThread(PlayerUpdatedEvent event) {
        loadPlayersAction.loadPlayers(recyclerView);
    }
}
