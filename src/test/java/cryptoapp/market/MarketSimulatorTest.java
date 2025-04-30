package cryptoapp.market;

import org.cryptoapp.market.MarketSimulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cryptoapp.core.Portfolio;
import org.cryptoapp.core.BaseCoin;
import org.cryptoapp.coins.Bitcoin;
import org.cryptoapp.trading.TradingPolicy;

class MarketSimulatorTest {
    private MarketSimulator simulator;
    private BaseCoin bitcoin;

    @BeforeEach
    void setUp() {
        Portfolio portfolio = new Portfolio("Test Portfolio");
        bitcoin = new Bitcoin(1.0); // 1 BTC at $50,000
        portfolio.addAsset(bitcoin);

        simulator = new MarketSimulator(portfolio, 10000.0);
    }

    @Test
    void testInitialValues() {
        assertEquals(10000.0, simulator.getCashBalance());
    }

    @Test
    void testSimulateDay() {
        simulator.setTradingPolicy(new AlwaysBuyPolicy());
        simulator.simulateDay();

        assertTrue(simulator.getCashBalance() < 10000.0);
        assertTrue(bitcoin.getQuantity() > 1.0);
    }

    @Test
    void testSetTradingPolicy() {
        TradingPolicy policy = new AlwaysBuyPolicy();
        simulator.setTradingPolicy(policy);
        assertEquals(policy, simulator.getTradingPolicy());
    }

    // Helper class for testing
    private static class AlwaysBuyPolicy implements TradingPolicy {
        @Override
        public boolean shouldBuy(BaseCoin coin, double cashBalance) {
            return cashBalance > 0;
        }

        @Override
        public double amountToBuy(BaseCoin coin, double cashBalance) {
            return 1000.0;
        }

        @Override
        public String getName() {
            return "Always Buy Policy";
        }
    }
}