package com.javaweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.service.CarService;

@RestController
@RequestMapping(value = "/api/car")
public class CarAPI {
	
	@Autowired
	private CarService carService;
	
	@GetMapping(value = "/lessthan7days")
	public Object lessThanSevenDay(){
		return carService.lessThanSevenDay();
	}
	
	@GetMapping(value = "/topTen")
	public Object topTen(){
		return carService.topTen();
	}
	
}
