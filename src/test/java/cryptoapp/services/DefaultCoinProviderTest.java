package cryptoapp.services;

import org.cryptoapp.services.CoinProvider;
import org.cryptoapp.services.DefaultCoinProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cryptoapp.core.BaseCoin;
import org.cryptoapp.coins.Bitcoin;
import org.cryptoapp.coins.Ethereum;
import org.cryptoapp.coins.Solana;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class DefaultCoinProviderTest {
    private CoinProvider provider;

    @BeforeEach
    void setUp() {
        provider = new DefaultCoinProvider();
    }

    @ParameterizedTest
    @MethodSource("sourceCoinCreateCoin")
    void testCreateCoin(String coinName, double quantity, double expectedPrice, Class<? extends BaseCoin> expectedClass) {
        BaseCoin coin = provider.createCoin(coinName, quantity);
        assertEquals(coinName, coin.getName());
        assertEquals(quantity, coin.getQuantity());
        assertEquals(expectedPrice, coin.getCurrentPrice());
        assertInstanceOf(expectedClass, coin);
    }

    @Test
    void testInvalidCoinType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            provider.createCoin("Unknown", 1.0);
        });

        String expectedMessage = "Unknown coin type: unknown";
        String actualMessage = exception.getMessage().toLowerCase();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testRandomCoin() {
        // Since this is random, we just make sure it creates a valid coin
        BaseCoin coin = provider.createCoin("random", 1.0);
        assertNotNull(coin);
        assertTrue(coin instanceof Bitcoin || coin instanceof Ethereum || coin instanceof Solana);
    }

    private static Stream<Arguments> sourceCoinCreateCoin() {
        return Stream.of(
                Arguments.of("Bitcoin", 1.0, 50000.0, Bitcoin.class),
                Arguments.of("Ethereum", 5.0, 3000.0, Ethereum.class),
                Arguments.of("Solana", 20.0, 100.0, Solana.class)
        );
    }

}