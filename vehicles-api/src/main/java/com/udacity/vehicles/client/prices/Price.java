package com.udacity.vehicles.client.prices;

import lombok.*;

import java.math.BigDecimal;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price {
    private Long id;
    private String currency;
    private BigDecimal price;
    private Long vehicleId;

    public Price(String currency, BigDecimal price, Long vehicleId) {
        this.currency = currency;
        this.price = price;
        this.vehicleId = vehicleId;
    }
}
