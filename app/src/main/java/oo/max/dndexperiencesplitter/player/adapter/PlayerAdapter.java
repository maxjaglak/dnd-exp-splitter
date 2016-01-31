package oo.max.dndexperiencesplitter.player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.player.model.Player;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

    private final Context context;
    private final PlayerActionListener playerActionListener;

    @Getter
    private final List<Player> players;

    private final LayoutInflater inflater;

    public PlayerAdapter(Context context,
                         List<Player> players,
                         PlayerActionListener playerActionListener) {
        this.context = context;
        this.playerActionListener = playerActionListener;
        this.players = new ArrayList<>(players);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_player_item, parent, false);
        PlayerViewHolder playerViewHolder = new PlayerViewHolder(view, playerActionListener);
        return playerViewHolder;
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.name.setText(player.getName());
        holder.characterName.setText(player.getCharacterName());
        holder.gameMaster.setVisibility(player.isGameMaster() ? View.VISIBLE : View.GONE);
        holder.setPlayer(player);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
