package oo.max.dndexperiencesplitter.playerpicking.model;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import oo.max.dndexperiencesplitter.expsplitting.model.ExpParam;
import oo.max.dndexperiencesplitter.player.model.Player;

@Getter
public class PickingRequest implements Serializable{

    private final List<Long> playerIds;

    @Setter
    private int baseExp;

    @Setter
    private int maxBonus;

    public PickingRequest(Set<Player> pickedPlayers) {
        playerIds = FluentIterable.from(pickedPlayers).transform(new Function<Player, Long>() {
            @Override
            public Long apply(Player input) {
                return input.getId();
            }
        }).toList();
    }

    public ExpParam getExpParam() {
        return ExpParam.builder()
                .baseExp(baseExp)
                .maxBonus(maxBonus)
                .build();
    }

}
