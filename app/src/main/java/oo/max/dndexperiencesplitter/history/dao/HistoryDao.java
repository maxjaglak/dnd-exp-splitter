package oo.max.dndexperiencesplitter.history.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import oo.max.dndexperiencesplitter.db.AbstractDao;
import oo.max.dndexperiencesplitter.db.DatabaseHelper;
import oo.max.dndexperiencesplitter.history.model.HistoryEntry;
import oo.max.dndexperiencesplitter.history.model.PlayerHistoryEntry;

@Singleton
public class HistoryDao extends AbstractDao<HistoryEntry, Long> {

    private final PlayerHistoryEntryDao playerHistoryEntryDao;

    @Inject
    public HistoryDao(DatabaseHelper databaseHelper,
                      PlayerHistoryEntryDao playerHistoryEntryDao) {
        super(databaseHelper, HistoryEntry.class);
        this.playerHistoryEntryDao = playerHistoryEntryDao;
    }

    @Override
    protected Long getId(HistoryEntry item) {
        return item.getId();
    }

    @Override
    public HistoryEntry save(HistoryEntry item) {
        HistoryEntry savedItem = super.save(item);
        for (PlayerHistoryEntry playerHistoryEntry : item.getPlayers()) {
            playerHistoryEntry.setHistoryEntry(savedItem);
            playerHistoryEntryDao.save(playerHistoryEntry);
        }
        return savedItem;
    }

    public List<HistoryEntry> getResults() {
        try {
            return dao.queryBuilder()
                    .orderBy("id", false)
                    .query();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
