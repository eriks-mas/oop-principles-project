package org.cryptoapp.services;

import org.cryptoapp.core.Asset;

public class StakingEnhancer extends CoinEnhancer {
    private final double stakingYield; // Annual yield as decimal

    public StakingEnhancer(Asset wrappedAsset, double stakingYield) {
        super(wrappedAsset);
        this.stakingYield = stakingYield;
    }

    @Override
    public String getName() {
        return wrappedAsset.getName() + " (Staked)";
    }

    @Override
    public double getTotalValue() {
        return wrappedAsset.getTotalValue() * (1 + stakingYield / 365);
    }
}