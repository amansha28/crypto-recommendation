package com.xm.cryptos.demo.service.impl;

import com.xm.cryptos.demo.constants.CryptoConstants;
import com.xm.cryptos.demo.dao.CryptoDataRepo;
import com.xm.cryptos.demo.service.CryptoRecommendationService;
import com.xm.cryptos.demo.utilities.CryptoDataUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Service
public class CryptoRecommendationServiceImpl implements CryptoRecommendationService {

    @Autowired
    private CryptoDataRepo cryptoDataRepo;

    public CryptoDataUtilities getUtilityDataForSpecificCryptoService(String cryptoSymbol) {
        return cryptoDataRepo.getUtilityDataForSpecificCryptoDao(cryptoSymbol);
    }

    public Map<String, BigDecimal> cryptosNormalizedRangeService() {
        return cryptoDataRepo.getNormalizedRangeForCrypto();
    }

    public Map<String, BigDecimal> cryptoWithHighestNormalizedRangeOnDateService(Date date) {

        //calculate start and end timestamps in EPOCH format to crypto prices data
        long startTimestamp = date.toInstant().toEpochMilli();
        long endTimestamp = date.toInstant().toEpochMilli() + CryptoConstants.MILLIS_IN_DAY;
        System.out.println("start time : " + startTimestamp); // remove this line
        System.out.println("end time : " + endTimestamp); // remove this line

        return cryptoDataRepo.getCryptoWithHighestNormalizedRangeByDate(startTimestamp, endTimestamp);
    }
}
