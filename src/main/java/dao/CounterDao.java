package dao;

import entities.StatisticEntry;

import java.util.Map;

/**
 * Created by NICK on 18.01.2017
 */
public interface CounterDao {
    void incrementAccessedByName(long id);

    void incrementPriceQueried(long id);

    void incrementTicketBooking(long id);

    Map<Long, StatisticEntry> getAllByEventId();
}
