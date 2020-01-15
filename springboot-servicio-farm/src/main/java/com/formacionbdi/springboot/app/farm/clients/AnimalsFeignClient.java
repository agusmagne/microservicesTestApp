package com.formacionbdi.springboot.app.farm.clients;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;

import feign.Param;

@FeignClient(name = "animals")
public interface AnimalsFeignClient {

	@DeleteMapping("/deleteAll")
	public void deleteAll();

	@PostMapping("/{species}/{maturity}/{sex}/create/{amount}")
	public void createAnimals(@PathVariable(value = "species") String species,
			@PathVariable(value = "maturity") String maturity, @PathVariable(value = "sex") String sex,
			@PathVariable(value = "amount") Integer amount);

	@DeleteMapping("/{species}/{maturity}/{sex}/delete/{amount}")
	public void deleteAnimals(@PathVariable(value = "species") String species,
			@PathVariable(value = "maturity") String maturity, @PathVariable(value = "sex") String sex,
			@PathVariable(value = "amount") Integer amount) throws Exception;

	@PostMapping("/{species}/eggs/create/{amount}")
	public void createEggs(@PathVariable(value = "species") String species,
			@PathVariable(value = "amount") Integer amount);

	@DeleteMapping("/{species}/eggs/delete/{amount}")
	public void deleteEggs(@PathVariable(value = "species") String species,
			@PathVariable(value = "amount") Integer amount) throws Exception;

	@GetMapping("/eggsHatchedList")
	public List<Integer> getEggsHatchedList();
	
	@GetMapping("/deadAnimals")
	public List<Integer> getDeadAnimals();
	
	@GetMapping("/updateList")
	public List<Integer> getUpdateList();
	
	@GetMapping("/eggsLaidList")
	public List<Integer> getEggsLaidList();
}
