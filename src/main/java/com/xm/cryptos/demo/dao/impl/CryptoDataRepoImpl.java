package com.xm.cryptos.demo.dao.impl;

import com.xm.cryptos.demo.config.CsvDataLoader;
import com.xm.cryptos.demo.dao.CryptoDataRepo;
import com.xm.cryptos.demo.model.CryptoData;
import com.xm.cryptos.demo.utilities.CryptoDataUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

@Repository
public class CryptoDataRepoImpl implements CryptoDataRepo {

    @Autowired
    private CsvDataLoader csvDataLoader;

    // for given crypto Symbol, return the corresponding CryptoDataUtilities Object containing oldest, newest, min and max prices.
    public CryptoDataUtilities getUtilityDataForSpecificCryptoDao(String cryptoSymbol) {

        return csvDataLoader.getCryptoDataUtilitiesMap().get(cryptoSymbol);
    }

    /*
     * Calculate the Normalized Range for each approved Crypto using following operations :
     * Step 1. Get a copy of HashMap<CryptoSymbol, CryptoDataUtilities> from the csvDataLoader
     * Step 2. Iterate through the above-mentioned HashMap and calculate the normalized range for each crypto. Normalized Range = (max-min)/min
     * Step 3. create a new hashmap of <CryptoSymbol, NormalizedRange> and store the corresponding values calculated in step 2.
     * Step 4. Sort the New HashMap<CryptoSymbol, NormalizedRange> according to NormalizedRange in the descending order. Return the sorted HashMap.
     * */
    public Map<String, BigDecimal> getNormalizedRangeForCrypto() {

        // Step 1
        Map<String, CryptoDataUtilities> map = csvDataLoader.getCryptoDataUtilitiesMap();
        Map<String, BigDecimal> cryptoNormalizedRangeMap = new HashMap<>();
        MathContext mc = new MathContext(4);

        // Step 2 and 3
        for (Map.Entry<String, CryptoDataUtilities> entry : map.entrySet()) {
            String cryptoSymbol = entry.getKey();
            BigDecimal difference = entry.getValue().getMax().subtract(entry.getValue().getMin());
            BigDecimal normalizedRange = difference.divide(entry.getValue().getMin(), mc);
            cryptoNormalizedRangeMap.put(cryptoSymbol, normalizedRange);
        }

        // Step 4
        LinkedHashMap<String, BigDecimal> sortedCryptoNormalizedRangeMap = new LinkedHashMap<>();
        List<BigDecimal> list = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : cryptoNormalizedRangeMap.entrySet())
            list.add(entry.getValue());

        Collections.sort(list, Collections.reverseOrder());
        for (BigDecimal num : list) {
            for (Map.Entry<String, BigDecimal> entry : cryptoNormalizedRangeMap.entrySet()) {
                if (entry.getValue().equals(num))
                    sortedCryptoNormalizedRangeMap.put(entry.getKey(), num);
            }
        }

        System.out.println(sortedCryptoNormalizedRangeMap); // remove this line
        return sortedCryptoNormalizedRangeMap;
    }

    /*
     * Find the crypto which has the highest Normalized Range for a given date, based on following operations :
     * Step 1. Get a copy of HashMap containing CryptoSymbol and corresponding price data from the csvDataLoader . Iterate through it.
     * Step 2. For each crypto Symbol, calculate the min and max prices between given time stamp range.
     * Step 3. Calculate the Normalized range of each crypto for the timestamp range.
     *         Simultaneously, calculate the crypto with max Normalized range for the date.
     * Step 4. Create a new HashMap<CryptoSymbol, NormalizedRangeOnDate> and store the Crypto with Highest Normalized range values for given date into it.
     *
     * */
    public Map<String, BigDecimal> getCryptoWithHighestNormalizedRangeByDate(long startTimestamp, long endTimeStamp) {
        //Step 1
        Map<String, List<CryptoData>> cryptoDataMap = csvDataLoader.getCryptoDataMap();
        MathContext mc = new MathContext(4);
        Map<String, CryptoDataUtilities> cryptoDataUtilitiesMap = new HashMap<>();
        BigDecimal maxNormalizedRange = new BigDecimal(0.0);
        String cryptoSymbolWithMaxNormRange = null;

        //Step 2
        for (Map.Entry<String, List<CryptoData>> entry : cryptoDataMap.entrySet()) {
            String cryptoSymbol = entry.getKey();
            List<CryptoData> cryptoDataList = entry.getValue();
            BigDecimal maxPrice = BigDecimal.valueOf(0.0);
            BigDecimal minPrice = BigDecimal.valueOf(0.0);

            Boolean isInitialMinPrice = true;
            for (CryptoData cryptoData : cryptoDataList) {
                if ((cryptoData.getTimestamp() >= startTimestamp) && (cryptoData.getTimestamp() < endTimeStamp)) {
                    if (isInitialMinPrice) {
                        minPrice = cryptoData.getPrice();
                        isInitialMinPrice = false;
                    } else {
                        if (cryptoData.getPrice().compareTo(minPrice) == -1)
                            minPrice = cryptoData.getPrice();
                    }

                    if (cryptoData.getPrice().compareTo(maxPrice) == 1)
                        maxPrice = cryptoData.getPrice();
                }

                //Step 3
                if (!minPrice.equals(BigDecimal.valueOf(0.0)) && !maxPrice.equals(BigDecimal.valueOf(0.0))) {
                    System.out.println("CryptoSymbol : " + cryptoSymbol + " minPrice : " + minPrice + " MaxPrice : " + maxPrice);// remove this line

                    BigDecimal difference = maxPrice.subtract(minPrice);
                    BigDecimal normalizedRange = difference.divide(minPrice, mc);
                    System.out.println("==================================");// remove this line
                    System.out.println("CryptoSymbol : " + cryptoSymbol + " Normalized Range : " + normalizedRange);// remove this line

                    if (normalizedRange.compareTo(maxNormalizedRange) == 1) {
                        maxNormalizedRange = normalizedRange;
                        cryptoSymbolWithMaxNormRange = cryptoSymbol;
                    }
                }

            }
        }

        //Step 4
        HashMap<String, BigDecimal> res = new HashMap<String, BigDecimal>();
        res.put(cryptoSymbolWithMaxNormRange, maxNormalizedRange);
        System.out.println("+++++++++++++++++++++++++"); // remove this line
        System.out.println(res); // remove this line
        return res;
    }

}
