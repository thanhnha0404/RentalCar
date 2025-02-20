package com.javaweb.service.Impl;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.javaweb.beans.CarDTO;
import com.javaweb.beans.ResultDTO;
import com.javaweb.entity.CarEntity;
import com.javaweb.repository.CarRepository;
import com.javaweb.service.CarService;

@Service
public class CarServiceImpl implements CarService{
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Override
	public Object lessThanSevenDay() {
		LocalDate localDate = LocalDate.now().minusDays(7);
		Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		localDate = LocalDate.now();
		Date endDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<CarEntity> cars = carRepository.findByDateOfStartBetween(startDate,endDate);
		
		ResultDTO rs = new ResultDTO();
		if(cars.size() == 0) {
			rs.setStatus(false);
			rs.setMessage("Không có xe nào được nhập cách đây 7 ngày");
			return rs;
		} else {
			List<CarDTO> carDTOs = convertToDTOList(cars);	
			return carDTOs;
		}
	}
	
	public CarDTO convertToDTO(CarEntity carEntity) {
        return modelMapper.map(carEntity, CarDTO.class);
    }

    public List<CarDTO> convertToDTOList(List<CarEntity> carEntities) {
        return carEntities.stream().map(car -> modelMapper.map(car, CarDTO.class))
                          .collect(Collectors.toList());
    }

	@Override
	public Object topTen() {
		PageRequest topTen = PageRequest.of(0, 10);
		List<CarEntity> cars = carRepository.findTop10ByOrderByHopDongsSizeDesc(topTen);
		ResultDTO rs = new ResultDTO();
		if(cars.size() == 0) {
			rs.setStatus(false);
			rs.setMessage("Không tìm thấy kết quả");
			return rs;
		} else {
			List<CarDTO> carDTOs = convertToDTOList(cars);	
			return carDTOs;
		}
	}

}
