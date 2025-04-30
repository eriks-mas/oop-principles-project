package org.cryptoapp.services;

import org.cryptoapp.core.Asset;

public class NFTBonusEnhancer extends CoinEnhancer {
    private final double bonusPercentage;

    public NFTBonusEnhancer(Asset wrappedAsset, double bonusPercentage) {
        super(wrappedAsset);
        this.bonusPercentage = bonusPercentage;
    }

    @Override
    public String getName() {
        return wrappedAsset.getName() + " (NFT Bonus)";
    }

    @Override
    public double getTotalValue() {
        return wrappedAsset.getTotalValue() * (1 + bonusPercentage);
    }
}