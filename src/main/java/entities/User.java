package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 02.01.17.
 */
public class User {
    private final Long id;
    private final String name;
    private final LocalDate birthday;
    private final String email;
    private List<Ticket> tickets = new ArrayList<>();

    public User(Long id, String name, LocalDate birthday, String email) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", tickets=" + tickets +
                '}';
    }
}
