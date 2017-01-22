package dao.impl;

import dao.ScheduleTableDao;
import entities.Auditorium;
import entities.Event;
import org.h2.jdbc.JdbcArray;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by NICK on 05.01.2017
 */
@Repository
public class ScheduleTableDaoImpl extends AbstractDao implements ScheduleTableDao {
    private static final String INSERT_SCHEDULED_EVENT = "INSERT INTO scheduled (event_id, date_time, name, num_of_seats, vip_seats) VALUES(?, ?, ?, ?, ?)";
    private static final String SELECT_SCHEDULED_EVENT = "SELECT * FROM scheduled WHERE event_id = ? AND date_time = ?";

    @Override
    public Auditorium addEventToAuditorium(Event event, Auditorium auditorium, LocalDateTime dateTime) {
        getJdbcTemplate().update(INSERT_SCHEDULED_EVENT, event.getId(), Timestamp.valueOf(dateTime),
                auditorium.getName(), auditorium.getNumberOfSeats(), auditorium.getVipSeats().toArray(new Integer[0]));
        return auditorium;
    }

    @Override
    public Auditorium getAuditoriumByEventAndDate(Event event, LocalDateTime dateTime) {
        return getJdbcTemplate().query(SELECT_SCHEDULED_EVENT, getAuditoriumResultSetExtractor(), event.getId(), Timestamp.valueOf(dateTime));
    }

    private static ResultSetExtractor<Auditorium> getAuditoriumResultSetExtractor() {
        return (ResultSet rs) -> {
            if (rs.next()) {
                String name = rs.getString("name");
                int numOfSeats = rs.getInt("num_of_seats");
                Set<Integer> vipSeats = getVipSeats(rs);

                return new Auditorium(name, numOfSeats, vipSeats);
            }
            return null;
        };
    }

    private static Set<Integer> getVipSeats(ResultSet rs) throws SQLException {
        Array vip_seats = rs.getArray("vip_seats");
        return new HashSet<>(Arrays.asList((Integer[]) vip_seats.getArray()));
    }
}
