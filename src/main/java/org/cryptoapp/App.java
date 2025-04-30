package org.cryptoapp;

import org.cryptoapp.core.Portfolio;
import org.cryptoapp.core.Asset;
import org.cryptoapp.market.MarketSimulator;
import org.cryptoapp.services.CoinProvider;
import org.cryptoapp.services.DefaultCoinProvider;
import org.cryptoapp.services.StakingEnhancer;
import org.cryptoapp.services.NFTBonusEnhancer;
import org.cryptoapp.trading.DipBuyingPolicy;
import org.cryptoapp.trading.RandomTradingPolicy;
import org.cryptoapp.trading.TradingPolicy;

public class App {
    public static void main(String[] args) {
        CoinProvider coinProvider = new DefaultCoinProvider();
        Portfolio portfolio = new Portfolio("My Crypto Portfolio");

        portfolio.addAsset(coinProvider.createCoin("Bitcoin", 0.5));
        portfolio.addAsset(coinProvider.createCoin("Ethereum", 5.0));
        portfolio.addAsset(coinProvider.createCoin("Solana", 20.0));

        Asset stakingEthereum = new StakingEnhancer(coinProvider.createCoin("Ethereum", 2.0), 0.05);
        Asset nftBitcoin = new NFTBonusEnhancer(coinProvider.createCoin("Bitcoin", 0.2), 0.10);
        portfolio.addAsset(stakingEthereum);
        portfolio.addAsset(nftBitcoin);

        TradingPolicy dipBuying = new DipBuyingPolicy(0.05); // 5% dip threshold
        TradingPolicy randomTrading = new RandomTradingPolicy();

        MarketSimulator simulator = new MarketSimulator(portfolio, 10000.0); // $10,000 initial cash
        simulator.setTradingPolicy(dipBuying);

        for (int day = 1; day <= 30; day++) {
            System.out.println("===== Day " + day + " =====");
            simulator.simulateDay();
            simulator.printDailySummary();

            // Switch trading policy every 10 days
            if (day % 10 == 0) {
                if (simulator.getTradingPolicy() instanceof DipBuyingPolicy) {
                    simulator.setTradingPolicy(randomTrading);
                    System.out.println("Switching to Random Trading Policy");
                } else {
                    simulator.setTradingPolicy(dipBuying);
                    System.out.println("Switching to Dip Buying Policy");
                }
            }
        }

        // Print final results
        System.out.println("\n===== FINAL PORTFOLIO SUMMARY =====");
        simulator.printFullSummary();
    }
}