package com.javaweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.service.CarBrandService;

@RestController
@RequestMapping(value = "/api/carbrand")
public class CarBrandApi {
	
	@Autowired
	private CarBrandService carBrandService;
	
	
	@GetMapping(value = "/")
	public Object GetAllCarBrand () {
		return null;
	}
}
