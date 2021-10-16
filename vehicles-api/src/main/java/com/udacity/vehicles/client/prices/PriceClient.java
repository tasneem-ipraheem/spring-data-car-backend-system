package com.udacity.vehicles.client.prices;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.udacity.vehicles.client.maps.Address;
import com.udacity.vehicles.domain.Location;

import reactor.core.publisher.Mono;

/**
 * Implements a class to interface with the Pricing Client for price data.
 */
@Component
public class PriceClient {

    private static final Logger log = LoggerFactory.getLogger(PriceClient.class);

    private final WebClient client;
    private final ModelMapper mapper;

    public PriceClient(WebClient pricing,
            ModelMapper mapper){
        this.client = pricing;
        this.mapper = mapper;

    }

    // In a real-world application we'll want to add some resilience
    // to this method with retries/CB/failover capabilities
    // We may also want to cache the results so we don't need to
    // do a request every time
    /**
     * Gets a vehicle price from the pricing client, given vehicle ID.
     * @param vehicleId ID number of the vehicle for which to get the price
     * @return Currency and price of the requested vehicle,
     *   error message that the vehicle ID is invalid, or note that the
     *   service is down.
     */
    public String getPrice(Long vehicleId) {
        try {
        	//http://localhost:8082/prices?vehicleId=1
        	//http://localhost:8082/prices/2
            Price price = client
                    .get().uri("http://localhost:8082/prices/"+vehicleId)
//                    .uri(uriBuilder -> uriBuilder
//                            .path("/prices")
//                            .queryParam("vehicleId", vehicleId)
//                            .build()
//                    )
                    .retrieve().bodyToMono(Price.class).block();
//            mapper.map(Objects.requireNonNull(price), price);

            return String.format("%s %s", price.getCurrency(), price.getPrice());

        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        return "(consult price)";
    }
    
}
