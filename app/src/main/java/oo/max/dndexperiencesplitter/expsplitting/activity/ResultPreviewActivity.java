package oo.max.dndexperiencesplitter.expsplitting.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.expsplitting.adapter.ResultAdapter;
import oo.max.dndexperiencesplitter.expsplitting.model.ExpResult;
import oo.max.dndexperiencesplitter.expsplitting.service.ExpSplittingManager;

public class ResultPreviewActivity extends AbstractBaseActivity {

    @Inject
    ExpSplittingManager expSplittingManager;

    @Bind(R.id.recycler_view)
    RecyclerView expResultsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splitting_result);
        List<ExpResult> expResults = expSplittingManager.calculateExp();
        expResultsRecyclerView.setAdapter(new ResultAdapter(this, expResults));
        expResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
