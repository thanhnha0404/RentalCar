package com.javaweb.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javaweb.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, Integer>{
	List<CarEntity> findByDateOfStartBetween(Date startDate, Date endDate);
	
	@Query("SELECT c FROM CarEntity c LEFT JOIN c.contractEntities co GROUP BY c.id ORDER BY COUNT(co) DESC")
	List<CarEntity> findTop10ByOrderByHopDongsSizeDesc(Pageable pageable);
}
