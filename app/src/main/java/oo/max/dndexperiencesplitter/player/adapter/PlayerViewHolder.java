package oo.max.dndexperiencesplitter.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.player.model.Player;

@Getter
public class PlayerViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.name)
    TextView name;

    @Setter
    private Player player;

    public PlayerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
