package com.udacity.pricing.domain.price;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Represents the price of a given vehicle, including currency.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String currency;
    private BigDecimal price;
    @Column(unique = true)
    private Long vehicleId;

    public Price(String currency, BigDecimal price, Long vehicleId){
        this.currency = currency;
        this.price = price;
        this.vehicleId = vehicleId;
    }
}
