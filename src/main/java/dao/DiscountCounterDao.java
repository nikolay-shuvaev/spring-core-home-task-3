package dao;

import java.util.Map;

/**
 * Created by Nikolai_Shuvaev on 1/19/2017
 */
public interface DiscountCounterDao {
    void incrementTotal(String canonicalName);

    void incrementForUser(String canonicalName, Long user_id);

    Map<String,Long> getTotals();

    Map<String,Long> getTotalForUser(Long id);
}
