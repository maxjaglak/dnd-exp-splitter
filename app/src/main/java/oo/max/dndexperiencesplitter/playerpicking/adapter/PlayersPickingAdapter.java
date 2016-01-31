package oo.max.dndexperiencesplitter.playerpicking.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.player.model.Player;

public class PlayersPickingAdapter extends RecyclerView.Adapter<PlayerPickingViewHolder>
        implements PickPlayerActionListener {

    private final Context context;

    @Getter
    private final List<Player> playersToPick;

    @Getter
    private final Set<Player> pickedPlayers = new HashSet<>();

    private final LayoutInflater layoutInflater;

    public PlayersPickingAdapter(Context context, List<Player> playersToPick) {
        this.context = context;
        this.playersToPick = new ArrayList<>(playersToPick);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PlayerPickingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_pick_player_item, parent, false);
        PlayerPickingViewHolder playerPickingViewHolder = new PlayerPickingViewHolder(view, this);
        return playerPickingViewHolder;
    }

    @Override
    public void onBindViewHolder(PlayerPickingViewHolder holder, int position) {
        Player player = playersToPick.get(position);
        holder.setPlayer(player);
        holder.getPlayerName().setText(player.getName());
        holder.getCharacterName().setText(player.getCharacterName());
        holder.getGameMaster().setVisibility(player.isGameMaster() ? View.VISIBLE : View.GONE);
        if(pickedPlayers.contains(player)) {
            holder.getContainer().setBackgroundColor(Color.BLUE);
        } else {
            holder.getContainer().setBackground(null);
        }
    }

    @Override
    public int getItemCount() {
        return playersToPick.size();
    }

    @Override
    public void onPlayerSelected(Player player) {
        if(pickedPlayers.contains(player)) {
            pickedPlayers.remove(player);
        } else {
            pickedPlayers.add(player);
        }
        notifyDataSetChanged();
    }

}
