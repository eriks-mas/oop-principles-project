package org.cryptoapp.trading;

import org.cryptoapp.core.BaseCoin;

import java.util.HashMap;
import java.util.Map;

public class DipBuyingPolicy implements TradingPolicy {
    private final double dipThreshold;
    private final Map<String, Double> lastPrices = new HashMap<>();

    public DipBuyingPolicy(double dipThreshold) {
        this.dipThreshold = dipThreshold;
    }

    @Override
    public boolean shouldBuy(BaseCoin coin, double cashBalance) {
        if (cashBalance <= 0) return false;

        Double lastPrice = lastPrices.get(coin.getName());
        if (lastPrice == null) {
            lastPrices.put(coin.getName(), coin.getCurrentPrice());
            return false;
        }

        double priceChange = (coin.getCurrentPrice() - lastPrice) / lastPrice;
        lastPrices.put(coin.getName(), coin.getCurrentPrice());

        return priceChange <= -dipThreshold; // Buy if price drops by threshold or more
    }

    @Override
    public double amountToBuy(BaseCoin coin, double cashBalance) {
        return Math.min(1000.0, cashBalance * 0.25); // Use 25% of available cash or $1000, whichever is less
    }

    @Override
    public String getName() {
        return "Dip Buying Strategy (" + (dipThreshold * 100) + "% threshold)";
    }
}