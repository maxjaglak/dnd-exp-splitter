package oo.max.dndexperiencesplitter.expsplitting.service;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.expsplitting.model.SingleAssessmentResult;
import oo.max.dndexperiencesplitter.player.model.Player;

public class ExpSplittingManagerTest  {

    private ExpSplittingManager expSplittingManager;

    private Player player1;
    private Player player2;
    private Player gm;
    private Category category;

    @Before
    public void setup() {
        expSplittingManager = new ExpSplittingManager();

        player1 = Player.builder()
                .id(1l)
                .name("Max")
                .characterName("Chris")
                .build();

        player2 = Player.builder()
                .id(2l)
                .name("Ktos")
                .characterName("a")
                .build();

        gm = Player.builder()
                .id(3l)
                .name("gm")
                .characterName("gm")
                .gameMaster(true)
                .build();

        category = Category.builder()
                .name("category 1")
                .value(1)
                .build();

        expSplittingManager.startSplitting(ImmutableList.of(player1, player2, gm),
                ImmutableList.of(category));
    }

    @Test
    public void shouldPlayersToBeAssessedByPlayerDoesNotContainGivenPlayer() {
        //when
        List<Player> players = expSplittingManager.getPlayersToBeAssessedByPlayer(player1);

        //then
        Assertions.assertThat(players).doesNotContain(player1);
    }

    @Test
    public void shouldPlayersToBeAssessedDoesNotContainGameMasterPlayer() {
        //when
        List<Player> players = expSplittingManager.getPlayersToBeAssessedByPlayer(player1);

        //then
        Assertions.assertThat(players).doesNotContain(gm);
    }

    @Test
    public void shouldSaveNewAssessmentResults() {
        //given
        SingleAssessmentResult singleAssessmentResult = SingleAssessmentResult.builder()
                .assessedPlayer(player1)
                .assessingPlayer(player2)
                .category(category)
                .value(1)
                .build();

        //when
        expSplittingManager.savePlayerResult(singleAssessmentResult);

        //then
        Assertions.assertThat(expSplittingManager.getResults()).contains(singleAssessmentResult);
    }

    @Test
    public void shouldUpdateResultIfSetSecondTimeForSameCategoryAndPlayers() {
        //given
        SingleAssessmentResult singleAssessmentResult = SingleAssessmentResult.builder()
                .assessedPlayer(player1)
                .assessingPlayer(player2)
                .category(category)
                .value(1)
                .build();

        expSplittingManager.savePlayerResult(singleAssessmentResult);

        SingleAssessmentResult singleAssessmentResult2 = SingleAssessmentResult.builder()
                .assessedPlayer(player1)
                .assessingPlayer(player2)
                .category(category)
                .value(2)
                .build();
        //when
        expSplittingManager.savePlayerResult(singleAssessmentResult2);

        //then
        Assertions.assertThat(expSplittingManager.getResults()).containsOnly(singleAssessmentResult2);
        Assertions.assertThat(expSplittingManager.getResults().get(0).getValue()).isEqualTo(2);
    }

    @Test
    public void shouldReturnOptionalAbsentWhenNoResultsMatchingSearchedResultsAreFound() {
        //when
        Optional<SingleAssessmentResult> result = expSplittingManager.findAssessmentResult(player1, player2, category);

        //then
        Assertions.assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void shouldFindResultForGivenPlayersAndCategory() {
        //given
        SingleAssessmentResult singleAssessmentResult = SingleAssessmentResult.builder()
                .assessedPlayer(player1)
                .assessingPlayer(player2)
                .category(category)
                .value(1)
                .build();

        expSplittingManager.savePlayerResult(singleAssessmentResult);

        //when
        Optional<SingleAssessmentResult> result = expSplittingManager.findAssessmentResult(player1, player2, category);

        //then
        Assertions.assertThat(result.get()).isEqualTo(singleAssessmentResult);
    }

}