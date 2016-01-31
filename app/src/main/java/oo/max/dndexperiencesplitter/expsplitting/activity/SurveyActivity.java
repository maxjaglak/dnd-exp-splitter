package oo.max.dndexperiencesplitter.expsplitting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.expsplitting.action.LoadPlayersAndCategoriesAction;
import oo.max.dndexperiencesplitter.expsplitting.adapter.CurrentPlayerAdapter;
import oo.max.dndexperiencesplitter.expsplitting.adapter.SurveyAdapter;
import oo.max.dndexperiencesplitter.expsplitting.service.ExpSplittingManager;
import oo.max.dndexperiencesplitter.player.model.Player;
import oo.max.dndexperiencesplitter.playerpicking.model.PickingRequest;

public class SurveyActivity extends AbstractBaseActivity implements LoadPlayersAndCategoriesAction.OnLoaded {

    public static final String PICKING_REQUEST_PARAM = "PICKING_REQUEST_PARAM";

    @Bind(R.id.players_view_pager)
    ViewPager playersViewPager;

    @Bind(R.id.progress_bar_container)
    RelativeLayout progressBarContainer;

    @Bind(R.id.assess_recycler_view)
    RecyclerView assessPlayersRecyclerView;

    private PickingRequest pickingRequest;

    @Inject
    LoadPlayersAndCategoriesAction loadPlayersAndCategoriesAction;

    @Inject
    ExpSplittingManager expSplittingManager;

    private List<Player> players;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splitting_survey);
        loadPickingRequestFromIntent();
    }

    private void loadPickingRequestFromIntent() {
        showWaitingLoader();
        pickingRequest = (PickingRequest) getIntent().getSerializableExtra(PICKING_REQUEST_PARAM);
        loadPlayersAndCategoriesAction.load(pickingRequest, this);
    }

    @Override
    public void onLoaded(List<Player> players, List<Category> categories) {
        hideLoader();
        this.players = players;
        this.categories = categories;
        playersViewPager.setAdapter(new CurrentPlayerAdapter(getSupportFragmentManager(), players));
        playersViewPager.addOnPageChangeListener(buildOnPageChangeListener());
        expSplittingManager.startSplitting(players, categories, pickingRequest.getExpParam());
        loadPlayersSurvey(players.get(0));
    }

    private ViewPager.OnPageChangeListener buildOnPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //nothing
            }

            @Override
            public void onPageSelected(int position) {
                loadPlayersSurvey(players.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //nothing
            }
        };
    }

    private void loadPlayersSurvey(Player pickedPlayer) {
        List<Player> playersToAssess = expSplittingManager.getPlayersToBeAssessedByPlayer(pickedPlayer);

        assessPlayersRecyclerView.setAdapter(new SurveyAdapter(this,
                playersToAssess,
                categories,
                pickedPlayer));

        assessPlayersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showWaitingLoader() {
        progressBarContainer.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        progressBarContainer.setVisibility(View.GONE);
    }

    @OnClick(R.id.split)
    public void splitExp() {
        Intent intent = new Intent(this, ResultPreviewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //nothing
    }
}
