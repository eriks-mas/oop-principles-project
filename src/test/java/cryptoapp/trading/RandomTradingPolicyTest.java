package cryptoapp.trading;

import org.cryptoapp.trading.RandomTradingPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cryptoapp.core.BaseCoin;
import org.cryptoapp.coins.Bitcoin;

class RandomTradingPolicyTest {
    private RandomTradingPolicy policy;
    private BaseCoin coin;

    @BeforeEach
    void setUp() {
        policy = new RandomTradingPolicy();
        coin = new Bitcoin(1.0);
    }

    @Test
    void testZeroCashBalance() {
        boolean shouldBuy = policy.shouldBuy(coin, 0.0);
        assertFalse(shouldBuy);
    }

    @Test
    void testAmountToBuy() {
        double amount = policy.amountToBuy(coin, 10000.0);
        assertTrue(amount >= 1000.0 && amount <= 3000.0);
    }

    @Test
    void testGetName() {
        assertEquals("Random Trading Policy", policy.getName());
    }
}