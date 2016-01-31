package oo.max.dndexperiencesplitter.expsplitting.model;

import lombok.Getter;
import lombok.experimental.Builder;
import oo.max.dndexperiencesplitter.player.model.Player;

@Builder
@Getter
public class ExpResult {

    private Player player;

    private int base;

    private int bonus;

    public int total() {
        return base + bonus;
    }

}
