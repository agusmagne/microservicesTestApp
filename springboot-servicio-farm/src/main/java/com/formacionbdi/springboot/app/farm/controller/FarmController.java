package com.formacionbdi.springboot.app.farm.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.farm.service.FarmService;

@RestController
public class FarmController {

	@Autowired
	private FarmService farmService;

	@PostMapping("/create/{name}")
	public ResponseEntity<Map<String, Object>> createFarm(@PathVariable String name) {
		return farmService.createFarm(name);
	}

	@PostMapping("/buy/{species}/{maturity}/{sex}/{amount}")
	public ResponseEntity<Map<String, Object>> buyAnimals(@PathVariable(value = "species") String species,
			@PathVariable(value = "maturity") String maturity, @PathVariable(value = "sex") String sex,
			@PathVariable(value = "amount") Integer amount) {
		return farmService.buyAnimals(species, maturity, sex, amount);
	}

	@PostMapping("/buy/{species}/eggs/{amount}")
	public ResponseEntity<Map<String, Object>> buyEggs(@PathVariable(value = "species") String species,
			@PathVariable(value = "amount") Integer amount) {
		return farmService.buyEggs(species, amount);
	}

	@PostMapping("/sell/{species}/{maturity}/{sex}/{amount}")
	public ResponseEntity<Map<String, Object>> sellAnimals(@PathVariable(value = "species") String species,
			@PathVariable(value = "maturity") String maturity, @PathVariable(value = "sex") String sex,
			@PathVariable(value = "amount") Integer amount) {
		return farmService.sellAnimals(species, maturity, sex, amount);
	}

	@PostMapping("/sell/{species}/eggs/{amount}")
	public ResponseEntity<Map<String, Object>> sellEggs(@PathVariable(value = "species") String species,
			@PathVariable(value = "amount") Integer amount) {
		return farmService.sellEggs(species, amount);
	}

	@PostMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteFarm() {
		return farmService.deleteFarm();
	}

	@PostMapping("/report")
	public ResponseEntity<Map<String, Object>> writeReport() throws IOException {
		return farmService.writeReport();
	}

	@PostMapping("/showReport")
	public ResponseEntity<Map<String, Object>> showReport(){
		return farmService.showReport();
	}
	
	@GetMapping("/getFarmFood")
	public Integer getFarmFood() {
		return farmService.getFarmFood();
	}

	@PutMapping("/setFarmFood")
	public void setFarmFood(@RequestBody Integer amount) {
		farmService.setFarmFood(amount);
	}

	@PostMapping("/buy/food/{amount}")
	public ResponseEntity<Map<String, Object>> buyFood(@PathVariable Integer amount) {
		return farmService.buyFood(amount);
	}

	@PutMapping("/updateFarm")
	public void updateFarm() {
		farmService.updateFarm();
	}
	
}