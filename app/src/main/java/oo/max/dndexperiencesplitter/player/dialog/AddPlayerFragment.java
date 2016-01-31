package oo.max.dndexperiencesplitter.player.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.app.ExpApplication;
import oo.max.dndexperiencesplitter.core.AsyncLoader;
import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.player.event.PlayerUpdatedEvent;
import oo.max.dndexperiencesplitter.player.model.Player;

public class AddPlayerFragment extends DialogFragment implements LoaderManager.LoaderCallbacks {

    public static final String PLAYER_ID = "playerId";
    @Inject
    PlayerDao playerDao;

    @Bind(R.id.player_name)
    EditText playerName;

    @Bind(R.id.character_name)
    EditText characterName;

    @Bind(R.id.container)
    LinearLayout container;

    @Bind(R.id.progress_bar_container)
    RelativeLayout progressBarContainer;

    @Bind(R.id.game_master)
    CheckBox gameMaster;

    @Inject
    EventBus eventBus;

    private long playerId = -1;
    private Optional<Player> player = Optional.absent();

    public static void editPlayer(Player player, FragmentManager fragmentManager) {
        AddPlayerFragment addPlayerFragment = new AddPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(PLAYER_ID, player.getId());
        addPlayerFragment.setArguments(bundle);
        addPlayerFragment.show(fragmentManager, AddPlayerFragment.class.getSimpleName());
    }

    public static void addNewPlayer(FragmentManager fragmentManager) {
        AddPlayerFragment addPlayerFragment = new AddPlayerFragment();
        addPlayerFragment.show(fragmentManager, AddPlayerFragment.class.getSimpleName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExpApplication.getApp(getActivity()).getInjector().getApplicationComponent().inject(this);
        if(getArguments()!=null) {
            playerId = getArguments().getLong(PLAYER_ID, -1);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_player_add,
                null, false);

        ButterKnife.bind(this, view);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(playerId >= 0) {
            showWaitingLoader();
            getLoaderManager().restartLoader(0, null, this);
        }
    }

    private void showWaitingLoader() {
        progressBarContainer.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = progressBarContainer.getLayoutParams();
        layoutParams.height = container.getMeasuredHeight();
        progressBarContainer.setLayoutParams(layoutParams);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new AsyncLoader(getActivity()) {
            @Override
            protected Object load() {
                player = playerDao.getById(playerId);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        hideLoader();
        if(player.isPresent()) {
            playerName.setText(player.get().getName());
            characterName.setText(player.get().getCharacterName());
            gameMaster.setChecked(player.get().isGameMaster());
        }
    }

    private void hideLoader() {
        progressBarContainer.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        //nothing
    }

    @OnTextChanged(R.id.player_name)
    public void onNameTextChanged() {
        validatePlayer();
    }

    @OnTextChanged(R.id.character_name)
    public void onCharacterNameTextChanged() {
        validateCharacter();
    }

    @OnClick(R.id.cancel)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.ok)
    public void add() {
        if(!validate()) {
            return;
        }

        savePlayer();
    }

    private boolean validate() {
        if (!validatePlayer()) return false;

        if (!validateCharacter()) return false;

        return true;
    }

    private boolean validateCharacter() {
        if(characterName.getText().toString().isEmpty()) {
            characterName.setError(getActivity().getString(R.string.character_name_validation_message));
            return false;
        }
        return true;
    }

    private boolean validatePlayer() {
        if(playerName.getText().toString().isEmpty()) {
            playerName.setError(getActivity().getString(R.string.player_name_required));
            return false;
        }
        return true;
    }

    private void savePlayer() {
        showWaitingLoader();

        Long id = this.player.transform(new Function<Player, Long>() {
            @Override
            public Long apply(Player input) {
                return input.getId();
            }
        }).orNull();

        Player player = Player.builder()
                .id(id)
                .name(playerName.getText().toString())
                .characterName(characterName.getText().toString())
                .gameMaster(gameMaster.isChecked())
                .build();

        new SavePlayerTask(player).execute();
    }

    private class SavePlayerTask extends AsyncTask {

        private final Player player;

        private SavePlayerTask(Player player) {
            this.player = player;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            if(player.isGameMaster()) {
                playerDao.unsetGameMasterFromAllPlayers();
            }

            if(player.getId() != null) {
                playerDao.update(player);
            } else {
                playerDao.save(player);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            hideLoader();
            eventBus.post(new PlayerUpdatedEvent());
            dismiss();
        }
    }

}