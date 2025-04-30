package cryptoapp.core;

import org.cryptoapp.core.BaseCoin;
import org.cryptoapp.core.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cryptoapp.coins.Bitcoin;
import org.cryptoapp.coins.Ethereum;

class PortfolioTest {
    private Portfolio portfolio;
    private BaseCoin bitcoin;
    private BaseCoin ethereum;

    @BeforeEach
    void setUp() {
        portfolio = new Portfolio("Test Portfolio");
        bitcoin = new Bitcoin(1.0);
        ethereum = new Ethereum(10.0);
    }

    @Test
    void testEmptyPortfolio() {
        assertEquals("Test Portfolio", portfolio.getName());
        assertEquals(0.0, portfolio.getTotalValue());
    }

    @Test
    void testAddAsset() {
        portfolio.addAsset(bitcoin);
        portfolio.addAsset(ethereum);

        assertEquals(2, portfolio.getAssets().size());
        assertEquals(80000.0, portfolio.getTotalValue());
    }

    @Test
    void testRemoveAsset() {
        portfolio.addAsset(bitcoin);
        portfolio.addAsset(ethereum);
        portfolio.removeAsset(bitcoin);

        assertEquals(1, portfolio.getAssets().size());
        assertEquals(30000.0, portfolio.getTotalValue());
    }

    @Test
    void testPriceChanges() {
        portfolio.addAsset(bitcoin);
        bitcoin.updatePrice(55000.0);

        assertEquals(55000.0, portfolio.getTotalValue());
    }

    @Test
    void testProfitLossCalculation() {
        portfolio.addAsset(bitcoin);
        portfolio.addAsset(ethereum);

        bitcoin.updatePrice(60000.0);
        ethereum.updatePrice(2500.0);

        assertEquals(5000.0, portfolio.getProfitLoss());
        assertEquals(6.25, portfolio.getProfitLossPercentage());
    }
}