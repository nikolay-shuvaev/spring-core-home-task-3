package aspects;

import dao.DiscountCounterDao;
import entities.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NICK on 12.01.2017
 */
@Aspect
@Component
public class DiscountAspect {
    private DiscountCounterDao discountCounterDao;

    @Pointcut("execution(* services.strategies.DiscountStrategy.*(..))")
    public void discountStrategies() {

    }

    @Pointcut("execution(* *+.getDiscount(..))")
    public void applyDiscount() {

    }

    @AfterReturning(pointcut = "discountStrategies() && applyDiscount()",
            returning = "discount")
    public void countTotalAppliedStrategies(JoinPoint joinPoint, Integer discount) {
        if (discount > 0) {
            Class clazz = joinPoint.getTarget().getClass();
            discountCounterDao.incrementTotal(clazz.getCanonicalName());
        }
    }

    @AfterReturning(pointcut = "discountStrategies() && applyDiscount() && args(user, ..)",
            returning = "discount")
    public void countStrategiesAppliedToUser(JoinPoint joinPoint, User user, Integer discount) {
        if (discount > 0) {
            Class clazz = joinPoint.getTarget().getClass();
            if (user != null) {
                discountCounterDao.incrementForUser(clazz.getCanonicalName(), user.getId());
            }
        }
    }

    public Map<String, Long> getTotalDiscountApplyCounter() {
        return discountCounterDao.getTotals();
    }

    public Map<String, Long> getParticularUserCount(User testUser) {
        return discountCounterDao.getTotalForUser(testUser.getId());
    }

    @Autowired
    public void setDiscountCounterDao(DiscountCounterDao discountCounterDao) {
        this.discountCounterDao = discountCounterDao;
    }
}

