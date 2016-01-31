package oo.max.dndexperiencesplitter.playerpicking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.Set;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.expsplitting.activity.MaxExpActivity;
import oo.max.dndexperiencesplitter.expsplitting.activity.SurveyActivity;
import oo.max.dndexperiencesplitter.player.model.Player;
import oo.max.dndexperiencesplitter.playerpicking.action.LoadPlayersForPickingAction;
import oo.max.dndexperiencesplitter.playerpicking.adapter.PlayersPickingAdapter;
import oo.max.dndexperiencesplitter.playerpicking.model.PickingRequest;

public class PickPlayersActivity extends AbstractBaseActivity {

    @Bind(R.id.players_recycle_view)
    RecyclerView pickPlayersView;

    @Bind(R.id.ok)
    Button okButton;

    @Inject
    LoadPlayersForPickingAction loadPlayersForPickingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splitting_pick_players);
        loadPlayersForPickingAction.load(pickPlayersView);
    }

    @OnClick(R.id.ok)
    public void pickSelectedPlayers() {
        if(!validate()) {
            return;
        }

        PickingRequest pickingRequest = new PickingRequest(getPickedPlayersFromAdapter());
        Intent intent = new Intent(this, MaxExpActivity.class);
        intent.putExtra(SurveyActivity.PICKING_REQUEST_PARAM, pickingRequest);
        startActivity(intent);
    }

    private boolean validate() {
        if(getPickedPlayersFromAdapter().isEmpty()) {
            okButton.setError(getString(R.string.pick_players_validation_message));
            return false;
        }
        return true;
    }

    private Set<Player> getPickedPlayersFromAdapter() {
        return ((PlayersPickingAdapter) pickPlayersView.getAdapter()).getPickedPlayers();
    }

}