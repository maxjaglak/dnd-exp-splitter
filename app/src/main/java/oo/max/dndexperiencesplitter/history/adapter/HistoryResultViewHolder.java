package oo.max.dndexperiencesplitter.history.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import oo.max.dndexperiencesplitter.R;

@Getter
public class HistoryResultViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.date)
    TextView dateText;

    @Bind(R.id.players_data)
    TextView playersData;

    public HistoryResultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
