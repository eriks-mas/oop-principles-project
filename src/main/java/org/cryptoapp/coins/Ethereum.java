package org.cryptoapp.coins;

import org.cryptoapp.core.BaseCoin;

public class Ethereum extends BaseCoin {
    public Ethereum(double initialQuantity) {
        super("Ethereum", 3000.0, initialQuantity);
    }
}