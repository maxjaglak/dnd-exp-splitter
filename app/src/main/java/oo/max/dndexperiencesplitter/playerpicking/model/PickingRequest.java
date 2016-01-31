package oo.max.dndexperiencesplitter.playerpicking.model;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import oo.max.dndexperiencesplitter.player.model.Player;

public class PickingRequest implements Serializable{

    @Getter
    private final List<Long> playerIds;

    public PickingRequest(Set<Player> pickedPlayers) {
        playerIds = FluentIterable.from(pickedPlayers).transform(new Function<Player, Long>() {
            @Override
            public Long apply(Player input) {
                return input.getId();
            }
        }).toList();
    }

}
