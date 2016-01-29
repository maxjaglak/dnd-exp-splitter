package oo.max.dndexperiencesplitter.player.action;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;

import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import oo.max.dndexperiencesplitter.player.adapter.PlayerAdapter;
import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.player.model.Player;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoadPlayersActionTest {

    @Mock
    PlayerDao playerDao;

    @Mock
    RecyclerView recyclerView;

    @Mock
    FragmentActivity fragmentActivity;

    @Mock
    FragmentManager fragmentManager;

    private LoadPlayersAction loadPlayersAction;

    @Before
    public void setup() {
        when(fragmentActivity.getSupportFragmentManager()).thenReturn(fragmentManager);
        when(recyclerView.getContext()).thenReturn(fragmentActivity);

        loadPlayersAction = new LoadPlayersAction(playerDao);
    }

    @Test
    public void shouldGetPlayersFromDaoAndSetPlayersAdapterToRecycleView() {
        //given
        Player player = Player.builder()
                .id(1l)
                .name("Max")
                .build();

        final ImmutableList<Player> players = ImmutableList.of(player);

        when(playerDao.get()).thenReturn(players);

        //when
        loadPlayersAction.load(recyclerView);
        loadPlayersAction.loadInBackground();
        loadPlayersAction.after();

        //then
        verify(recyclerView, times(1)).setAdapter(Matchers.argThat(new ArgumentMatcher<RecyclerView.Adapter>() {
            @Override
            public boolean matches(Object argument) {
                PlayerAdapter adapter = (PlayerAdapter) argument;
                return adapter.getPlayers().equals(players);
            }
        }));
    }


}