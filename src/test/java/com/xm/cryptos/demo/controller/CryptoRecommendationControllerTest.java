package com.xm.cryptos.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.cryptos.demo.service.CryptoRecommendationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(value = {SpringExtension.class})
@WebMvcTest(value = CryptoRecommendationController.class)
public class CryptoRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoRecommendationService cryptoRecommendationService;


    @Test
    public void cryptosNormalizedRangeControllerTest() throws Exception {
        HashMap<String, BigDecimal> mockCryptosNormalizedRange = new HashMap<>(
                Map.of("ETH", new BigDecimal(0.6384),
                        "XRP", new BigDecimal(0.5061),
                        "DOGE", new BigDecimal(0.5047),
                        "LTC", new BigDecimal(0.4652),
                        "BTC", new BigDecimal(0.4341)));

        Mockito.when(cryptoRecommendationService.cryptosNormalizedRangeService()).thenReturn(mockCryptosNormalizedRange);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/crypto/norm-range").accept(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        String mockCryptosNormalizedRangeToJson = objectMapper.writeValueAsString(mockCryptosNormalizedRange);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        assertEquals(mockCryptosNormalizedRangeToJson.toString(), result.getResponse().getContentAsString());
    }
}
