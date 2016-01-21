package oo.max.dndexperiencesplitter.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import oo.max.dndexperiencesplitter.R;

public class PlayerViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.name)
    TextView name;

    public PlayerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
