package oo.max.dndexperiencesplitter.playerpicking.adapter;

import android.support.v7.widget.CardView;
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
public class PlayerPickingViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.player_name)
    TextView playerName;

    @Bind(R.id.character_name)
    TextView characterName;

    @Bind(R.id.container)
    CardView container;

    @Bind(R.id.game_master)
    TextView gameMaster;

    @Setter
    private Player player;

    private final PickPlayerActionListener pickPlayerActionListener;

    public PlayerPickingViewHolder(View itemView,
                                   PickPlayerActionListener pickPlayerActionListener) {
        super(itemView);
        this.pickPlayerActionListener = pickPlayerActionListener;
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.container)
    public void playerClicked() {
        pickPlayerActionListener.onPlayerSelected(player);
    }

}