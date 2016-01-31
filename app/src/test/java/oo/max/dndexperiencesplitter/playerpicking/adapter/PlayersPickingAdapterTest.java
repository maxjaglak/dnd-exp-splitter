package oo.max.dndexperiencesplitter.playerpicking.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.collect.ImmutableList;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import oo.max.dndexperiencesplitter.player.model.Player;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@RunWith(RobolectricTestRunner.class)
public class PlayersPickingAdapterTest  {

    @Mock
    Context context;

    @Mock
    TextView someTextView;

    private Player player;

    private PlayersPickingAdapter adapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        player = Player.builder()
                .name("name")
                .characterName("character name")
                .build();

        adapter = new PlayersPickingAdapter(RuntimeEnvironment.application,
                ImmutableList.of(player));
    }

    @Test
    public void shouldMarkPlayerPickedWhenItsClicked() {
        //when
        adapter.onPlayerSelected(player);

        //then
        Assertions.assertThat(adapter.getPickedPlayers()).contains(player);
    }

    @Test
    public void shouldRemovePlayerFromPickedPlayersWhenItsClickedAndAlreadySelected() {
        //given
        adapter.onPlayerSelected(player);

        //when
        adapter.onPlayerSelected(player);

        //then
        Assertions.assertThat(adapter.getPickedPlayers()).doesNotContain(player);
    }

    @Test
    public void shouldSelectedPlayerHaveSpecialBackground() {
        //given
        LinearLayout container = mock(LinearLayout.class);
        PlayerPickingViewHolder viewHolder = mock(PlayerPickingViewHolder.class);

        Mockito.when(viewHolder.getContainer()).thenReturn(container);
        Mockito.when(viewHolder.getPlayerName()).thenReturn(someTextView);
        Mockito.when(viewHolder.getCharacterName()).thenReturn(someTextView);

        adapter.onPlayerSelected(player);

        //when
        adapter.onBindViewHolder(viewHolder, 0);

        //then
        Mockito.verify(container, times(1)).setBackgroundColor(anyInt());
    }

    @Test
    public void shouldSetNullBackgroundWhenPlayerIsNotPicked() {
        //given
        LinearLayout container = mock(LinearLayout.class);
        PlayerPickingViewHolder viewHolder = mock(PlayerPickingViewHolder.class);

        Mockito.when(viewHolder.getContainer()).thenReturn(container);
        Mockito.when(viewHolder.getPlayerName()).thenReturn(someTextView);
        Mockito.when(viewHolder.getCharacterName()).thenReturn(someTextView);

        //when
        adapter.onBindViewHolder(viewHolder, 0);

        //then
        Mockito.verify(container, times(1)).setBackground(Matchers.<Drawable>any());
    }
}