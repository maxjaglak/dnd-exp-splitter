package oo.max.dndexperiencesplitter.player.dao;

import com.google.common.collect.ImmutableSet;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

import oo.max.dndexperiencesplitter.db.DatabaseHelper;
import oo.max.dndexperiencesplitter.player.model.Player;
import oo.max.dndexperiencesplitter.playerpicking.model.PickingRequest;

@RunWith(RobolectricTestRunner.class)
public class PlayerDaoTest {

    private PlayerDao playerDao;
    private Player player1;
    private Player player2;

    @Before
    public void setup() {
        DatabaseHelper databaseHelper = new DatabaseHelper(RuntimeEnvironment.application);
        playerDao = new PlayerDao(databaseHelper);

        player1 = Player.builder()
                .id(1l)
                .build();

        player2 = Player.builder()
                .id(2l)
                .build();

        playerDao.save(player1);
        playerDao.save(player2);
    }

    @Test
    public void shouldGetPlayersBasedOnPickingRequest() {
        //given
        PickingRequest pickingRequest = new PickingRequest(ImmutableSet.of(player1));

        //when
        List<Player> players = playerDao.get(pickingRequest);

        //then
        Assertions.assertThat(players).extracting("id").containsExactly(1l);
    }

    @Test
    public void shouldGetPlayersEmptyListWhenPickingRequestIsEmpty() {
        //given
        PickingRequest pickingRequest = new PickingRequest(ImmutableSet.<Player>of());

        //when
        List<Player> players = playerDao.get(pickingRequest);

        //then
        Assertions.assertThat(players).isEmpty();
    }

    @Test
    public void shouldGetMoreThanOnePlayerWithPickingRequest() {
        //given
        PickingRequest pickingRequest = new PickingRequest(ImmutableSet.of(player1, player2));

        //when
        List<Player> players = playerDao.get(pickingRequest);

        //then
        Assertions.assertThat(players).extracting("id").contains(1l, 2l);
    }

}