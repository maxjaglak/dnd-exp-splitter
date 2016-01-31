package oo.max.dndexperiencesplitter.playerpicking.action;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.player.model.Player;
import oo.max.dndexperiencesplitter.playerpicking.adapter.PlayersPickingAdapter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class LoadPlayersForPickingActionTest {

    @Mock
    RecyclerView recyclerView;

    @Mock
    PlayerDao playerDao;

    private LoadPlayersForPickingAction loadPlayersForPickingAction;

    @Before
    public void setup() {
        loadPlayersForPickingAction = new LoadPlayersForPickingAction(playerDao);
    }

    @Test
    public void shouldSetPlayerPickingAdapterToTargetRecyclerView() {
        //given
        final Player player = Player.builder().build();

        Mockito.when(playerDao.get()).thenReturn(ImmutableList.of(player));

        //when
        loadPlayersForPickingAction.load(recyclerView);
        loadPlayersForPickingAction.loadInBackground();
        loadPlayersForPickingAction.after();

        //then
        Mockito.verify(recyclerView, times(1)).setAdapter(argThat(new ArgumentMatcher<RecyclerView.Adapter>() {
            @Override
            public boolean matches(Object argument) {
                PlayersPickingAdapter playersPickingAdapter = (PlayersPickingAdapter) argument;
                return playersPickingAdapter.getPlayersToPick().get(0).equals(player);
            }
        }));

        Mockito.verify(recyclerView, times(1)).setLayoutManager(any(LinearLayoutManager.class));
    }

}