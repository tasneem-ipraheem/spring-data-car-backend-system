package com.udacity.pricing.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//https://classroom.udacity.com/nanodegrees/nd035/parts/49128b96-8489-4719-a3ea-c0beaaa46ff9/modules/585c4b4f-654f-4a9a-acbd-0fe5576a42ae/lessons/f9a4fae6-81e6-4871-95df-dcd5ba7536a2/concepts/ff7698b0-5bca-4c12-bced-4aaa6f5621cd

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Id not found")
public class PriceException extends RuntimeException  {

	public PriceException(String message) {
		super(message);
	}
}
