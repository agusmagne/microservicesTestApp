package com.formacionbdi.springboot.app.farm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.formacionbdi.springboot.app.farm.models.entity.Farm;

public interface FarmRepository extends JpaRepository<Farm, Long>{

}
