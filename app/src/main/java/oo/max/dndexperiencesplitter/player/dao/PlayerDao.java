package oo.max.dndexperiencesplitter.player.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

import oo.max.dndexperiencesplitter.db.AbstractDao;
import oo.max.dndexperiencesplitter.db.DatabaseHelper;
import oo.max.dndexperiencesplitter.player.model.Player;

@Singleton
public class PlayerDao extends AbstractDao<Player, Long>{

    @Inject
    public PlayerDao(DatabaseHelper databaseHelper) {
        super(databaseHelper, Player.class);
    }

    @Override
    protected Long getId(Player item) {
        return item.getId();
    }
}
