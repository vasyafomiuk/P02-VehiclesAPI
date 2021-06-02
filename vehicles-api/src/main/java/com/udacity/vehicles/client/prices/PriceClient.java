package com.udacity.vehicles.client.prices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Implements a class to interface with the Pricing Client for price data.
 */
@Component
public class PriceClient {
    private static final Logger log = LoggerFactory.getLogger(PriceClient.class);
    private final WebClient client;

    public PriceClient(WebClient pricing) {
        this.client = pricing;
    }

    // In a real-world application we'll want to add some resilience
    // to this method with retries/CB/failover capabilities
    // We may also want to cache the results so we don't need to
    // do a request every time

    /**
     * Gets a vehicle price from the pricing client, given vehicle ID.
     *
     * @param vehicleId ID number of the vehicle for which to get the price
     * @return Currency and price of the requested vehicle,
     * error message that the vehicle ID is invalid, or note that the
     * service is down.
     */
    public String getPrice(Long vehicleId) {
        try {
            Price price = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("services/price/")
                            .queryParam("vehicleId", vehicleId)
                            .build()
                    )
                    .retrieve().bodyToMono(Price.class).block();

            return String.format("%s %s", price.getCurrency(), price.getPrice());

        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        return "(consult price)";
    }

    public Price getPriceObject(Long vehicleId) {
        try {
            Price price = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("services/price/")
                            .queryParam("vehicleId", vehicleId)
                            .build()
                    )
                    .retrieve().bodyToMono(Price.class).block();

            return price;

        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        return null;
    }

    public String addPrice(Price price) {
        try {
            client
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("services/price/")
                            .build()
                    )
                    .body(Mono.just(price), Price.class)
                    .retrieve().bodyToMono(Price.class).block();
            return "price added";
        } catch (Exception e) {
            log.error("Failed to create a price: " + price);
            e.printStackTrace();
        }
        return "price not added";
    }

    public String updatePrice(Price price) {
        try {
            client
                    .put()
                    .uri(uriBuilder -> uriBuilder
                            .path("services/price/")
                            .build()
                    )
                    .body(Mono.just(price), Price.class)
                    .retrieve().bodyToMono(Price.class).block();
            return "price updated";
        } catch (Exception e) {
            log.error("Failed to create a price: " + price);
            e.printStackTrace();
        }
        return "price not updated";
    }

    public String deletePrice(Long vehicleId) {
        System.out.println("inside deletePrice client ");
        try {
            client
                    .method(HttpMethod.DELETE)
                    .uri(uriBuilder -> uriBuilder
                            .path("services/price")
                            .queryParam("vehicleId", vehicleId)
                            .build()
                    )
                    .exchange()
                    .subscribe(res -> System.out.println("DELETED PRICE: " + res.statusCode()));
            return "price deleted";
        } catch (Exception e) {
            log.error("Failed to delete a price");
            e.printStackTrace();
        }
        return "price not deleted";
    }
}
