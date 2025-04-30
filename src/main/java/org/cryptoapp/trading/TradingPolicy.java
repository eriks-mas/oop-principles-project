package org.cryptoapp.trading;

import org.cryptoapp.core.BaseCoin;

public interface TradingPolicy {
    boolean shouldBuy(BaseCoin coin, double cashBalance);
    double amountToBuy(BaseCoin coin, double cashBalance);
    String getName();
}