package oo.max.dndexperiencesplitter.expsplitting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.expsplitting.model.ExpResult;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    private final Context context;
    private final List<ExpResult> results;
    private final LayoutInflater layoutInflater;

    public ResultAdapter(Context context, List<ExpResult> results) {
        this.context = context;
        this.results = results;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_splitting_result_item, parent, false);
        ResultViewHolder resultViewHolder = new ResultViewHolder(view);
        return resultViewHolder;
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        ExpResult result = results.get(position);
        holder.playerName.setText(result.getPlayer().getName());
        holder.characterName.setText(result.getPlayer().getCharacterName());
        holder.baseExp.setText(String.valueOf(result.getBase()));
        holder.bonusExp.setText(String.valueOf(result.getBonus()));
        holder.totalExp.setText(String.valueOf(result.total()));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
