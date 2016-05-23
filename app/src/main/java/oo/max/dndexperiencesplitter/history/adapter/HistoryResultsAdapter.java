package oo.max.dndexperiencesplitter.history.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import oo.max.dndexperiencesplitter.R;
import oo.max.dndexperiencesplitter.history.model.HistoryEntry;
import oo.max.dndexperiencesplitter.util.DatePrinters;

public class HistoryResultsAdapter extends RecyclerView.Adapter<HistoryResultViewHolder> {

    private final Context context;
    private final List<HistoryEntry> historyEntries;
    private final LayoutInflater layoutInflater;

    public HistoryResultsAdapter(Context context,
                                 List<HistoryEntry> historyEntries) {
        this.context = context;
        this.historyEntries = new ArrayList<>(historyEntries);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public HistoryResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_history_entry_item, parent, false);
        return new HistoryResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryResultViewHolder holder, int position) {
        HistoryEntry historyEntry = historyEntries.get(position);

        holder.getDateText().setText(DatePrinters.printDateTime(historyEntry.getDateTime()));
        holder.getPlayersData().setText(historyEntry.printPlayersData());
    }

    @Override
    public int getItemCount() {
        return historyEntries.size();
    }
}
