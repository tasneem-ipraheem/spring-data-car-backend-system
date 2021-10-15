package com.udacity.boogle.maps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
public class MapsController {

	/*
	 * ex 
	 * req : http://localhost:9191/maps?lat=1.999&lon=9.555
	 * res : {"address":"1415 7Th Street South","city":"Clanton","state":"AL","zip":"35045"}
	 */
    @GetMapping
    public Address get(@RequestParam Double lat, @RequestParam Double lon) {
        return MockAddressRepository.getRandom();
    }
}
