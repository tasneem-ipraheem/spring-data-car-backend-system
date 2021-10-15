package com.udacity.pricing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.repository.PriceRepository;

@Service
public class PriceServiceImp implements PriceService{
	  @Autowired
	  PriceRepository priceRepository;

	    public List<Price> retrievePricess() {
	        return (List<Price>) priceRepository.findAll();
	    }

}
