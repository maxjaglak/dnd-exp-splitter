package oo.max.dndexperiencesplitter.expsplitting.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Bind(R.id.lock_player_assessment)
    Button lockAssessmentButton;

    @Bind(R.id.assessment_locked_container)
    LinearLayout assessmentLockedTextContainer;

    private PickingRequest pickingRequest;

    @Inject
    LoadPlayersAndCategoriesAction loadPlayersAndCategoriesAction;

    @Inject
    ExpSplittingManager expSplittingManager;

    private List<Player> players;
    private List<Category> categories;
    private Player currentlyVisiblePlayer;

    private Set<Player> lockedPlayers = new HashSet<>();

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
        currentlyVisiblePlayer = pickedPlayer;

        if(lockedPlayers.contains(pickedPlayer)) {
            hideAssessmentView();
            return;
        }

        showAssessmentView();

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
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.really))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startResultActivity();
                    }
                })
                .setNeutralButton(R.string.cancel, null)
                .show();
    }

    public void startResultActivity() {
        Intent intent = new Intent(this, ResultPreviewActivity.class);
        startActivity(intent);
        finish();;
    }

    @Override
    public void onBackPressed() {
        //nothing
    }

    @OnClick(R.id.lock_player_assessment)
    public void onLockCurrentAssessmentButtonClicked() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.lock_assessment_confirmation_title)
                .setMessage(R.string.lock_assessment_confirmation_message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lockCurrentPlayerAssessment();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();

    }

    private void lockCurrentPlayerAssessment() {
        lockedPlayers.add(currentlyVisiblePlayer);
        hideAssessmentView();
    }

    private void hideAssessmentView() {
        assessPlayersRecyclerView.setVisibility(View.GONE);
        lockAssessmentButton.setVisibility(View.GONE);
        assessmentLockedTextContainer.setVisibility(View.VISIBLE);
    }

    private void showAssessmentView() {
        assessPlayersRecyclerView.setVisibility(View.VISIBLE);
        lockAssessmentButton.setVisibility(View.VISIBLE);
        assessmentLockedTextContainer.setVisibility(View.GONE);
    }
}
