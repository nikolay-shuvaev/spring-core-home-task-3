package dao.impl;

import dao.PurchasedTicketDao;
import entities.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by NICK on 06.01.2017.
 */
@Repository
public class PurchasedTicketDaoImpl extends AbstractDao implements PurchasedTicketDao {
    private static final String SELECT_TICKET_BY_EVENT_ID_AND_TIMESTAMP = "SELECT * FROM tickets " +
            "LEFT JOIN  users ON tickets.user_id = users.id " +
            "LEFT JOIN events ON tickets.event_id = events.id " +
            "WHERE tickets.event_id = ? AND tickets.date_time = ?";

    private static final String SELECT_TICKET_BY_USER_ID = "SELECT * FROM tickets " +
            "LEFT JOIN  users ON tickets.user_id = users.id " +
            "LEFT JOIN events ON tickets.event_id = events.id " +
            "WHERE tickets.user_id = ?";

    private static final String INSERT_TICKET = "INSERT INTO tickets (user_id, event_id, date_time,seat_number, seat_type) VALUES(?, ?, ?, ?, ?);";

    @Override
    public long saveTicket(Ticket ticket) {
        final KeyHolder holder = new GeneratedKeyHolder();

        getJdbcTemplate().update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(INSERT_TICKET, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, ticket.getUser() == null ? -1 : ticket.getUser().getId());
            ps.setLong(2, ticket.getEvent().getId());
            ps.setTimestamp(3, Timestamp.valueOf(ticket.getDateTime()));
            ps.setInt(4, ticket.getSeat().getSeatNumber());
            ps.setString(5, ticket.getSeat().getType().toString());
            return ps;
        }, holder);

        return holder.getKey().longValue();
    }

    @Override
    public List<Ticket> getBy(Event event, LocalDateTime dateTime) {
        return getJdbcTemplate().query(SELECT_TICKET_BY_EVENT_ID_AND_TIMESTAMP, getTicketRowMapper(), event.getId(), Timestamp.valueOf(dateTime));
    }

    @Override
    public List<Ticket> getBy(User user) {
        return getJdbcTemplate().query(SELECT_TICKET_BY_USER_ID, getTicketRowMapper(), user.getId());
    }

    @Override
    public Set<Seat> getOccupiedSeats(Event event, LocalDateTime dateTime) {
        List<Ticket> tickets = getBy(event, dateTime);
        return tickets == null ? null : tickets.stream().map(Ticket::getSeat).collect(Collectors.toSet());
    }

    private RowMapper<Ticket> getTicketRowMapper() {
        return (ResultSet rs, int i) -> Ticket.of(getEvent(rs), getTicketDate(rs), getSeat(rs)).add(getUser(rs));
    }

    private User getUser(ResultSet rs) throws SQLException {
        long id = rs.getLong("users.id");
        if (id > 0) {
            String name = rs.getString("users.name");
            Date birthday = rs.getDate("users.birthday");
            String email = rs.getString("users.email");
            return new User(id, name, birthday.toLocalDate(), email);
        }
        return null;
    }

    private LocalDateTime getTicketDate(ResultSet rs) throws SQLException {
        Timestamp timestamp = rs.getTimestamp("tickets.date_time");
        return timestamp.toLocalDateTime();
    }

    private Event getEvent(ResultSet rs) throws SQLException {
        long id = rs.getLong("events.id");
        String name = rs.getString("events.name");
        double basePrice = rs.getDouble("events.base_price");
        Rating rating = Rating.valueOf(rs.getString("events.rating"));
        return new Event(id, name, basePrice, rating);
    }

    public Seat getSeat(ResultSet rs) throws SQLException {
        int seatNumber = rs.getInt("tickets.seat_number");
        SeatType seatType = SeatType.valueOf(rs.getString("tickets.seat_type"));
        return Seat.of(seatNumber, seatType);
    }
}
