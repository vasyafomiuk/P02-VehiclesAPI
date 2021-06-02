package com.udacity.pricing.exceptions;

public class PriceException extends RuntimeException {

    public PriceException(String message) {
        super(message);
    }

    public PriceException(long vehicleId){
        super("Cannot find price for Vehicle " + vehicleId);
    }

    public PriceException() {
        super("Cannot find price for Vehicle");
    }
}
