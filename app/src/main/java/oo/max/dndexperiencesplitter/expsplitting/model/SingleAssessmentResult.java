package oo.max.dndexperiencesplitter.expsplitting.model;

import lombok.Getter;
import lombok.experimental.Builder;
import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.player.model.Player;

@Getter
@Builder
public class SingleAssessmentResult {

    private final Player assessedPlayer;

    private final Player assessingPlayer;

    private final Category category;

    private final int value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleAssessmentResult that = (SingleAssessmentResult) o;

        if (!assessedPlayer.equals(that.assessedPlayer)) return false;
        if (!assessingPlayer.equals(that.assessingPlayer)) return false;
        return category.equals(that.category);
    }

    @Override
    public int hashCode() {
        int result = assessedPlayer.hashCode();
        result = 31 * result + assessingPlayer.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }
}