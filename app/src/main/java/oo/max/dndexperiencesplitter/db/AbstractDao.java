package oo.max.dndexperiencesplitter.db;

import com.google.common.base.Optional;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import oo.max.dndexperiencesplitter.app.Logger;


public abstract class AbstractDao<T, IdType> {

    protected final DatabaseHelper databaseHelper;
    protected final Class<T> clazz;

    protected final Dao<T, IdType> dao;

    protected AbstractDao(DatabaseHelper databaseHelper, Class<T> clazz) {
        this.databaseHelper = databaseHelper;
        this.clazz = clazz;

        try {
            dao = DaoManager.createDao(databaseHelper.getConnectionSource(), clazz);
        } catch (SQLException e) {
            Logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public T save(T item) {
        try {
            dao.create(item);
            return item;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(T item) {
        try {
            dao.update(item);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(List<T> data) {
        for (T t : data) {
            save(t);
        }
    }

    public List<T> get() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPresent(T item) {
        try {
            T itemFromDao = dao.queryForId(getId(item));
            return itemFromDao != null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(T item) {
        try {
            dao.delete(item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        try {
            TableUtils.clearTable(dao.getConnectionSource(), clazz);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<T> getById(IdType id) {
        try {
            return Optional.fromNullable(dao.queryForId(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract IdType getId(T item);

}
