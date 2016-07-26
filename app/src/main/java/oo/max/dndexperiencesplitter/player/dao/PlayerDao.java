package oo.max.dndexperiencesplitter.player.dao;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import oo.max.dndexperiencesplitter.db.AbstractDao;
import oo.max.dndexperiencesplitter.db.DatabaseHelper;
import oo.max.dndexperiencesplitter.player.model.Player;
import oo.max.dndexperiencesplitter.playerpicking.model.PickingRequest;

@Singleton
public class PlayerDao extends AbstractDao<Player, Long> {

    @Inject
    public PlayerDao(DatabaseHelper databaseHelper) {
        super(databaseHelper, Player.class);
    }

    @Override
    protected Long getId(Player item) {
        return item.getId();
    }

    public List<Player> get(PickingRequest pickingRequest) {
        return FluentIterable
                .from(pickingRequest.getPlayerIds())
                .transform(new Function<Long, Optional<Player>>() {
                    @Override
                    public Optional<Player> apply(Long input) {
                        return getById(input);
                    }
                })
                .filter(new Predicate<Optional<Player>>() {
                    @Override
                    public boolean apply(Optional<Player> input) {
                        return input.isPresent();
                    }
                })
                .transform(new Function<Optional<Player>, Player>() {
                    @Override
                    public Player apply(Optional<Player> input) {
                        return input.get();
                    }
                }).toList();
    }

    public void unsetGameMasterFromAllPlayers() {
        try {
            UpdateBuilder<Player, Long> updateBuilder = dao.updateBuilder();
            updateBuilder.updateColumnValue("gameMaster", false);
            updateBuilder.update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Player> getByName(String name) {
        try {
            SelectArg nameArg = new SelectArg(name);
            List<Player> players = dao.queryBuilder()
                    .where()
                    .eq("name", nameArg)
                    .query();

            return firstOrAbsent(players);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Player> getByCharacterName(String characterName) {
        try {
            SelectArg characterNameArg = new SelectArg(characterName);
            List<Player> players = dao.queryBuilder()
                    .where()
                    .eq("characterName", characterNameArg)
                    .query();

            return firstOrAbsent(players);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Player> firstOrAbsent(List<Player> players) {
        if(!players.isEmpty()) {
            return Optional.fromNullable(players.get(0));
        }

        return Optional.absent();
    }
}
