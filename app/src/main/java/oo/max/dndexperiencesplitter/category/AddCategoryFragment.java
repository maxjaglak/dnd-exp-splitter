package oo.max.dndexperiencesplitter.category;

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
import oo.max.dndexperiencesplitter.category.dao.CategoryDao;
import oo.max.dndexperiencesplitter.category.event.CategoryUpdatedEvent;
import oo.max.dndexperiencesplitter.category.model.Category;

public class AddCategoryFragment extends DialogFragment {

    @Inject
    CategoryDao categoryDao;

    @Bind(R.id.category_name)
    EditText categoryName;

    @Bind(R.id.category_value)
    EditText categoryValue;

    @Inject
    EventBus eventBus;

    public static void show(FragmentManager fragmentManager) {
        AddCategoryFragment addCategoryFragment = new AddCategoryFragment();
        addCategoryFragment.show(fragmentManager, AddCategoryFragment.class.getSimpleName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExpApplication.getApp(getActivity()).getInjector().getApplicationComponent().inject(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_category_add,
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

    @OnClick(R.id.save)
    public void save() {
        if(!validate()) {
            return;
        }

        saveNewPlayer();
    }

    private boolean validate() {
        if(categoryName.getText().toString().isEmpty()) {
            categoryName.setError(getActivity().getString(R.string.player_name_required));
            return false;
        }

        if(categoryValue.getText().toString().isEmpty()) {
            categoryValue.setError(getActivity().getString(R.string.category_value_required));
            return false;
        }

        return true;
    }

    private void saveNewPlayer() {
        Category category = Category.builder()
                .name(categoryName.getText().toString())
                .value(Integer.parseInt(categoryValue.getText().toString()))
                .build();

        new SaveCategoryTask(category).execute();
    }

    private class SaveCategoryTask extends AsyncTask {

        private final Category category;

        private SaveCategoryTask(Category category) {
            this.category = category;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            categoryDao.save(category);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            eventBus.post(new CategoryUpdatedEvent());
            dismiss();
        }
    }
}
