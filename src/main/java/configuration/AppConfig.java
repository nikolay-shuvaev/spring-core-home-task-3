package configuration;

import entities.Rating;
import entities.SeatType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import services.strategies.BirthdayDiscountStrategy;
import services.strategies.DiscountStrategy;
import services.strategies.SoldTicketDiscountStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolai_Shuvaev on 1/9/2017
 */
@Configuration
@ComponentScan({"dao", "services"})
@PropertySource(value = "classpath:discount.properties")
@EnableAspectJAutoProxy
@Import({AuditoriumConfig.class, DatabaseConfig.class})
public class AppConfig {
    @Value("${birthday.discount.value}")
    private Integer birthdayDiscountValue;

    @Value("${birthday.discount.days}")
    private Integer birthdayDiscountDays;

    @Value("${sold.ticket.discount.value}")
    private Integer soldTicketDiscountValue;

    @Value("${sold.ticket.discount.ticket.number}")
    private Integer soldTicketDiscountTicketNumber;

    @Bean
    public DiscountStrategy birthdayDiscountStrategy() {
        return new BirthdayDiscountStrategy(birthdayDiscountValue, birthdayDiscountDays);
    }

    @Bean
    public DiscountStrategy soldTicketDiscountStrategy() {
        return new SoldTicketDiscountStrategy(soldTicketDiscountValue, soldTicketDiscountTicketNumber);
    }

    @Bean
    public List<DiscountStrategy> discountStrategies() {
        return Arrays.asList(birthdayDiscountStrategy(), soldTicketDiscountStrategy());
    }

    @Bean
    public Map<Rating, Double> multiplierByRating() {
        Map<Rating, Double> bean = new HashMap<>();
        bean.put(Rating.HIGH, 1.5);
        bean.put(Rating.MID, 1.0);
        bean.put(Rating.LOW, .5);
        return bean;
    }

    @Bean
    public Map<SeatType, Double> multiplierBySeatType() {
        Map<SeatType, Double> bean = new HashMap<>();
        bean.put(SeatType.STANDARD, 1.);
        bean.put(SeatType.VIP, 2.);
        return bean;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
