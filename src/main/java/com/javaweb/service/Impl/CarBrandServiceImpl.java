package com.javaweb.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.beans.CarBrandDTO;
import com.javaweb.entity.CarBrandEntity;
import com.javaweb.repository.CarBrandRepository;
import com.javaweb.service.CarBrandService;

@Service
public class CarBrandServiceImpl implements CarBrandService  {
	
	@Autowired
	private CarBrandRepository carBrandRepository;

	@Override
	public List<CarBrandDTO> getAllCarBrand() {
		List<CarBrandEntity> listCarBrandEntity = carBrandRepository.findAll();
		List<CarBrandDTO> listCarBrandDTO = new ArrayList<>();
		return listCarBrandDTO;
	}

}
