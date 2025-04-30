package cryptoapp.services;

import org.cryptoapp.services.NFTBonusEnhancer;
import org.cryptoapp.services.StakingEnhancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.cryptoapp.core.Asset;
import org.cryptoapp.coins.Bitcoin;

class CoinEnhancerTest {
    private Asset baseBitcoin;

    @BeforeEach
    void setUp() {
        baseBitcoin = new Bitcoin(1.0);
    }

    @Test
    void testStakingEnhancer() {
        double stakingYield = 0.05;
        Asset stakedBitcoin = new StakingEnhancer(baseBitcoin, stakingYield);

        assertEquals("Bitcoin (Staked)", stakedBitcoin.getName());
        assertEquals(50000.0, stakedBitcoin.getCurrentPrice());

        double expectedValue = 50000.0 * (1 + stakingYield / 365);
        assertEquals(expectedValue, stakedBitcoin.getTotalValue(), 0.001);
    }

    @Test
    void testNFTBonusEnhancer() {
        double bonusPercentage = 0.10;
        Asset nftBitcoin = new NFTBonusEnhancer(baseBitcoin, bonusPercentage);

        assertEquals("Bitcoin (NFT Bonus)", nftBitcoin.getName());
        assertEquals(50000.0, nftBitcoin.getCurrentPrice());

        double expectedValue = 50000.0 * (1 + bonusPercentage);
        assertEquals(expectedValue, nftBitcoin.getTotalValue(), 0.001);
    }

    @Test
    void testNestedEnhancers() {
        Asset enhancedBitcoin = new NFTBonusEnhancer(
                new StakingEnhancer(baseBitcoin, 0.05), 0.10);

        assertEquals("Bitcoin (Staked) (NFT Bonus)", enhancedBitcoin.getName());

        double stakingMultiplier = 1 + 0.05/365;
        double nftMultiplier = 1 + 0.10;
        double expectedValue = 50000.0 * stakingMultiplier * nftMultiplier;

        assertEquals(expectedValue, enhancedBitcoin.getTotalValue(), 0.001);
    }

    @Test
    void testPriceUpdate() {
        Asset stakedBitcoin = new StakingEnhancer(baseBitcoin, 0.05);
        baseBitcoin.updatePrice(55000.0);
        assertEquals(55000.0, stakedBitcoin.getCurrentPrice());
    }
}