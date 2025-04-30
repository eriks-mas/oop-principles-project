package cryptoapp.trading;

import org.cryptoapp.trading.DipBuyingPolicy;
import org.cryptoapp.trading.TradingPolicy;
import org.cryptoapp.core.BaseCoin;
import org.cryptoapp.coins.Bitcoin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class DipBuyingPolicyTest {
    private TradingPolicy policy;
    private BaseCoin coin;

    @BeforeEach
    void setUp() {
        policy = new DipBuyingPolicy(0.05);
        coin = new Bitcoin(1.0);
    }

    @ParameterizedTest
    @MethodSource("sourceShouldBuyData")
    void testShouldBuy(boolean firstCall, double initialPrice, double updatedPrice, double cashBalance, boolean expected) {
        if (firstCall) {
            boolean shouldBuy = policy.shouldBuy(coin, cashBalance);
            assertEquals(expected, shouldBuy);
        } else {
            policy.shouldBuy(coin, initialPrice);
            coin.updatePrice(updatedPrice);
            boolean shouldBuy = policy.shouldBuy(coin, cashBalance);
            assertEquals(expected, shouldBuy);
        }
    }

    @ParameterizedTest
    @MethodSource("sourceAmountToBuyTestData")
    void testAmountToBuy(double cashBalance, double expectedAmount) {
        double amount = policy.amountToBuy(coin, cashBalance);
        assertEquals(expectedAmount, amount);
    }

    private static Stream<Arguments> sourceShouldBuyData() {
        return Stream.of(
                Arguments.of(true, 10000.0, 0.0, 10000.0, false),
                Arguments.of(false, 10000.0, 55000.0, 10000.0, false),
                Arguments.of(false, 10000.0, 48000.0, 10000.0, false),
                Arguments.of(false, 10000.0, 47000.0, 10000.0, true),
                Arguments.of(false, 10000.0, 40000.0, 0.0, false)
        );
    }

    private static Stream<Arguments> sourceAmountToBuyTestData() {
        return Stream.of(
                Arguments.of(10000.0, 1000.0),
                Arguments.of(2000.0, 500.0)
        );
    }

}