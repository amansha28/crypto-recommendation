package com.xm.cryptos.demo.dao;

import com.xm.cryptos.demo.utilities.CryptoDataUtilities;

import java.math.BigDecimal;
import java.util.Map;

public interface CryptoDataRepo {

    public CryptoDataUtilities getUtilityDataForSpecificCryptoDao(String cryptoSymbol);

    public Map<String, BigDecimal> getNormalizedRangeForCrypto();

    public Map<String, BigDecimal> getCryptoWithHighestNormalizedRangeByDate(long startTimestamp, long endTimeStamp);

}
