package com.xm.cryptos.demo.service;

import com.xm.cryptos.demo.utilities.CryptoDataUtilities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface CryptoRecommendationService {

    public CryptoDataUtilities getUtilityDataForSpecificCryptoService(String cryptoSymbol);

    public Map<String, BigDecimal> cryptoWithHighestNormalizedRangeOnDateService(Date date);

    public Map<String, BigDecimal> cryptosNormalizedRangeService();

}
