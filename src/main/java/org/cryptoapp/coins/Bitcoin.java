package org.cryptoapp.coins;

import org.cryptoapp.core.BaseCoin;

public class Bitcoin extends BaseCoin {
    public Bitcoin(double initialQuantity) {
        super("Bitcoin", 50000.0, initialQuantity);
    }
}