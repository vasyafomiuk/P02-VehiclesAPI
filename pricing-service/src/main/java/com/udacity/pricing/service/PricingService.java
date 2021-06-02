package com.udacity.pricing.service;

import com.udacity.pricing.domain.price.Price;

import java.util.List;


public interface PricingService {
    Price getPrice(Long id);

    Price save(Price price);

    List<Price> getPrices();

    void delete(Price price);

    Price getPriceByVehicleID(Long vehicleId);
}
