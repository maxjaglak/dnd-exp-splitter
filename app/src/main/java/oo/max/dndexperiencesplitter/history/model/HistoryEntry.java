package oo.max.dndexperiencesplitter.history.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import org.joda.time.DateTime;

import java.util.Collection;

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

}
