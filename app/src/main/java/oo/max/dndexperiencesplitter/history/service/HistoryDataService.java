package oo.max.dndexperiencesplitter.history.service;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import oo.max.dndexperiencesplitter.expsplitting.model.ExpResult;
import oo.max.dndexperiencesplitter.history.dao.HistoryDao;
import oo.max.dndexperiencesplitter.history.model.HistoryEntry;
import oo.max.dndexperiencesplitter.history.model.PlayerHistoryEntry;

@Singleton
public class HistoryDataService {

    private final HistoryDao historyDao;

    @Inject
    public HistoryDataService(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    public void saveExpSplittingResult(List<ExpResult> expResults) {
        List<PlayerHistoryEntry> players = buildPlayerHistoryEntries(expResults);

        HistoryEntry historyEntry = HistoryEntry.builder()
                .dateTime(DateTime.now())
                .players(players)
                .build();

        historyDao.save(historyEntry);
    }

    private List<PlayerHistoryEntry> buildPlayerHistoryEntries(List<ExpResult> expResults) {
        return FluentIterable.from(expResults).transform(new Function<ExpResult, PlayerHistoryEntry>() {
            @Override
            public PlayerHistoryEntry apply(ExpResult input) {
                return PlayerHistoryEntry.builder()
                        .characterName(input.getPlayer().getCharacterName())
                        .playerName(input.getPlayer().getName())
                        .totalExp(input.getBase()+input.getBonus())
                        .build();
            }
        }).toList();
    }
}
