package dao;

import entities.Auditorium;
import entities.Seat;

import java.util.Set;

/**
 * Created by NICK on 05.01.2017.
 */
public interface OccupiedSeatsDao {
    Set<Seat> getOccupiedSeats(Auditorium auditorium);
}
