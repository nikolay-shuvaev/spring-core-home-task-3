package dao.impl;

import dao.DiscountCounterDao;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Nikolai_Shuvaev on 1/19/2017
 */
@Repository
public class DiscountCounterDaoImpl extends AbstractDao implements DiscountCounterDao {
    private static final String COUNT_ROWS_FOR_TOTAL = "SELECT COUNT(*) FROM discount_counters WHERE class_name = ? AND user_id IS NULL;";
    private static final String COUNT_ROWS_FOR_USER = "SELECT COUNT(*) FROM discount_counters WHERE class_name = ? AND user_id = ?;";
    private static final String UPDATE_TOTAL_COUNTERS = "UPDATE discount_counters SET counter = counter + 1 WHERE class_name = ? AND user_id IS NULL;";
    private static final String INSERT_TOTAL_COUNTERS = "INSERT INTO discount_counters (class_name, counter) VALUES(?, 1);";
    private static final String UPDATE_USER_COUNTERS = "UPDATE discount_counters SET counter = counter + 1 WHERE class_name = ? AND user_id = ?;";
    private static final String INSERT_USER_COUNTERS = "INSERT INTO discount_counters (class_name, user_id, counter) VALUES(?, ?, 1);";
    private static final String SELECT_ALL_TOTAL = "SELECT * FROM discount_counters WHERE user_id IS NULL;";
    private static final String SELECT_ALL_FOR_USER = "SELECT * FROM discount_counters WHERE user_id = ?;";

    @Override
    public void incrementTotal(String canonicalName) {
        getJdbcTemplate().update(getSqlStatementForTotal(canonicalName), canonicalName);
    }

    @Override
    public void incrementForUser(String canonicalName, Long user_id) {
        getJdbcTemplate().update(getSqlStatementForUser(canonicalName, user_id), canonicalName, user_id);
    }

    @Override
    public Map<String, Long> getTotals() {
        return getJdbcTemplate().query(SELECT_ALL_TOTAL, getMapResultSetExtractor());
    }

    @Override
    public Map<String, Long> getTotalForUser(Long user_id) {
        return getJdbcTemplate().query(SELECT_ALL_FOR_USER, new Object[]{user_id}, getMapResultSetExtractor());
    }

    private String getSqlStatementForTotal(String canonicalName) {
        Integer count = getJdbcTemplate().queryForObject(COUNT_ROWS_FOR_TOTAL, new String[]{canonicalName}, Integer.class);
        return count > 0 ? UPDATE_TOTAL_COUNTERS : INSERT_TOTAL_COUNTERS;
    }

    private static ResultSetExtractor<Map<String, Long>> getMapResultSetExtractor() {
        return rs -> {
            Map<String, Long> result = new LinkedHashMap<>();
            while (rs.next()) {
                String class_name = rs.getString("class_name");
                long counter = rs.getLong("counter");
                result.put(class_name, counter);
            }
            return result;
        };
    }

    private String getSqlStatementForUser(String canonicalName, Long user_id) {
        Integer count = getJdbcTemplate().queryForObject(COUNT_ROWS_FOR_USER, new Object[]{canonicalName, user_id}, Integer.class);
        return count > 0 ? UPDATE_USER_COUNTERS : INSERT_USER_COUNTERS;
    }
}
