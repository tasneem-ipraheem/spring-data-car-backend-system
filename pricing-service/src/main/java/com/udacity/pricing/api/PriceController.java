package com.udacity.pricing.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.service.PriceService;

@RestController

public class PriceController {
	
	

	   private PriceService priceService;
	   
	   
//	  public PriceController(PriceService priceService) {
//		// TODO Auto-generated constructor stub
//		  this.priceService =  priceService;
//	}

	    @Autowired
	    public void setPriceService(PriceService priceService) {
	        this.priceService = priceService;
	    }

	    @GetMapping("/location")
	    public ResponseEntity<List<Price>> getAllPrices() {
	        List<Price> list = priceService.retrievePricess();
	        return new ResponseEntity<List<Price>>(list, HttpStatus.OK);
	    }

}
