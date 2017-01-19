package dao.impl;

import dao.EventDao;
import entities.Event;
import entities.Rating;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by macbook on 03.01.17
 */
@Repository
public class EventDaoImpl extends AbstractDao implements EventDao {
    private static final String SELECT_ALL_EVENT = "SELECT * FROM events;";
    private static final String SELECT_EVENT_BY_NAME = "SELECT * FROM events WHERE name = ?;";
    private static final String SELECT_EVENT_BY_ID = "SELECT * FROM events WHERE id = ?;";
    private static final String INSERT_EVENT = "INSERT INTO events(name, base_price, rating) VALUES(?, ?, ?);";
    private static final String DELETE_EVENT = "DELETE FROM events WHERE id = ?";

    private long currentId = 1;
    private Map<Long, Event> events = new HashMap<>();

    @Override
    public long save(String name, double basePrice, Rating rating) {
        final KeyHolder holder = new GeneratedKeyHolder();

        getJdbcTemplate().update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(INSERT_EVENT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setDouble(2, basePrice);
            ps.setString(3, rating.toString());
            return ps;
        }, holder);

        return holder.getKey().longValue();
    }

    @Override
    public void remove(long id) {
        getJdbcTemplate().update(DELETE_EVENT, id);
    }

    @Override
    public Event getById(long id) {
        return getJdbcTemplate().queryForObject(SELECT_EVENT_BY_ID, getEventRowMapper(), id);
    }

    @Override
    public Event getEventByName(String name) {
        return getJdbcTemplate().queryForObject(SELECT_EVENT_BY_NAME, getEventRowMapper(), name);
    }

    @Override
    public List<Event> getAll() {
        return getJdbcTemplate().query(SELECT_ALL_EVENT, getEventRowMapper());
    }

    private static RowMapper<Event> getEventRowMapper() {
        return (rm, i) -> {
            long id = rm.getLong("id");
            String name = rm.getString("name");
            double base_price = rm.getDouble("base_price");
            Rating rating = Rating.valueOf(rm.getString("rating"));
            return new Event(id, name, base_price, rating);
        };
    }
}
