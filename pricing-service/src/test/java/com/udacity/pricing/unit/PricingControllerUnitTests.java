package com.udacity.pricing.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.pricing.api.PricingController;
import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.impl.PricingServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PricingController.class)
public class PricingControllerUnitTests {
    private static final Logger logger = LoggerFactory.getLogger(PricingControllerUnitTests.class);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private PricingServiceImpl pricingService;
    private Price price;

    @Test
    public void getAllPrices() throws Exception {
        mvc.perform(get("/services/price/all")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        verify(pricingService, times(1)).getPrices();
    }

    @Test
    public void getOnePriceObject() throws Exception {
        mvc.perform(get("/services/price/29")).
                andDo(print()).
                andExpect(status().isOk());
        verify(pricingService, times(1)).getPrice(29L);
    }

    @Test
    public void createPrice() throws Exception {
        price = new Price(1L, "USD", BigDecimal.valueOf(10000L), 1L);
        given(this.pricingService.save(any(Price.class))).willReturn(price);

        mvc.perform(
                post("/services/price").
                        content(new ObjectMapper().writeValueAsString(price)).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON_UTF8)).
                andDo(print()).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.id").value(1)).
                andExpect(jsonPath("$.currency").value("USD")).
                andExpect(jsonPath("$.price").value(10000)).
                andExpect(jsonPath("$.vehicleId").value(1));
    }

    @Test
    public void updatePrice() throws Exception {
        price = new Price(1L, "USD", BigDecimal.valueOf(10000L), 1L);
        given(this.pricingService.save(any(Price.class))).willReturn(price);

        mvc.perform(
                post("/services/price").
                        content(new ObjectMapper().writeValueAsString(price)).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON_UTF8)).
                andDo(print()).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.id").value(1)).
                andExpect(jsonPath("$.currency").value("USD")).
                andExpect(jsonPath("$.price").value(10000)).
                andExpect(jsonPath("$.vehicleId").value(1));

        price = new Price(1L, "EUR", BigDecimal.valueOf(19000L), 1L);

        when(this.pricingService.save(any(Price.class))).thenReturn(price);

        mvc.perform(
                put("/services/price").
                        content(new ObjectMapper().writeValueAsString(price)).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON_UTF8)).
                andDo(print()).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.id").value(1)).
                andExpect(jsonPath("$.currency").value("EUR")).
                andExpect(jsonPath("$.price").value(19000)).
                andExpect(jsonPath("$.vehicleId").value(1));
    }


    @Test
    public void deletePriceObject() throws Exception {
        price = new Price(29L, "USD", BigDecimal.valueOf(10000L), 1L);
        given(this.pricingService.save(any(Price.class))).willReturn(price);

        mvc.perform(delete("/services/price?vehicleId=1")).
                andExpect(status().isNoContent());
    }
}
