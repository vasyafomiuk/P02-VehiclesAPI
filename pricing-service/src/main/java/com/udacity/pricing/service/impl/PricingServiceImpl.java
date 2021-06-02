package com.udacity.pricing.service.impl;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.exceptions.PriceException;
import com.udacity.pricing.repository.PriceRepository;
import com.udacity.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements the pricing service to get prices for each vehicle.
 */
@Service
public class PricingServiceImpl implements PricingService {
    private final PriceRepository priceRepository;

    @Autowired
    public PricingServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * If a valid vehicle ID, gets the price of the vehicle from the stored array.
     *
     * @param vehicleId ID number of the vehicle the price is requested for.
     * @return price of the requested vehicle
     * @throws PriceException vehicleID was not found
     */
    public Price getPriceByVehicleID(Long vehicleId) {
        return priceRepository.findByVehicleId(vehicleId).orElseThrow(() -> new PriceException(vehicleId));
    }

    public List<Price> getPrices() {
        return (List<Price>) priceRepository.findAll();
    }

    @Override
    public void delete(Price price) {
        priceRepository.delete(price);
    }

    @Override
    public Price getPrice(Long id) {
        return priceRepository.findById(id).orElseThrow(PriceException::new);
    }

    public Price save(Price price) {
        return priceRepository.save(price);
    }
}
