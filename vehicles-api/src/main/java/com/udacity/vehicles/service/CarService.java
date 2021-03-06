package com.udacity.vehicles.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final PriceClient priceClient;
    private final MapsClient mapsClient;
    
    public CarService(CarRepository repository,
    		@Qualifier("pricing") WebClient priceWebClient,
    		MapsClient mapsClient) {
        /**
         * TODO: DONE: Add the Maps and Pricing Web Clients you create
         *   in `VehiclesApiApplication` as arguments and set them here.
         */
        this.repository = repository;
		this.priceClient = new PriceClient(priceWebClient);
		this.mapsClient = mapsClient;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
//        return repository.findAll();
    	
    	List<Car> cars = repository.findAll();
    	
    	cars.forEach(car -> fillCar(car) );
    	
    	return cars;
    }

    public Car fillCar(Car car) {
    	
    	 String price = priceClient.getPrice(car.getId());
         car.setPrice(price);
         
         Location address = mapsClient.getAddress(car.getLocation());
         car.setLocation(address);
         
         return car;

	}
    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long vehicleId) {
        /**
         * TODO: DONE: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         *   Remove the below code as part of your implementation.
         */
        Car car = repository.findById(vehicleId).orElseThrow(CarNotFoundException::new);


        /**
         * TODO: DONE: Use the Pricing Web client you create in `VehiclesApiApplication`
         *   to get the price based on the `id` input'
         * TODO: DONE: Set the price of the car
         * Note: The car class file uses @transient, meaning you will need to call
         *   the pricing service each time to get the price.
         */
        String price = priceClient.getPrice(vehicleId);
        car.setPrice(price);

        System.out.println("price done : "+price);
        
        /**
         * TODO: DONE: Use the Maps Web client you create in `VehiclesApiApplication`
         *   to get the address for the vehicle. You should access the location
         *   from the car object and feed it to the Maps service.
         * TODO: DONE: Set the location of the vehicle, including the address information
         * Note: The Location class file also uses @transient for the address,
         * meaning the Maps service needs to be called each time for the address.
         */
        Location address = mapsClient.getAddress(car.getLocation());
        car.setLocation(address);
        System.out.println("Location done : "+address.toString());

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
//                        carToBeUpdated.setPrice(car.getPrice());

                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        /**
         * TODO: DONE: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         */
        Car car = repository.findById(id).orElseThrow(CarNotFoundException::new);

        /**
         * TODO: DONE: Delete the car from the repository.
         */
        repository.deleteById(id);

    }
}
