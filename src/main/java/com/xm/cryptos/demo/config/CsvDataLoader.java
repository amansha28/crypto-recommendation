package com.xm.cryptos.demo.config;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.xm.cryptos.demo.model.CryptoData;
import com.xm.cryptos.demo.utilities.CryptoDataUtilities;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
//@Service
public class CsvDataLoader {

    //ResourceLoader to load the CSV files from the classpath or file system.
    private final PathMatchingResourcePatternResolver resourceLoader;
    @Value("${cryptos.symbols.approved}")
    private List<String> approvedCrytosList;
    // HashMap to store CRYPTO_NAME and corresponding CRYPTO_NAME_prices.csv data
    private Map<String, List<CryptoData>> cryptoDataMap;

    // HashMap to store CRYPTO_NAME and corresponding utility data like oldest, newest, max and min prices.
    private Map<String, CryptoDataUtilities> cryptoDataUtilitiesMap;

    public CsvDataLoader(PathMatchingResourcePatternResolver resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Map<String, List<CryptoData>> getCryptoDataMap() {
        return cryptoDataMap;
    }

    public Map<String, CryptoDataUtilities> getCryptoDataUtilitiesMap() {
        return cryptoDataUtilitiesMap;
    }

    @PostConstruct
    public void init() throws IOException {
        System.out.println("=== List of approved Cryptos : " + approvedCrytosList);
        // read all .csv files and store data in HashMap
        cryptoDataMap = readCsvFiles();

        // calculate utilities data for each Crypto and store in a HasHMap.
        cryptoDataUtilitiesMap = calculateUtilities(cryptoDataMap);

    }

    public Map<String, List<CryptoData>> readCsvFiles() throws IOException {
        List<CryptoData> dataList = new ArrayList<>();
        Map<String, List<CryptoData>> dataMap = new HashMap<>();

        // Using ResourceLoader load all available csv files from given location, into an array
        Resource[] resources = resourceLoader.getResources("classpath*:prices/*.csv");

        /*
         * Iterate through Array and do following operations :
         * Step 1. Check if the .csv file to be read is for supported Crypto Symbol.
         * Step 2. Parse the CSV records, map to CryptoData Bean and add the mapped bean to a list.
         * Step 3. Once all records in a CSV is parsed, add the entry of CryptoSymBol,List<MappedBead> to HashMap
         * */
        for (Resource resource : resources) {
            System.out.println("Resource File Name : " + resource.getFilename()); // remove this line
            //Step 1
            String fileName = resource.getFilename();
            int indexOfUnderScoreAfterCryptoSymbl = fileName.indexOf("_", 0);

            if (approvedCrytosList.contains(fileName.substring(0, indexOfUnderScoreAfterCryptoSymbl))) {
                System.out.println("File Name === : " + fileName.substring(0, indexOfUnderScoreAfterCryptoSymbl)); // remove this line
                //Step 2
                CsvToBean<CryptoData> csvToBean = new CsvToBeanBuilder<CryptoData>(
                        new InputStreamReader(resource.getInputStream()))
                        .withType(CryptoData.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<CryptoData> fileData = csvToBean.parse();
                //Step 3
                dataList.addAll(fileData);
                dataMap.put(fileData.get(0).getSymbol(), fileData);
            }
        }
        return dataMap;
    }

    /*
     * Iterate through already provided HashMap<String, List<CryptoData>> to perform following operation for each Crypto Symbol :
     * Step 1. For each cryptoSymbol get the List of time-price data.
     * Step 2. Traverse through the list to Calculate the oldest, newest, min and max price data for each crypto.
     * Step 3. Store the oldest, newest, min and max price data for each crypto into the CryptoDataUtilities object;
     * Step 4. Add the CryptoDataUtilities Obj to HashMap<CryptoSymbol, CryptoDataUtilities> to store the values.
     * */
    public Map<String, CryptoDataUtilities> calculateUtilities(Map<String, List<CryptoData>> cryptoDataMap) {
        Map<String, CryptoDataUtilities> cryptoDataUtilitiesMap = new HashMap<>();
        // Step 1
        for (Map.Entry<String, List<CryptoData>> entry : cryptoDataMap.entrySet()) {
            String cryptoSymbol = entry.getKey();
            List<CryptoData> cryptoData = entry.getValue();
            BigDecimal oldestPrice = cryptoData.get(0).getPrice();
            BigDecimal newestPrice = cryptoData.get(cryptoData.size() - 1).getPrice();
            BigDecimal minPrice = cryptoData.get(0).getPrice();
            BigDecimal maxPrice = BigDecimal.valueOf(0.0);

            //Step 2
            for (int i = 0; i < cryptoData.size(); i++) {
                if (cryptoData.get(i).getPrice().compareTo(minPrice) == -1)
                    minPrice = cryptoData.get(i).getPrice();

                if (cryptoData.get(i).getPrice().compareTo(maxPrice) == 1)
                    maxPrice = cryptoData.get(i).getPrice();
            }
            //Step 3
            CryptoDataUtilities utilities = new CryptoDataUtilities();
            utilities.setOldest(oldestPrice);
            utilities.setNewest(newestPrice);
            utilities.setMin(minPrice);
            utilities.setMax(maxPrice);

            //Step 4
            cryptoDataUtilitiesMap.put(cryptoSymbol, utilities);
            System.out.println(cryptoDataUtilitiesMap); // remove this line

        }

        return cryptoDataUtilitiesMap;
    }

}
