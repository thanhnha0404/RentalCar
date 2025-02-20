package com.javaweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.javaweb.entity.CarEntity;

public interface CarRepository extends JpaRepository <CarEntity,Integer> {
	List<CarEntity> findByBrand_IdAndStatus(int brandId, String status);
	@Modifying
	@Transactional
	@Query("UPDATE CarEntity c SET c.picture = :picture  WHERE c.id = :id")
	public int updatePicutre(@Param("id")  int id, @Param("picture") byte[] picture);
}
