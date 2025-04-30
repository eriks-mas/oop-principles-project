package org.cryptoapp.services;

import org.cryptoapp.core.BaseCoin;

public interface CoinProvider {
    BaseCoin createCoin(String type, double initialQuantity);
}