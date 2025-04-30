package org.cryptoapp.core;

public interface Asset {
    String getName();
    double getCurrentPrice();
    double getTotalValue();
    void updatePrice(double newPrice);
}