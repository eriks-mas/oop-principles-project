package org.cryptoapp.trading;

import org.cryptoapp.core.BaseCoin;

import java.util.Random;

public class RandomTradingPolicy implements TradingPolicy {
    private final Random random = new Random();

    @Override
    public boolean shouldBuy(BaseCoin coin, double cashBalance) {
        if (cashBalance <= 0) return false;
        return random.nextDouble() < 0.3; // 30% chance of buying
    }

    @Override
    public double amountToBuy(BaseCoin coin, double cashBalance) {
        return cashBalance * (0.1 + random.nextDouble() * 0.2); // 10-30% of cash balance
    }

    @Override
    public String getName() {
        return "Random Trading Policy";
    }
}