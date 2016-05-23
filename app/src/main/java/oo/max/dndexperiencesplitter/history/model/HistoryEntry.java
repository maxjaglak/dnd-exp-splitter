package oo.max.dndexperiencesplitter.history.model;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import org.joda.time.DateTime;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
public class HistoryEntry {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private DateTime dateTime;

    @ForeignCollectionField
    Collection<PlayerHistoryEntry> players;

    public String printPlayersData() {
        List<String> strings = FluentIterable.from(players).transform(new Function<PlayerHistoryEntry, String>() {
            @Override
            public String apply(PlayerHistoryEntry input) {
                return input.printData();
            }
        }).toList();

        return Joiner.on(System.lineSeparator()).join(strings);
    }

}
