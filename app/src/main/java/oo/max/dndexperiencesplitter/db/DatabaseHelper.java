package oo.max.dndexperiencesplitter.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.common.collect.ImmutableList;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import oo.max.dndexperiencesplitter.app.Logger;
import oo.max.dndexperiencesplitter.category.model.Category;
import oo.max.dndexperiencesplitter.history.model.HistoryEntry;
import oo.max.dndexperiencesplitter.history.model.PlayerHistoryEntry;
import oo.max.dndexperiencesplitter.player.model.Player;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private final List<Class<?>> clazzes = ImmutableList.of(Player.class,
            Category.class,
            HistoryEntry.class,
            PlayerHistoryEntry.class);

    public DatabaseHelper(Context context) {
        super(context, "oo.max.dndexpsplitter", null, 7);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for (Class<?> clazz : clazzes) {
                TableUtils.createTable(connectionSource, clazz);
            }
        } catch (SQLException e) {
            Logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            dropAllTables(connectionSource);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private void dropAllTables(ConnectionSource connectionSource) throws SQLException {
        for (Class<?> clazz : clazzes) {
            TableUtils.dropTable(connectionSource, clazz, true);
        }
    }

}
