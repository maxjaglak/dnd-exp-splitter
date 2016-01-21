package oo.max.dndexperiencesplitter.player.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.app.ExpApplication;
import oo.max.dndexperiencesplitter.player.dao.PlayerDao;
import oo.max.dndexperiencesplitter.player.event.PlayerUpdatedEvent;
import oo.max.dndexperiencesplitter.player.model.Player;

public class AddPlayerFragment extends DialogFragment {

    @Inject
    PlayerDao playerDao;

    @Bind(R.id.player_name)
    EditText playerName;

    @Inject
    EventBus eventBus;

    public static void show(FragmentManager fragmentManager) {
        AddPlayerFragment addPlayerFragment = new AddPlayerFragment();
        addPlayerFragment.show(fragmentManager, AddPlayerFragment.class.getSimpleName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExpApplication.getApp(getActivity()).getInjector().getApplicationComponent().inject(this);
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

    @OnClick(R.id.cancel)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.ok)
    public void add() {
        if(!validate()) {
            return;
        }

        saveNewPlayer();
    }

    private boolean validate() {
        if(playerName.getText().toString().isEmpty()) {
            playerName.setError(getActivity().getString(R.string.player_name_required));
            return false;
        }

        return true;
    }

    private void saveNewPlayer() {
        Player player = Player.builder()
                .name(playerName.getText().toString())
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
            playerDao.save(player);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            eventBus.post(new PlayerUpdatedEvent());
            dismiss();
        }
    }

}