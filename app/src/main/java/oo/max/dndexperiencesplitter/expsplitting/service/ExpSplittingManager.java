package oo.max.dndexperiencesplitter.expsplitting.service;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;
import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.expsplitting.model.SingleAssessmentResult;
import oo.max.dndexperiencesplitter.player.model.Player;

@Singleton
public class ExpSplittingManager {

    private List<Player> players;
    private List<Category> categories;

    @Getter
    private final List<SingleAssessmentResult> results = new LinkedList<>();

    @Inject
    public ExpSplittingManager() {}

    public void startSplitting(List<Player> players,
                               List<Category> categories) {
        this.players = players;
        this.categories = categories;
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
}
