package oo.max.dndexperiencesplitter.expsplitting.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import oo.max.dndexperiencesplitter.expsplitting.fragment.PlayerNameFragment;
import oo.max.dndexperiencesplitter.player.model.Player;

public class CurrentPlayerAdapter extends FragmentStatePagerAdapter {

    private final List<Player> players;

    public CurrentPlayerAdapter(FragmentManager fragmentManager,
                                List<Player> players) {
        super(fragmentManager);
        this.players = players;
    }

    @Override
    public Fragment getItem(int position) {
        return PlayerNameFragment.create(players.get(position));
    }

    @Override
    public int getCount() {
        return players.size();
    }
}
