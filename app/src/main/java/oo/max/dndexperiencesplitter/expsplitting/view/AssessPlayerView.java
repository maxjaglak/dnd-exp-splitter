package oo.max.dndexperiencesplitter.expsplitting.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.app.ExpApplication;
import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.expsplitting.model.SingleAssessmentResult;
import oo.max.dndexperiencesplitter.expsplitting.service.ExpSplittingManager;
import oo.max.dndexperiencesplitter.player.model.Player;

public class AssessPlayerView extends LinearLayout implements CategoryView.OnCategoryValuePicked {

    @Bind(R.id.player_name)
    TextView playerName;

    @Bind(R.id.character_name)
    TextView characterName;

    @Bind(R.id.category_container)
    LinearLayout categoryContainer;

    private Player playerToAssess;
    private Player assessingPlayer;

    @Inject
    ExpSplittingManager expSplittingManager;

    private Map<Category, CategoryView> categoryViewMap = new HashMap<>();

    public AssessPlayerView(Context context) {
        this(context, null);
    }

    public AssessPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AssessPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ExpApplication.getApp(getContext()).getInjector().getApplicationComponent().inject(this);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_splitting_assess_player, this, true);
        ButterKnife.bind(this, view);
    }

    public void setAssessingPlayer(Player assessingPlayer) {
        this.assessingPlayer = assessingPlayer;
    }

    public void setCategories(List<Category> categories) {
        categoryContainer.removeAllViews();
        categoryViewMap.clear();
        for (Category category : categories) {
            CategoryView categoryView = new CategoryView(getContext());
            categoryView.setCategory(category);
            categoryView.setOnCategoryValuePicked(this);
            categoryContainer.addView(categoryView);
            categoryViewMap.put(category, categoryView);
        }
    }

    public void setPlayerToAssess(Player player) {
        this.playerToAssess = player;
        this.playerName.setText(player.getName());
        this.characterName.setText(player.getCharacterName());
        resetCategoryViews();
        updateCategoriesIfResultsExist();
    }

    private void resetCategoryViews() {
        for (CategoryView categoryView : categoryViewMap.values()) {
            categoryView.reset();
        }
    }

    private void updateCategoriesIfResultsExist() {
        for (Map.Entry<Category, CategoryView> entry : categoryViewMap.entrySet()) {
            Category category = entry.getKey();
            Optional<SingleAssessmentResult> result = expSplittingManager.findAssessmentResult(playerToAssess,
                    assessingPlayer,
                    category);

            if(result.isPresent()) {
                entry.getValue().setValue(result.get().getValue());
            }
        }
    }

    @Override
    public void onCategoryValuePicked(Category category, int value) {
        SingleAssessmentResult singleAssessmentResult = SingleAssessmentResult.builder()
                .assessingPlayer(assessingPlayer)
                .assessedPlayer(playerToAssess)
                .category(category)
                .value(value)
                .build();

        expSplittingManager.savePlayerResult(singleAssessmentResult);
    }
}
