package oo.max.dndexperiencesplitter.expsplitting.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import oo.max.dndexperiencesplitter.R;

public class ResultViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.player_name)
    TextView playerName;

    @Bind(R.id.character_name)
    TextView characterName;

    @Bind(R.id.base_exp)
    TextView baseExp;

    @Bind(R.id.bonus_exp)
    TextView bonusExp;

    @Bind(R.id.total_exp)
    TextView totalExp;

    public ResultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
