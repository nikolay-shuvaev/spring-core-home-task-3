package dao.impl;

import dao.UserDao;
import entities.Ticket;
import entities.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by macbook on 03.01.17
 */
@Repository
public class UserDaoImpl extends AbstractDao implements UserDao {
    private static final String SELECT_ALL_USER = "SELECT * FROM users;";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?;";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?;";
    private static final String INSERT_USER = "INSERT INTO users(name, birthday,email) VALUES(?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    private Map<Long, User> users = new HashMap<>();

    @Override
    public long save(String name, LocalDate birthday, String email) {
        final KeyHolder holder = new GeneratedKeyHolder();

        getJdbcTemplate().update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setDate(2, Date.valueOf(birthday));
            ps.setString(3, email);
            return ps;
        }, holder);

        return holder.getKey().longValue();
    }

    @Override
    public void remove(long id) {
        getJdbcTemplate().update(DELETE_USER, id);
    }

    @Override
    public User getById(long id) {
        return getJdbcTemplate().queryForObject(SELECT_USER_BY_ID, getUserRowMapper(), id);

    }

    @Override
    public User getUserByEmail(String email) {
        return getJdbcTemplate().queryForObject(SELECT_USER_BY_EMAIL, getUserRowMapper(), email);
    }

    @Override
    public List<User> getAll() {
        return getJdbcTemplate().query(SELECT_ALL_USER, getUserRowMapper());
    }

    public void addPurchasedTicket(User user, Ticket ticket) {
        User storedUser = users.get(user.getId());
        storedUser.getTickets().add(ticket);
    }

    private static RowMapper<User> getUserRowMapper() {
        return (rm, i) -> {
            long user_id = rm.getLong("id");
            String name = rm.getString("name");
            Date birthday = rm.getDate("birthday");
            String user_email = rm.getString("email");
            return new User(user_id, name, birthday.toLocalDate(), user_email);
        };
    }
}
