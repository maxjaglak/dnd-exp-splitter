package oo.max.dndexperiencesplitter.expsplitting.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.expsplitting.view.AssessPlayerView;

@Getter
public class SurveyViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.assess_player_view)
    AssessPlayerView assessPlayerView;

    public SurveyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
