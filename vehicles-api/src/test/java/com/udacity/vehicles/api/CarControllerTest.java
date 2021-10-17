package com.udacity.vehicles.api;

//	// good material : https://lankydan.dev/2017/03/26/testing-data-transfer-objects-and-rest-controllers-in-spring-boot
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.service.CarService;

/**
 * Implements testing of the CarController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<Car> json;

	@MockBean
	private CarService carService;

	@MockBean
	private PriceClient priceClient;

	@MockBean
	private MapsClient mapsClient;

	private static String CAR_JSON_STRING = "";
	private static String CARS_LIST_JSON_STRING = "";

	
	Long id = 1L;
//	public static final org.springframework.http.MediaType HAL_JSON_UTF8;
	public static MediaType HAL_JSON_UTF8 = new MediaType("application", "hal+json", Charset.forName("UTF-8"));

	/**
	 * Creates pre-requisites for testing, such as an example car.
	 */
	@Before
	public void setup() {

		Car car = getCreatedCar();
//		car.setId(id);

		given(carService.save(any())).willReturn(car);
		given(carService.findById(any())).willReturn(car);
		given(carService.list()).willReturn(Collections.singletonList(car));

		try {
			json.write(car);
		} catch (IOException e) {
			e.printStackTrace();
		}

		CAR_JSON_STRING = getCarJsonString();
		CARS_LIST_JSON_STRING = getCarsListJsonString();
//		JacksonTester.initFields(this, car);
	}

	/**
	 * Tests for successful creation of new car in the system
	 * 
	 * @throws Exception when car creation fails in the system
	 */
	@Test
	public void createCar() throws Exception {
		Car car = getCar();
		mvc.perform(post(new URI("/cars")).content(json.write(car).getJson())
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
	}
	
	/**
	 * Tests for successful creation of new car in the system
	 * 
	 * @throws Exception when car creation fails in the system
	 */
	@Test
	public void editCar() throws Exception {
		Car newCar = getNewCar();
		
		mvc.perform(MockMvcRequestBuilders.put(new URI("/cars/"+id))
				.content(json.write(newCar).getJson())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}


	/**
	 * Tests if the read operation appropriately returns a list of vehicles.
	 * 
	 * @throws Exception if the read operation of the vehicle list fails
	 */
	@Test
	public void listCars() throws Exception {
		/**
		 * TODO: DONE: Add a test to check that the `get` method works by calling the whole
		 * list of vehicles. This should utilize the car from `getCar()` below (the
		 * vehicle will be the first in the list).
		 */

		mvc.perform(get("/cars")).andExpect(status().isOk())
			.andExpect(content().contentType(HAL_JSON_UTF8))
			.andExpect(content().json(CARS_LIST_JSON_STRING));

		verify(carService, times(1)).list();

	}

	/**
	 * Tests the read operation for a single car by ID.
	 * 
	 * @throws Exception if the read operation for a single car fails
	 */
	@Test
	public void findCar() throws Exception {
		/**
		 * TODO: DONE: Add a test to check that the `get` method works by calling a vehicle by
		 * ID. This should utilize the car from `getCar()` below.
		 */

		mvc.perform(get("/cars/" + id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(HAL_JSON_UTF8))
				/*
				 * or we can check single values as Follows
				 *        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
				 *        .andExpect(MockMvcResultMatchers.jsonPath("$.condition").value(Condition.USED));
				 */
				.andExpect(content().json(CAR_JSON_STRING));

		verify(carService, times(1)).findById(id);

	}

	/**
	 * Tests the deletion of a single car by ID.
	 * 
	 * @throws Exception if the delete operation of a vehicle fails
	 */
	@Test
	public void deleteCar() throws Exception {
		/**
		 * TODO: DONE: Add a test to check whether a vehicle is appropriately deleted when the
		 * `delete` method is called from the Car Controller. This should utilize the
		 * car from `getCar()` below.
		 */
		
		mvc.perform(MockMvcRequestBuilders.delete("/cars/" + id))
			.andExpect(status().isNoContent());
		
		verify(carService, times(1)).delete(id);

		
	}

	/**
	 * Creates an example Car object for use in testing.
	 * 
	 * @return an example Car object
	 */
	private Car getCar() {
		Car car = new Car();
		car.setLocation(new Location(40.730610, -73.935242));
		Details details = new Details();
		Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
		details.setManufacturer(manufacturer);
		details.setModel("Impala");
		details.setMileage(32280);
		details.setExternalColor("white");
		details.setBody("sedan");
		details.setEngine("3.6L V6");
		details.setFuelType("Gasoline");
		details.setModelYear(2018);
		details.setProductionYear(2018);
		details.setNumberOfDoors(4);
		car.setDetails(details);
		car.setCondition(Condition.USED);
		return car;
	}

	private Car getCreatedCar() {
		Car dummyCar = getCar();

		dummyCar.setId(id);
		dummyCar.setPrice("USD 1000.0");

		return dummyCar;

	}
	
	private Car getNewCar() {
		Car newCar = getCreatedCar();
		
		
		Details details = newCar.getDetails();
		details.setNumberOfDoors(3);;
		newCar.setDetails(details);
		
		return newCar;
	}

	private String getCarJsonString() {
		return " {\r\n" + "  \"id\":1,\r\n" 
				+ "   \"createdAt\":null,\r\n" 
				+ "   \"modifiedAt\":null,\r\n"
				+ "   \"condition\":\"USED\",\r\n" 
				+ "   \"details\":{\r\n" 
				+ "      \"body\":\"sedan\",\r\n"
				+ "      \"model\":\"Impala\",\r\n" 
				+ "      \"manufacturer\":{\r\n" 
				+ "         \"code\":101,\r\n"
				+ "         \"name\":\"Chevrolet\"\r\n" 
				+ "      },\r\n" 
				+ "      \"numberOfDoors\":4,\r\n"
				+ "      \"fuelType\":\"Gasoline\",\r\n" 
				+ "      \"engine\":\"3.6L V6\",\r\n"
				+ "      \"mileage\":32280,\r\n" 
				+ "      \"modelYear\":2018,\r\n"
				+ "      \"productionYear\":2018,\r\n" 
				+ "      \"externalColor\":\"white\"\r\n" 
				+ "   },\r\n"
				+ "   \"location\":{\r\n" 
				+ "      \"lat\":40.73061,\r\n" 
				+ "      \"lon\":-73.935242,\r\n"
				+ "      \"address\":null,\r\n" 
				+ "      \"city\":null,\r\n" 
				+ "      \"state\":null,\r\n"
				+ "      \"zip\":null\r\n" 
				+ "   },\r\n" 
				+ "   \"price\":\"USD 1000.0\",\r\n" 
				+ "   \"_links\":{\r\n"
				+ "      \"self\":{\r\n"
				+ "         \"href\":\"http://localhost/cars/1\"\r\n" 
				+ "      },\r\n"
				+ "      \"cars\":{\r\n"
				+ "         \"href\":\"http://localhost/cars\"\r\n" 
				+ "      }\r\n"
				+ "   }\r\n" 
				+ "}";
	}
	

	private String getCarsListJsonString() {
		return "{\r\n" + "   \"_embedded\":{\r\n" 
				+ "      \"carList\":[\r\n" 
				+ "         {\r\n"
				+ "            \"id\":1,\r\n" 
				+ "            \"createdAt\":null,\r\n"
				+ "            \"modifiedAt\":null,\r\n" 
				+ "            \"condition\":\"USED\",\r\n"
				+ "            \"details\":{\r\n" 
				+ "               \"body\":\"sedan\",\r\n"
				+ "               \"model\":\"Impala\",\r\n" 
				+ "               \"manufacturer\":{\r\n"
				+ "                  \"code\":101,\r\n" 
				+ "                  \"name\":\"Chevrolet\"\r\n"
				+ "               },\r\n" 
				+ "               \"numberOfDoors\":4,\r\n"
				+ "               \"fuelType\":\"Gasoline\",\r\n" 
				+ "               \"engine\":\"3.6L V6\",\r\n"
				+ "               \"mileage\":32280,\r\n" 
				+ "               \"modelYear\":2018,\r\n"
				+ "               \"productionYear\":2018,\r\n" 
				+ "               \"externalColor\":\"white\"\r\n"
				+ "            },\r\n" 
				+ "            \"location\":{\r\n" 
				+ "               \"lat\":40.73061,\r\n"
				+ "               \"lon\":-73.935242,\r\n" 
				+ "               \"address\":null,\r\n"
				+ "               \"city\":null,\r\n" 
				+ "               \"state\":null,\r\n"
				+ "               \"zip\":null\r\n" 
				+ "            },\r\n" 
				+ "            \"price\":\"USD 1000.0\",\r\n"
				+ "            \"_links\":{\r\n" 
				+ "               \"self\":{\r\n"
				+ "                  \"href\":\"http://localhost/cars/1\"\r\n" 
				+ "               },\r\n"
				+ "               \"cars\":{\r\n" 
				+ "                  \"href\":\"http://localhost/cars\"\r\n"
				+ "               }\r\n" 
				+ "            }\r\n" 
				+ "         }\r\n" 
				+ "      ]\r\n" 
				+ "   },\r\n"
				+ "   \"_links\":{\r\n" 
				+ "      \"self\":{\r\n" 
				+ "         \"href\":\"http://localhost/cars\"\r\n"
				+ "      }\r\n" 
				+ "   }\r\n" 
				+ "}";
	}

}