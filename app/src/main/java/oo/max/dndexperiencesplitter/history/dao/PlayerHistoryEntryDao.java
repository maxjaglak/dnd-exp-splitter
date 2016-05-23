package oo.max.dndexperiencesplitter.history.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import oo.max.dndexperiencesplitter.db.AbstractDao;
import oo.max.dndexperiencesplitter.db.DatabaseHelper;
import oo.max.dndexperiencesplitter.history.model.PlayerHistoryEntry;

@Singleton
public class PlayerHistoryEntryDao extends AbstractDao<PlayerHistoryEntry, Long> {

    @Inject
    public PlayerHistoryEntryDao(DatabaseHelper databaseHelper) {
        super(databaseHelper, PlayerHistoryEntry.class);
    }

    @Override
    protected Long getId(PlayerHistoryEntry item) {
        return item.getId();
    }
}
