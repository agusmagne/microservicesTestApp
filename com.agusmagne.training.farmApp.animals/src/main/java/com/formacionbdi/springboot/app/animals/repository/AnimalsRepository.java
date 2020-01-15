package com.formacionbdi.springboot.app.animals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.formacionbdi.springboot.app.animals.models.Animal;

public interface AnimalsRepository extends JpaRepository<Animal, Long>{

}
