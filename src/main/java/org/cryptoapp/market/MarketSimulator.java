package org.cryptoapp.market;

import org.cryptoapp.core.Asset;
import org.cryptoapp.core.BaseCoin;
import org.cryptoapp.core.Portfolio;
import org.cryptoapp.trading.TradingPolicy;

import java.util.List;
import java.util.Random;

public class MarketSimulator {
    private final Portfolio portfolio;
    private double cashBalance;
    private TradingPolicy tradingPolicy;
    private final Random random = new Random();

    private final double initialCashBalance;
    private final double initialPortfolioValue;
    private double dailyTradingVolume;

    public MarketSimulator(Portfolio portfolio, double initialCash) {
        this.portfolio = portfolio;
        this.cashBalance = initialCash;
        this.initialCashBalance = initialCash;
        this.initialPortfolioValue = portfolio.getTotalValue();
    }

    public void setTradingPolicy(TradingPolicy tradingPolicy) {
        this.tradingPolicy = tradingPolicy;
    }

    public TradingPolicy getTradingPolicy() {
        return tradingPolicy;
    }

    public void simulateDay() {
        dailyTradingVolume = 0;

        List<Asset> assets = portfolio.getAssets();
        for (Asset asset : assets) {
            if (asset instanceof BaseCoin) {
                BaseCoin coin = (BaseCoin) asset;

                double changePercent = (random.nextDouble() * 10 - 5) / 100;
                double newPrice = coin.getCurrentPrice() * (1 + changePercent);
                coin.updatePrice(newPrice);

                if (tradingPolicy != null && tradingPolicy.shouldBuy(coin, cashBalance)) {
                    double dollarAmount = tradingPolicy.amountToBuy(coin, cashBalance);
                    double coinAmount = dollarAmount / coin.getCurrentPrice();

                    if (dollarAmount <= cashBalance && coinAmount > 0) {
                        coin.buy(coinAmount);
                        cashBalance -= dollarAmount;
                        dailyTradingVolume += dollarAmount;

                        System.out.printf("Purchased %.4f %s for $%.2f at $%.2f per coin%n",
                                coinAmount, coin.getName(), dollarAmount, coin.getCurrentPrice());
                    }
                }
            }
        }
    }

    public void printDailySummary() {
        System.out.println("Portfolio Value: $" + String.format("%.2f", portfolio.getTotalValue()));
        System.out.println("Cash Balance: $" + String.format("%.2f", cashBalance));
        System.out.println("Daily Trading Volume: $" + String.format("%.2f", dailyTradingVolume));
        System.out.println("Active Trading Policy: " + (tradingPolicy != null ? tradingPolicy.getName() : "None"));
        System.out.println();
    }

    public void printFullSummary() {
        double totalCurrentValue = portfolio.getTotalValue() + cashBalance;
        double totalInitialValue = initialPortfolioValue + initialCashBalance;
        double totalProfitLoss = totalCurrentValue - totalInitialValue;
        double totalProfitLossPercent = (totalCurrentValue / totalInitialValue - 1) * 100;

        System.out.println("Assets:");
        List<Asset> assets = portfolio.getAssets();
        for (Asset asset : assets) {
            System.out.printf("- %s: $%.2f%n", asset.getName(), asset.getTotalValue());
        }

        System.out.println("\nPortfolio Value: $" + String.format("%.2f", portfolio.getTotalValue()));
        System.out.println("Cash Balance: $" + String.format("%.2f", cashBalance));
        System.out.println("Total Value: $" + String.format("%.2f", totalCurrentValue));
        System.out.println("\nOverall Performance:");
        System.out.printf("Initial Investment: $%.2f%n", totalInitialValue);
        System.out.printf("Total Profit/Loss: $%.2f (%.2f%%)%n",
                totalProfitLoss, totalProfitLossPercent);
    }

    public double getCashBalance() {
        return cashBalance;
    }
}