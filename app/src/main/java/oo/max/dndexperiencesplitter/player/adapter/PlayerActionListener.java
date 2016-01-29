package oo.max.dndexperiencesplitter.player.adapter;

import android.support.v4.app.FragmentManager;

import oo.max.dndexperiencesplitter.player.dialog.AddPlayerFragment;
import oo.max.dndexperiencesplitter.player.model.Player;

public class PlayerActionListener {

    private final FragmentManager fragmentManager;

    public PlayerActionListener(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void editPlayer(Player player) {
        AddPlayerFragment.editPlayer(player, fragmentManager);
    }
}
