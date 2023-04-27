package com.xm.cryptos.demo.model;

import java.math.BigDecimal;
import java.util.Map;

public class CryptoNormRange {

    private Map<String, BigDecimal> cryptoNormalizedRange;

    public Map<String, BigDecimal> getCryptoNormalizedRange() {
        return cryptoNormalizedRange;
    }

    public void setCryptoNormalizedRange(Map<String, BigDecimal> cryptoNormalizedRange) {
        this.cryptoNormalizedRange = cryptoNormalizedRange;
    }
}
