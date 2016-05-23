package oo.max.dndexperiencesplitter.history.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import oo.max.dndexperiencesplitter.db.AbstractDao;
import oo.max.dndexperiencesplitter.db.DatabaseHelper;
import oo.max.dndexperiencesplitter.history.model.HistoryEntry;

@Singleton
public class HistoryDao extends AbstractDao<HistoryEntry, Long> {

    @Inject
    public HistoryDao(DatabaseHelper databaseHelper) {
        super(databaseHelper, HistoryEntry.class);
    }

    @Override
    protected Long getId(HistoryEntry item) {
        return item.getId();
    }
}
