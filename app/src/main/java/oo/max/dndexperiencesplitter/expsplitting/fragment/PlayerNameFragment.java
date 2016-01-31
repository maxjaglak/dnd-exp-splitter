package oo.max.dndexperiencesplitter.expsplitting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.player.model.Player;

public class PlayerNameFragment extends Fragment {

    private Player player;

    @Bind(R.id.player_name)
    TextView playerName;

    @Bind(R.id.character_name)
    TextView characterName;

    public static PlayerNameFragment create(Player player) {
        PlayerNameFragment playerNameFragment = new PlayerNameFragment();
        playerNameFragment.player = player;
        return playerNameFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splitting_player_name, container, false);
        ButterKnife.bind(this, view);
        playerName.setText(player.getName());
        characterName.setText(player.getCharacterName());
        return view;
    }
}
