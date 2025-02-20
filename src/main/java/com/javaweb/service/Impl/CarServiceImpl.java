package com.javaweb.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaweb.repository.CarRepository;
import com.javaweb.beans.CarDTO;
import com.javaweb.entity.CarEntity;
import com.javaweb.service.CarService;

@Service
public class CarServiceImpl implements CarService{
	
	@Autowired
	private CarRepository carRepository;
	
	
	@Override
	public List<CarDTO> getCarOfBrandActive(int idBrand) {
		
		List<CarEntity> listCarEntity = carRepository.findByBrand_IdAndStatus(idBrand, "Available");
		List<CarDTO> listCarDTO = new ArrayList<>();
		for (CarEntity item : listCarEntity) {
			CarDTO carDTO = new CarDTO();
			
			carDTO.setId(item.getId());
			carDTO.setName(item.getName());
			carDTO.setDateOfStart(item.getDateOfStart());
			carDTO.setDescription(item.getDescription());
			carDTO.setPicture(item.getPicture());
			carDTO.setPrice(item.getPrice());
			listCarDTO.add(carDTO);
			
		}
		
		return listCarDTO;
		
	}

	@Override
	public int updateLogo(int carId, MultipartFile file) throws IOException {
	    if (file.isEmpty()) {
	        return 0;
	    }
	    // Cập nhật mảng byte vào database (THAY VÌ LƯU TÊN FILE)
	    return carRepository.updatePicutre(carId, file.getBytes());
	    
	}

}
