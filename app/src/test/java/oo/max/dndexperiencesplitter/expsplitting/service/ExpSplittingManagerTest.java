package oo.max.dndexperiencesplitter.expsplitting.service;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.expsplitting.model.ExpParam;
import oo.max.dndexperiencesplitter.expsplitting.model.ExpResult;
import oo.max.dndexperiencesplitter.expsplitting.model.SingleAssessmentResult;
import oo.max.dndexperiencesplitter.player.model.Player;

public class ExpSplittingManagerTest  {

    private ExpSplittingManager expSplittingManager;

    private Player player1;
    private Player player2;
    private Player gm;
    private Category category;
    private ExpParam expParam;

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

        expParam = ExpParam.builder()
                .baseExp(1000)
                .maxBonus(100)
                .build();

        expSplittingManager.startSplitting(ImmutableList.of(player1, player2, gm),
                ImmutableList.of(category), expParam);
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

    @Test
    public void shouldCalculateExpBasedOnResults() {
        //given
        SingleAssessmentResult player2To1 = SingleAssessmentResult.builder()
                .assessedPlayer(player1)
                .assessingPlayer(player2)
                .category(category)
                .value(5)
                .build();

        expSplittingManager.savePlayerResult(player2To1);

        SingleAssessmentResult gmToPlayer1 = SingleAssessmentResult.builder()
                .assessedPlayer(player1)
                .assessingPlayer(gm)
                .category(category)
                .value(5)
                .build();

        expSplittingManager.savePlayerResult(gmToPlayer1);

        SingleAssessmentResult player1To2 = SingleAssessmentResult.builder()
                .assessedPlayer(player2)
                .assessingPlayer(player1)
                .category(category)
                .value(0)
                .build();

        expSplittingManager.savePlayerResult(player1To2);

        SingleAssessmentResult gmToPlayer2 = SingleAssessmentResult.builder()
                .assessedPlayer(player2)
                .assessingPlayer(gm)
                .category(category)
                .value(5)
                .build();

        expSplittingManager.savePlayerResult(gmToPlayer2);

        //when
        List<ExpResult> expResults = expSplittingManager.calculateExp();

        //then
        //player 1 gets 1000 + (5+5 /10) * 100 = 1100
        //player 2 gets 1000 + (5+0 /10) * 100 = 1050
        //gm gets 1000 + (100 + 50) / 2 = 1075

        ExpResult player1Result = getForPlayer(player1, expResults);
        ExpResult player2Result = getForPlayer(player2, expResults);
        ExpResult gmResult = getForPlayer(gm, expResults);

        Assertions.assertThat(player1Result.getBonus()).isEqualTo(100);
        Assertions.assertThat(player1Result.getBase()).isEqualTo(1000);

        Assertions.assertThat(player2Result.getBonus()).isEqualTo(50);
        Assertions.assertThat(player2Result.getBase()).isEqualTo(1000);

        Assertions.assertThat(gmResult.getBonus()).isEqualTo(75);
        Assertions.assertThat(gmResult.getBase()).isEqualTo(1000);
    }

    private ExpResult getForPlayer(final Player player, List<ExpResult> expResults) {
        return FluentIterable.from(expResults).firstMatch(new Predicate<ExpResult>() {
            @Override
            public boolean apply(ExpResult input) {
                return input.getPlayer().equals(player);
            }
        }).orNull();
    }

    @Test
    public void shouldCalculateCategoryValueResult() {
        //given
        SingleAssessmentResult player2to1 = SingleAssessmentResult.builder()
                .assessedPlayer(player1)
                .assessingPlayer(player2)
                .category(category)
                .value(5)
                .build();

        SingleAssessmentResult gmToPlayer1 = SingleAssessmentResult.builder()
                .assessedPlayer(player1)
                .assessingPlayer(gm)
                .category(category)
                .value(1)
                .build();

        expSplittingManager.savePlayerResult(player2to1);
        expSplittingManager.savePlayerResult(gmToPlayer1);

        //when
        double value = expSplittingManager.calculateAverageCategoryValue(category, player1);

        //then
        Assertions.assertThat(value).isEqualTo(3.0, Offset.offset(0.00000000001));
    }

    @Test
    public void shouldCalculateWeightedAverageForGivenCategories() {
        //given
        Category category1 = Category.builder()
                .name("1")
                .value(1)
                .build();

        Category category2 = Category.builder()
                .name("2")
                .value(2)
                .build();

        Map<Category, Double> values = ImmutableMap.<Category, Double>builder()
                .put(category1, 5.0)
                .put(category2, 3.5)
                .build();

        //when
        double v = expSplittingManager.weightedAverageResult(values);

        //then
        //value should be: ( 1*5.0 + 2*3.5 ) / (1 + 2) = (5+7)/3 = 4

        Assertions.assertThat(v).isEqualTo(4.0, Offset.offset(0.0000000001));
    }

}