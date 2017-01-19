package dao.impl;

import dao.CounterDao;
import entities.StatisticEntry;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by NICK on 18.01.2017
 */
@Repository
public class CounterDaoImpl extends AbstractDao implements CounterDao {
    private static final String COUNT_ROWS_FOR_EVENT = "SELECT COUNT(*) FROM COUNTERS WHERE event_id = ?;";
    private static final String SELECT_ALL = "SELECT * FROM COUNTERS;";
    private static final String UPDATE_COUNTERS = "UPDATE counters SET {0} = {0} + 1 WHERE event_id = ?;";
    private static final String INSERT_COUNTERS = "INSERT INTO counters (event_id, {0}) VALUES(?, 1);";

    @Override
    public void incrementAccessedByName(long id) {
        getJdbcTemplate().update( getSqlStatement(id, "accessed_by_name"), id);
    }

    @Override
    public void incrementPriceQueried(long id) {
        getJdbcTemplate().update( getSqlStatement(id, "price_queried"), id);
    }

    @Override
    public void incrementTicketBooking(long id) {
        getJdbcTemplate().update( getSqlStatement(id, "ticket_booking"), id);
    }

    @Override
    public Map<Long, StatisticEntry> getAllByEventId() {
        return getJdbcTemplate().query(SELECT_ALL, rs -> {
            Map<Long, StatisticEntry> map = new LinkedHashMap<>();
            while (rs.next()) {
                StatisticEntry statisticEntry = new StatisticEntry();
                Long eventId = rs.getLong("event_id");
                statisticEntry.setAccessedByNameCount( rs.getLong("accessed_by_name"));
                statisticEntry.setPriceQueriedCount( rs.getLong("price_queried"));
                statisticEntry.setTicketBookingCount( rs.getLong("ticket_booking"));
                map.put(eventId, statisticEntry);
            }
            return map;
        });
    }

    private String getSqlStatement(long id, String columnName) {
        Integer count = getJdbcTemplate().queryForObject(COUNT_ROWS_FOR_EVENT, new Long[]{id}, Integer.class);
        return count > 0 ?
                MessageFormat.format(UPDATE_COUNTERS, columnName) :
                MessageFormat.format(INSERT_COUNTERS, columnName);
    }
}
