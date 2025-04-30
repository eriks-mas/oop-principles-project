package org.cryptoapp.core;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCoin implements Asset {
    private final String name;
    private double price;
    private double quantity;
    private final List<PriceChangeListener> listeners = new ArrayList<>();

    protected BaseCoin(String name, double initialPrice, double initialQuantity) {
        this.name = name;
        this.price = initialPrice;
        this.quantity = initialQuantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getCurrentPrice() {
        return price;
    }

    @Override
    public double getTotalValue() {
        return price * quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void buy(double additionalQuantity) {
        this.quantity += additionalQuantity;
    }

    @Override
    public void updatePrice(double newPrice) {
        double oldPrice = this.price;
        this.price = newPrice;
        notifyPriceListeners(oldPrice, newPrice);
    }

    public void addPriceListener(PriceChangeListener listener) {
        listeners.add(listener);
    }

    public void removePriceListener(PriceChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyPriceListeners(double oldPrice, double newPrice) {
        for (PriceChangeListener listener : listeners) {
            listener.onPriceChanged(this, oldPrice, newPrice);
        }
    }
}