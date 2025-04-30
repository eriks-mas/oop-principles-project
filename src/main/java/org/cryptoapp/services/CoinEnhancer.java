package org.cryptoapp.services;

import org.cryptoapp.core.Asset;

public abstract class CoinEnhancer implements Asset {
    protected Asset wrappedAsset;

    protected CoinEnhancer(Asset wrappedAsset) {
        this.wrappedAsset = wrappedAsset;
    }

    @Override
    public String getName() {
        return wrappedAsset.getName();
    }

    @Override
    public double getCurrentPrice() {
        return wrappedAsset.getCurrentPrice();
    }

    @Override
    public double getTotalValue() {
        return wrappedAsset.getTotalValue();
    }

    @Override
    public void updatePrice(double newPrice) {
        wrappedAsset.updatePrice(newPrice);
    }
}