package oo.max.dndexperiencesplitter.player.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@DatabaseTable(tableName = "players")
public class Player  {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String characterName;

}
