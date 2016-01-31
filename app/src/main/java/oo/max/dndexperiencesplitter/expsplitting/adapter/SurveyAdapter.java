package oo.max.dndexperiencesplitter.expsplitting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.player.model.Player;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyViewHolder> {

    private final Context context;
    private final List<Player> playersToAssess;
    private final List<Category> categories;
    private Player assessingPlayer;
    private final LayoutInflater layoutInflater;

    public SurveyAdapter(Context context,
                         List<Player> playersToAssess,
                         List<Category> categories,
                         Player assessingPlayer) {
        this.context = context;
        this.playersToAssess = playersToAssess;
        this.categories = categories;
        this.assessingPlayer = assessingPlayer;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_assess_item, parent, false);
        SurveyViewHolder surveyViewHolder = new SurveyViewHolder(view);
        surveyViewHolder.getAssessPlayerView().setCategories(categories);
        surveyViewHolder.getAssessPlayerView().setAssessingPlayer(assessingPlayer);
        return surveyViewHolder;
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {
        Player player = playersToAssess.get(position);
        holder.getAssessPlayerView().setPlayerToAssess(player);
    }

    @Override
    public int getItemCount() {
        return playersToAssess.size();
    }
}
