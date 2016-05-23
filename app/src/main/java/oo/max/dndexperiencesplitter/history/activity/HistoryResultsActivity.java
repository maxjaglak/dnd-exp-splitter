package oo.max.dndexperiencesplitter.history.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.Bind;
import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.core.activity.AbstractBaseActivity;
import oo.max.dndexperiencesplitter.history.adapter.HistoryResultsAdapter;
import oo.max.dndexperiencesplitter.history.dao.HistoryDao;

public class HistoryResultsActivity extends AbstractBaseActivity {

    @Inject
    HistoryDao historyDao;

    @Bind(R.id.results_recycler_view)
    RecyclerView resultsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_results);

        resultsRecyclerView.setAdapter(new HistoryResultsAdapter(this, historyDao.getResults()));
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
