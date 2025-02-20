package com.javaweb.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javaweb.beans.CarDTO;
import com.javaweb.service.CarService;


@RestController

@RequestMapping(value = "/api/car")
public class CarApi {
	@Autowired
	private CarService carService;
	
	@GetMapping
	public Object getCarByBrandActive(@RequestParam(value = "idBrand" , required = false) int idBrand) {
		
		List<CarDTO> listCar = carService.getCarOfBrandActive(idBrand);
		return listCar;
	}
	
    @PostMapping("/updateLogoCar/{id}")
    public ResponseEntity<?> updateCarLogo(@PathVariable("id") int carId, @RequestParam("file") MultipartFile file) {
        try {
            if (carService.updateLogo(carId, file) == 1) {
            	return ResponseEntity.ok("Cập nhật logo thành công!");
            }
            else {
            	return ResponseEntity.badRequest().body("Không thể cập nhật");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật logo: " + e.getMessage());
        }
    }
}
