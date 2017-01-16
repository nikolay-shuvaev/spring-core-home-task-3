package aspects;

import entities.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.LotteryService;

/**
 * Created by NICK on 12.01.2017.
 */
@Aspect
@Component
public class LuckyWinnerAspect {
    private LotteryService lotteryService;

    @Pointcut("within(services.BookingService+)")
    public void bookingService() {

    }

    @Pointcut("execution(* *.getTotalPrice(..))")
    public void getTotalPrice() {

    }

    @Around("bookingService() && getTotalPrice() && args(.. , user, *)")
    public double performLuckyCheck(ProceedingJoinPoint joinPoint, User user) throws Throwable {
        if (user != null) {
            if (lotteryService.isYouLucky()) {
                System.out.println("Lucky user is " + user);
                return 0;
            }
        }
        return (double) joinPoint.proceed();
    }

    @Autowired
    public void setLotteryService(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }
}
