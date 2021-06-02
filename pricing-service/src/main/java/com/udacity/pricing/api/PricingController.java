package com.udacity.pricing.api;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PricingService;
import com.udacity.pricing.service.impl.PricingServiceImpl;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Implements a REST-based controller for the pricing service.
 */
@RestController
@RequestMapping("/services/price")
public class PricingController {
    private final PricingService pricingService;

    @Autowired
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    /**
     * Gets the price for a requested vehicle.
     *
     * @param vehicleId ID number of the vehicle for which the price is requested
     * @return price of the vehicle, or error that it was not found.
     */
    @GetMapping
    public ResponseEntity<Price> getAll(@RequestParam Long vehicleId) {
        return new ResponseEntity<>(pricingService.getPriceByVehicleID(vehicleId), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Price> getPrice(@PathVariable Long id) {
        return new ResponseEntity<>(pricingService.getPrice(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Price>> getAll() {
        return new ResponseEntity<>(pricingService.getPrices(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody Price price) {
        return new ResponseEntity<>(pricingService.save(price), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> put(@RequestBody Price price) {
        return new ResponseEntity<>(pricingService.save(price), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long vehicleId) {
        Price price = pricingService.getPriceByVehicleID(vehicleId);
        pricingService.delete(price);
        return ResponseEntity.noContent().build();
    }
}
