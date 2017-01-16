package dao.impl;

import dao.OccupiedSeatsDao;
import entities.Auditorium;
import entities.Seat;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by NICK on 05.01.2017.
 */
@Repository
public class OccupiedSeatsDaoImpl implements OccupiedSeatsDao {
    private Map<Auditorium, Set<Seat>> occupiedSeats = new HashMap<>();
    @Override
    public Set<Seat> getOccupiedSeats(Auditorium auditorium) {
        return occupiedSeats.get(auditorium);
    }
}
