package services.impl;

import org.springframework.stereotype.Service;
import services.LotteryService;

import java.util.Random;

/**
 * Created by NICK on 12.01.2017.
 */
@Service
public class LotteryServiceImpl implements LotteryService {
    @Override
    public boolean isYouLucky() {
        return new Random().nextInt(10) == 5;
    }
}
