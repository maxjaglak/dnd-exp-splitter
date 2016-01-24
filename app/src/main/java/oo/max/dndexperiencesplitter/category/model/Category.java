package oo.max.dndexperiencesplitter.category.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@DatabaseTable(tableName = "categories")
public class Category {

    @DatabaseField(id = true)
    private String name;

    @DatabaseField
    private int value;

}
