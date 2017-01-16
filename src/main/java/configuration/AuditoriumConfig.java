package configuration;

import entities.Auditorium;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by NICK on 10.01.2017
 */
@Configuration
@PropertySource(value = {"classpath:auditoriums/auditoriums-center.properties", "classpath:auditoriums/auditoriums-east.properties"})
public class AuditoriumConfig {

    @Bean
    public List<Auditorium> auditoriumList(Environment env) {
        return Arrays.asList(
                getAuditorium(env,
                        "center.auditorium.1.name",
                        "center.auditorium.1.numberOfSeats",
                        "center.auditorium.1.vipSeats"),
                getAuditorium(env,"center.auditorium.2.name",
                        "center.auditorium.2.numberOfSeats",
                        "center.auditorium.2.vipSeats"),
                getAuditorium(env,"center.auditorium.3.name",
                        "center.auditorium.3.numberOfSeats",
                        "center.auditorium.3.vipSeats"),
                getAuditorium(env,"center.auditorium.4.name",
                        "center.auditorium.4.numberOfSeats",
                        "center.auditorium.4.vipSeats"),
                getAuditorium(env,"center.auditorium.5.name",
                        "center.auditorium.5.numberOfSeats",
                        "center.auditorium.5.vipSeats"),
                getAuditorium(env,"east.auditorium.1.name",
                        "east.auditorium.1.numberOfSeats",
                        "east.auditorium.1.vipSeats"),
                getAuditorium(env,"east.auditorium.2.name",
                        "east.auditorium.2.numberOfSeats",
                        "east.auditorium.2.vipSeats")
        );

    }

    private Auditorium getAuditorium(Environment env, String name, String numberOfSeats, String vipSeats) {
        String auditoriumName = env.getProperty(name);
        Integer auditoriumNumberOfSeats = env.getProperty(numberOfSeats, Integer.class);
        Set auditoriumVipSeats = env.getProperty(vipSeats, Set.class);

        return new Auditorium(auditoriumName, auditoriumNumberOfSeats, convertToInt(auditoriumVipSeats));
    }

    private Set<Integer> convertToInt(Set vipSeats) {
        Set<Integer> result = new HashSet<>();
        for (Object vipSeat : vipSeats) {
            Integer seat = vipSeat instanceof String ? Integer.valueOf((String)vipSeat) : null;
            if (seat != null) {
                result.add(seat);
            }
        }
        return result;
    }
}
