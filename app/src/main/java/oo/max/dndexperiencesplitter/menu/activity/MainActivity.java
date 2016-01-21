package oo.max.dndexperiencesplitter.menu.activity;

import android.content.Intent;
import android.os.Bundle;

import butterknife.OnClick;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.player.activity.PlayerListActivity;

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
}
