package com.udacity.pricing.repository;

import org.springframework.data.repository.CrudRepository;

import com.udacity.pricing.entity.Price;

public interface PriceRepository extends CrudRepository<Price, Long> {

}
