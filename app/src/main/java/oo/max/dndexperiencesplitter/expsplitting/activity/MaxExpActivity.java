package oo.max.dndexperiencesplitter.expsplitting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.playerpicking.model.PickingRequest;

public class MaxExpActivity extends AbstractBaseActivity {

    private PickingRequest pickingRequest;

    @Bind(R.id.base_exp)
    EditText baseExpEdit;

    @Bind(R.id.max_bonus)
    EditText maxBonusEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splitting_max_exp);
        loadParamsFromIntent();
    }

    private void loadParamsFromIntent() {
        pickingRequest = (PickingRequest) getIntent().getSerializableExtra(SurveyActivity.PICKING_REQUEST_PARAM);
    }

    @OnTextChanged(R.id.base_exp)
    public void onBaseExpTextChanged() {
        validateBaseExp();
    }

    private boolean validateBaseExp() {
        return validateEdit(baseExpEdit);
    }

    @OnTextChanged(R.id.max_bonus)
    public void onMaxBonusTextChanged() {
        validateMaxBonus();
    }

    private boolean validateMaxBonus() {
        return validateEdit(maxBonusEdit);
    }

    private boolean validateEdit(EditText edit) {
        String baseExp = edit.getText().toString();

        if (baseExp.isEmpty()) {
            edit.setError(getString(R.string.value_is_required));
            return false;
        }

        try {
            Integer.parseInt(baseExp);
        } catch (NumberFormatException e) {
            edit.setError(getString(R.string.value_must_be_a_number));
            return false;
        }

        return true;
    }

    @OnClick(R.id.ok)
    public void saveExp() {
        if (!validate()) {
            return;
        }

        pickingRequest.setBaseExp(Integer.parseInt(baseExpEdit.getText().toString()));
        pickingRequest.setMaxBonus(Integer.parseInt(maxBonusEdit.getText().toString()));

        Intent intent = new Intent(this, SurveyActivity.class);
        intent.putExtra(SurveyActivity.PICKING_REQUEST_PARAM, pickingRequest);
        startActivity(intent);
        finish();
    }

    private boolean validate() {
        if (!validateBaseExp()) return false;

        if (!validateMaxBonus()) return false;

        return true;
    }

}