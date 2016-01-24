package oo.max.dndexperiencesplitter.player.action;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import javax.inject.Inject;

import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.player.adapter.PlayerViewHolder;
import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.player.model.Player;

public class PlayerSwipeRemoveAction extends ItemTouchHelper.Callback {

    private final PlayerDao playerDao;

    @Inject
    public PlayerSwipeRemoveAction(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.END | ItemTouchHelper.START;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        PlayerViewHolder playerViewHolder = (PlayerViewHolder) viewHolder;
        Player player = playerViewHolder.getPlayer();
        playerDao.remove(player);
        Toast.makeText(playerViewHolder.getName().getContext(),
                R.string.player_deleted,
                Toast.LENGTH_SHORT)
                .show();
    }
}
