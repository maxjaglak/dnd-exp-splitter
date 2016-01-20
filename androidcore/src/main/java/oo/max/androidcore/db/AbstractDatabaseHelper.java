package oo.max.androidcore.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import oo.max.androidcore.log.Logger;

public abstract class AbstractDatabaseHelper extends OrmLiteSqliteOpenHelper {

    protected AbstractDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for (Class<?> clazz : getClazzes()) {
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
        for (Class<?> clazz : getClazzes()) {
            TableUtils.dropTable(connectionSource, clazz, true);
        }
    }

    protected abstract List<Class<?>> getClazzes();
}
