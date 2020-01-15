package com.formacionbdi.springboot.app.animals.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.animals.models.Animal;
import com.formacionbdi.springboot.app.animals.repository.AnimalsRepository;
import com.formacionbdi.springboot.app.animals.service.AnimalService;

@RestController
public class AnimalController {

	@Autowired
	private AnimalService animalService;

	@DeleteMapping("/deleteAll")
	public void deleteAll() {
		animalService.deleteAll();
	}

	@PostMapping("/{species}/{maturity}/{sex}/create/{amount}")
	public void createAnimals(@PathVariable(value = "species") String species,
			@PathVariable(value = "maturity") String maturity, @PathVariable(value = "sex") String sex,
			@PathVariable(value = "amount") Integer amount) {

		animalService.createAnimals(species, maturity, sex, amount);
	}

	@PostMapping("/{species}/eggs/create/{amount}")
	public void createEggs(@PathVariable(value = "species") String species,
			@PathVariable(value = "amount") Integer amount) {
		animalService.createEggs(species, amount);
	}

	@DeleteMapping("/{species}/{maturity}/{sex}/delete/{amount}")
	public void deleteAnimals(@PathVariable(value = "species") String species,
			@PathVariable(value = "maturity") String maturity, @PathVariable(value = "sex") String sex,
			@PathVariable(value = "amount") Integer amount) throws Exception {

		animalService.deleteAnimals(species, maturity, sex, amount);
	}

	@DeleteMapping("/{species}/eggs/delete/{amount}")
	public void deleteEggs(@PathVariable(value = "species") String species,
			@PathVariable(value = "amount") Integer amount) throws Exception {

		animalService.deleteEggs(species, amount);
	}
	
	@GetMapping("/eggsHatchedList")
	public List<Integer> getEggsHatchedList(){
		return animalService.getEggsHatchedList();
	}
	
	@GetMapping("/eggsLaidList")
	public List<Integer> getEggsLaidList(){
		return animalService.getEggsLaidList();
	}
	
	@GetMapping("/deadAnimals")
	public List<Integer> getDeadAnimals(){
		return animalService.getDeadAnimals();
	}

	@GetMapping("/updateList")
	public List<Integer> getUpdateList() {
		return animalService.getUpdateList();
	}
}
