package com.xm.cryptos.demo.controller;


import com.xm.cryptos.demo.constants.CryptoConstants;
import com.xm.cryptos.demo.service.CryptoRecommendationService;
import com.xm.cryptos.demo.utilities.CryptoDataUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crypto")
public class CryptoRecommendationController {

    @Value("${cryptos.symbols.approved}")
    private List<String> approvedCryptosList;

    @Autowired
    private CryptoRecommendationService cryptoRecommendationService;

    // endpoint to get the info about all supported Cryptos in descending order, based on comparison of their normalized range
    @GetMapping("/norm-range")
    public ResponseEntity<Map<String, BigDecimal>> cryptosNormalizedRangeController() {

        Map<String, BigDecimal> result = cryptoRecommendationService.cryptosNormalizedRangeService();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // endpoint to get the info about the crypto with the highest normalized range for a given date
    @GetMapping("/norm-range/{date}")
    public ResponseEntity<Map<String, BigDecimal>> cryptoHighestNormalizedRangeOnDateController(@PathVariable("date")
                                                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        Map<String, BigDecimal> result = cryptoRecommendationService.cryptoWithHighestNormalizedRangeOnDateService(date);

        return new ResponseEntity<>(result, HttpStatus.OK);
        // possible scenario not covered : when we don't have date for provided correct date.
    }

    // endpoint to get the info about oldest, newest, min and max prices for given Crypto Symbol.
    @GetMapping("/info/{symbol}")
    public ResponseEntity<CryptoDataUtilities> getUtilityDataByCrypto(@PathVariable("symbol") String cryptoSymbol) {

        //check if provided cryptoSymbol is present in approvedCryptosList
        if (!approvedCryptosList.contains(cryptoSymbol)) {
            ResponseEntity result = new ResponseEntity<>(CryptoConstants.UNSUPPORTED_CRYPTO_SYMBOL, HttpStatus.BAD_REQUEST);
            return result;
        }
        CryptoDataUtilities utility = cryptoRecommendationService.getUtilityDataForSpecificCryptoService(cryptoSymbol);
        System.out.println("MaxPrice : " + utility.getMax());// remove this line
        System.out.println("MinPrice : " + utility.getMin());// remove this line
        System.out.println("Newest : " + utility.getNewest());// remove this line
        System.out.println("Oldest : " + utility.getOldest());// remove this line

        return new ResponseEntity<>(utility, HttpStatus.OK);
    }

}
