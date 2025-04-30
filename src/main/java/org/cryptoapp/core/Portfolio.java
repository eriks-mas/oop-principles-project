package org.cryptoapp.core;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio implements Asset, PriceChangeListener {
    private final String name;
    private final List<Asset> assets = new ArrayList<>();
    private final Map<String, Double> initialValues = new HashMap<>();
    private final Map<String, Double> latestPrices = new HashMap<>();

    public Portfolio(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getCurrentPrice() {
        return 0;
    }

    @Override
    public double getTotalValue() {
        double total = 0;
        for (Asset asset : assets) {
            total += asset.getTotalValue();
        }
        return total;
    }

    @Override
    public void updatePrice(double newPrice) {
        // No update price for Portfolio
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
        if (asset instanceof BaseCoin) {
            ((BaseCoin) asset).addPriceListener(this);
            initialValues.put(asset.getName(), asset.getTotalValue());
            latestPrices.put(asset.getName(), asset.getCurrentPrice());
        }
    }

    public void removeAsset(Asset asset) {
        assets.remove(asset);
        if (asset instanceof BaseCoin) {
            ((BaseCoin) asset).removePriceListener(this);
        }
    }

    public List<Asset> getAssets() {
        return new ArrayList<>(assets);
    }

    @Override
    public void onPriceChanged(BaseCoin coin, double oldPrice, double newPrice) {
        latestPrices.put(coin.getName(), newPrice);
        System.out.printf("Price Alert: %s price changed from $%.2f to $%.2f (%.2f%%)%n",
                coin.getName(), oldPrice, newPrice, ((newPrice / oldPrice) - 1) * 100);
    }

    public double getProfitLoss() {
        double initialTotal = 0;
        for (Map.Entry<String, Double> entry : initialValues.entrySet()) {
            initialTotal += entry.getValue();
        }
        return getTotalValue() - initialTotal;
    }

    public double getProfitLossPercentage() {
        double initialTotal = 0;
        for (Map.Entry<String, Double> entry : initialValues.entrySet()) {
            initialTotal += entry.getValue();
        }
        if (initialTotal == 0) return 0;
        return (getTotalValue() / initialTotal - 1) * 100;
    }
}