package org.cryptoapp.core;

public interface PriceChangeListener {
    void onPriceChanged(BaseCoin coin, double oldPrice, double newPrice);
}