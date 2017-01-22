package services.strategies;

import entities.Event;
import entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by macbook on 02.01.17
 */
public class BirthdayDiscountStrategy extends DiscountStrategyBase {
    private int discountDays;

    public BirthdayDiscountStrategy(int discountValue, int discountDays) {
        super(discountValue);
        this.discountDays = discountDays;
    }

    public boolean isApplyDiscount(User user, Event event, LocalDateTime dateTime, int numberOfTickets) {
        if (user != null) {
            LocalDateTime till = dateTime.plusDays(discountDays);
            LocalDate birthday = user.getBirthday();
            LocalDateTime currentDateOfBirth = LocalDateTime.of(dateTime.getYear(), birthday.getMonth(), birthday.getDayOfMonth(), 0, 0);

            return (dateTime.equals(currentDateOfBirth) || dateTime.isBefore(currentDateOfBirth))
                    && (till.equals(currentDateOfBirth) || till.isAfter(currentDateOfBirth));
        }
        return false;

    }

    @Override
    public int calculateDiscount(User user, Event event, LocalDateTime dateTime, int numberOfTickets) {
        return getDiscountValue();
    }

}
