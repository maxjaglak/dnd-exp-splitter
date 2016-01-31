package oo.max.dndexperiencesplitter.expsplitting.service;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;
import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.expsplitting.model.ExpParam;
import oo.max.dndexperiencesplitter.expsplitting.model.ExpResult;
import oo.max.dndexperiencesplitter.expsplitting.model.SingleAssessmentResult;
import oo.max.dndexperiencesplitter.player.model.Player;

@Singleton
public class ExpSplittingManager {

    public static final double MAX_ASSESSMENT_VALUE = 5.0;

    private List<Player> players;
    private List<Category> categories;

    @Getter
    private final List<SingleAssessmentResult> results = new LinkedList<>();
    private ExpParam expParam;

    @Inject
    public ExpSplittingManager() {}

    public void startSplitting(List<Player> players,
                               List<Category> categories,
                               ExpParam expParam) {
        results.clear();
        this.players = players;
        this.categories = categories;
        this.expParam = expParam;
    }

    public List<Player> getPlayersToBeAssessedByPlayer(final Player player) {
        return FluentIterable.from(players).filter(new Predicate<Player>() {
            @Override
            public boolean apply(Player input) {
                return !input.getId().equals(player.getId())
                        && !input.isGameMaster();
            }
        }).toList();
    }

    public void savePlayerResult(SingleAssessmentResult singleAssessmentResult) {
        if(results.contains(singleAssessmentResult)) {
            results.remove(singleAssessmentResult);
        }
        results.add(singleAssessmentResult);
    }

    public Optional<SingleAssessmentResult> findAssessmentResult(final Player assessedPlayer,
                                                                 final Player assessingPlayer,
                                                                 final Category category) {
        return FluentIterable.from(results).firstMatch(new Predicate<SingleAssessmentResult>() {
            @Override
            public boolean apply(SingleAssessmentResult input) {
                return input.getAssessedPlayer().equals(assessedPlayer)
                        && input.getAssessingPlayer().equals(assessingPlayer)
                        && input.getCategory().equals(category);
            }
        });
    }

    public List<ExpResult> calculateExp() {
        List<ExpResult> expResult = new LinkedList<>();
        for (Player player : players) {
            if(!player.isGameMaster()) {
                expResult.add(calculateExpForPlayer(player, expParam));
            }
        }

        Optional<ExpResult> gmResult = calculateGmResult(expParam, expResult);
        if(gmResult.isPresent()) {
            expResult.add(gmResult.get());
        }

        return expResult;
    }

    private ExpResult calculateExpForPlayer(Player player, ExpParam expParam) {
        Map<Category, Double> categoryValues = new HashMap<>();
        for (Category category : categories) {
            double averageCategoryValue = calculateAverageCategoryValue(category, player);
            categoryValues.put(category, averageCategoryValue);
        }

        double weightedAverageResult = weightedAverageResult(categoryValues);
        int bonusExp = (int) (expParam.getMaxBonus() * weightedAverageResult / MAX_ASSESSMENT_VALUE);

        return ExpResult.builder()
                .player(player)
                .base(expParam.getBaseExp())
                .bonus(bonusExp)
                .build();
    }

    double calculateAverageCategoryValue(Category category, Player player) {
        int valueSum = 0;
        for (SingleAssessmentResult result : results) {
            if(result.getCategory().equals(category) && result.getAssessedPlayer().equals(player)) {
                valueSum += result.getValue();
            }
        }
        return (double) valueSum / (double) (players.size() - 1);
    }

    double weightedAverageResult(Map<Category, Double> categoryValues) {
        double sum = 0.0;
        double weightsSum = 0.0;

        for (Map.Entry<Category, Double> entry : categoryValues.entrySet()) {
            sum += entry.getValue() * (double) entry.getKey().getValue();
            weightsSum += (double) entry.getKey().getValue();
        }

        return sum / weightsSum;
    }

    private Optional<ExpResult> calculateGmResult(ExpParam expParam, List<ExpResult> expResults) {
        Optional<Player> gmPlayer = FluentIterable.from(players).firstMatch(new Predicate<Player>() {
            @Override
            public boolean apply(Player input) {
                return input.isGameMaster();
            }
        });

        if(!gmPlayer.isPresent()) {
            return Optional.absent();
        }

        int bonusSum = 0;
        for (ExpResult result : expResults) {
            bonusSum += result.getBonus();
        }
        int gmBonus = bonusSum / expResults.size();

        ExpResult gmResult = ExpResult.builder()
                .base(expParam.getBaseExp())
                .bonus(gmBonus)
                .player(gmPlayer.get())
                .build();

        return Optional.of(gmResult);
    }

}
