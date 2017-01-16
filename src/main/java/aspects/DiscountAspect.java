package aspects;

import entities.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NICK on 12.01.2017
 */
@Aspect
@Component
public class DiscountAspect {
    private Map<Class, Long> totalDiscountApplyCounter = new HashMap<>();
    private Map<Class, Map<User, Long>> particularUserCount = new HashMap<>();


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
            increaseCount(clazz, totalDiscountApplyCounter);
        }
    }

    @AfterReturning(pointcut = "discountStrategies() && applyDiscount() && args(user, ..)",
            returning = "discount")
    public void countStrategiesAppliedToUser(JoinPoint joinPoint, User user, Integer discount) {
        if (discount > 0) {
            Class clazz = joinPoint.getTarget().getClass();
            if (user != null) {
                updateUserCountInformation(clazz, user);
            }
        }
    }

    private void updateUserCountInformation(Class clazz, User user) {
        Map<User, Long> userCount = particularUserCount.get(clazz);
        if (userCount == null) {
            userCount = new HashMap<>();
            particularUserCount.put(clazz, userCount);
        }
        increaseCount(user, userCount);
    }

    public Map<Class, Long> getTotalDiscountApplyCounter() {
        return totalDiscountApplyCounter;
    }

    public Map<Class, Map<User, Long>> getParticularUserCount() {
        return particularUserCount;
    }

    private static <T> void increaseCount(T t, Map<T, Long> map) {
        Long count = map.get(t);
        count = (count != null) ? count + 1 : 1L;
        map.put(t, count);
    }
}

