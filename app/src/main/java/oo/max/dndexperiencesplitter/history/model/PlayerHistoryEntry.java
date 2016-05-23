package oo.max.dndexperiencesplitter.history.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

@Builder
@Getter
@Setter
@DatabaseTable
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
public class PlayerHistoryEntry {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String playerName;

    @DatabaseField
    private String characterName;

    @DatabaseField
    private int totalExp;

    @DatabaseField(foreign = true, columnName = "players", foreignAutoRefresh = true)
    private HistoryEntry historyEntry;

}
