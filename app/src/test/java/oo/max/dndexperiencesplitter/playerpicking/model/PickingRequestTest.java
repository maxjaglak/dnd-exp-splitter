package oo.max.dndexperiencesplitter.playerpicking.model;

import com.google.common.collect.ImmutableSet;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import oo.max.dndexperiencesplitter.player.model.Player;

public class PickingRequestTest {

    @Test
    public void shouldBuildIdsListFromPlayersSet() {
        //given
        Player player = Player.builder()
                .id(123l)
                .build();

        //when
        PickingRequest pickingRequest = new PickingRequest(ImmutableSet.of(player));

        //then
        Assertions.assertThat(pickingRequest.getPlayerIds()).containsExactly(123l);
    }

}