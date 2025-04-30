package org.cryptoapp.services;

import org.cryptoapp.core.BaseCoin;
import org.cryptoapp.coins.Bitcoin;
import org.cryptoapp.coins.Ethereum;
import org.cryptoapp.coins.Solana;

import java.util.Random;

public class DefaultCoinProvider implements CoinProvider {
    private final Random random = new Random();

    @Override
    public BaseCoin createCoin(String type, double initialQuantity) {
        if (type == null || type.equalsIgnoreCase("random")) {
            String[] types = {"Bitcoin", "Ethereum", "Solana"};
            type = types[random.nextInt(types.length)];
        }

        switch (type.toLowerCase()) {
            case "bitcoin":
                return new Bitcoin(initialQuantity);
            case "ethereum":
                return new Ethereum(initialQuantity);
            case "solana":
                return new Solana(initialQuantity);
            default:
                throw new IllegalArgumentException("Unknown coin type: " + type);
        }
    }
}