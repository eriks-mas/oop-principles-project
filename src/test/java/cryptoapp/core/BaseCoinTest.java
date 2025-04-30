package cryptoapp.core;

import org.cryptoapp.core.BaseCoin;
import org.cryptoapp.core.PriceChangeListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cryptoapp.coins.Bitcoin;

class BaseCoinTest {
    private BaseCoin coin;
    private TestPriceListener listener;

    @BeforeEach
    void setUp() {
        coin = new Bitcoin(1.0); // 1 BTC with default price of $50,000
        listener = new TestPriceListener();
        coin.addPriceListener(listener);
    }

    @Test
    void testInitialValues() {
        assertEquals("Bitcoin", coin.getName());
        assertEquals(50000.0, coin.getCurrentPrice());
        assertEquals(1.0, coin.getQuantity());
        assertEquals(50000.0, coin.getTotalValue());
    }

    @Test
    void testBuy() {
        coin.buy(0.5);
        assertEquals(1.5, coin.getQuantity());
        assertEquals(75000.0, coin.getTotalValue());
    }

    @Test
    void testUpdatePrice() {
        coin.updatePrice(55000.0);
        assertEquals(55000.0, coin.getCurrentPrice());
        assertEquals(55000.0, coin.getTotalValue());
        assertTrue(listener.notificationReceived);
        assertEquals(coin, listener.updatedCoin);
        assertEquals(50000.0, listener.oldPrice);
        assertEquals(55000.0, listener.newPrice);
    }

    @Test
    void testRemoveListener() {
        coin.removePriceListener(listener);
        coin.updatePrice(55000.0);
        assertFalse(listener.notificationReceived);
    }

    private static class TestPriceListener implements PriceChangeListener {
        boolean notificationReceived = false;
        BaseCoin updatedCoin = null;
        double oldPrice = 0;
        double newPrice = 0;

        @Override
        public void onPriceChanged(BaseCoin coin, double oldPrice, double newPrice) {
            this.notificationReceived = true;
            this.updatedCoin = coin;
            this.oldPrice = oldPrice;
            this.newPrice = newPrice;
        }
    }
}