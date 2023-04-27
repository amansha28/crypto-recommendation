package com.xm.cryptos.demo.model;

import java.math.BigDecimal;

public class CryptoData {

    private long timestamp;
    private String symbol;
    private BigDecimal price;


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CryptoData{" +
                "timestamp=" + timestamp +
                ", symbol='" + symbol + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
