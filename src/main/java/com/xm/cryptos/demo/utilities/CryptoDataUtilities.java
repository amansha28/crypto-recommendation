package com.xm.cryptos.demo.utilities;

import java.math.BigDecimal;

/*
 * This class contains the various utility price attributes (like oldest,newest,min and max) for corresponding to each Cryptocurrency.
 * */
public class CryptoDataUtilities {

    private BigDecimal oldest;
    private BigDecimal newest;
    private BigDecimal min;
    private BigDecimal max;

    public BigDecimal getOldest() {
        return oldest;
    }

    public void setOldest(BigDecimal oldest) {
        this.oldest = oldest;
    }

    public BigDecimal getNewest() {
        return newest;
    }

    public void setNewest(BigDecimal newest) {
        this.newest = newest;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }
}
