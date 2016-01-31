package oo.max.dndexperiencesplitter.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import lombok.Setter;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.player.model.Player;

@Getter
public class PlayerViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.character_name)
    TextView characterName;

    @Bind(R.id.game_master)
    TextView gameMaster;

    @Setter
    private Player player;

    private final PlayerActionListener playerActionListener;

    public PlayerViewHolder(View itemView, PlayerActionListener playerActionListener) {
        super(itemView);
        this.playerActionListener = playerActionListener;
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.card_view)
    public void editPlayer() {
        playerActionListener.editPlayer(player);
    }


}
