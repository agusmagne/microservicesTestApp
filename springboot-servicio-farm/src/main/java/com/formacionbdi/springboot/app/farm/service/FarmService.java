package com.formacionbdi.springboot.app.farm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import feign.RequestLine;

public interface FarmService {

	@PostMapping("/create/{name}")
	public ResponseEntity<Map<String, Object>> createFarm(@PathVariable String name);

	@PostMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteFarm();

	@PostMapping("/buy/{species}/{maturity}/{sex}/{amount}")
	public ResponseEntity<Map<String, Object>> buyAnimals(@PathVariable(value = "species") String species,
			@PathVariable(value = "maturity") String maturity, @PathVariable(value = "sex") String sex,
			@PathVariable(value = "amount") Integer amount);

	@PostMapping("/sell/{species}/{maturity}/{sex}/{amount}")
	public ResponseEntity<Map<String, Object>> sellAnimals(@PathVariable(value = "species") String species,
			@PathVariable(value = "maturity") String maturity, @PathVariable(value = "sex") String sex,
			@PathVariable(value = "amount") Integer amount);

	@PostMapping("/buy/{species}/eggs/{amount}")
	public ResponseEntity<Map<String, Object>> buyEggs(@PathVariable(value = "species") String species,
			@PathVariable(value = "amount") Integer amount);

	@PostMapping("/sell/{species}/eggs/{amount}")
	public ResponseEntity<Map<String, Object>> sellEggs(@PathVariable(value = "species") String species,
			@PathVariable(value = "amount") Integer amount);

	@PostMapping("/report")
	public ResponseEntity<Map<String, Object>> writeReport() throws IOException;

	@PostMapping("/showReport")
	public ResponseEntity<Map<String, Object>> showReport();
	
	@GetMapping("/getFarmFood")
	public Integer getFarmFood();

	@PutMapping("/setFarmFood")
	public void setFarmFood(@RequestBody Integer amount);

	@PostMapping("/buy/food/{amount}")
	public ResponseEntity<Map<String, Object>> buyFood(@PathVariable Integer amount);

	@PutMapping("/updateFarm")
	public void updateFarm();
}