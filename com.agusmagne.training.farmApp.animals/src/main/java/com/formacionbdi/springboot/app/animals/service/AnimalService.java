package com.formacionbdi.springboot.app.animals.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.formacionbdi.springboot.app.animals.models.Animal;
import com.formacionbdi.springboot.app.animals.repository.AnimalsRepository;

public interface AnimalService {

	public void StartUp();

	@DeleteMapping("/deleteAll")
	public void deleteAll();

	@PostMapping("/{species}/{maturity}/{sex}/create/{amount}")
	public void createAnimals(@PathVariable(value = "species") String species,
			@PathVariable(value = "maturity") String maturity, @PathVariable(value = "sex") String sex,
			@PathVariable(value = "amount") Integer amount);

	@PostMapping("/{species}/eggs/create/{amount}")
	public void createEggs(@PathVariable(value = "species") String species,
			@PathVariable(value = "amount") Integer amount);

	@DeleteMapping("/{species}/{maturity}/{sex}/delete/{amount}")
	public void deleteAnimals(@PathVariable(value = "species") String species,
			@PathVariable(value = "maturity") String maturity, @PathVariable(value = "sex") String sex,
			@PathVariable(value = "amount") Integer amount) throws Exception;

	public void deleteAnimalByEntity(Animal animal);

	@DeleteMapping("/{species}/eggs/delete/{amount}")
	public void deleteEggs(@PathVariable(value = "species") String species,
			@PathVariable(value = "amount") Integer amount) throws Exception;

	@GetMapping("/eggsHatchedList")
	public List<Integer> getEggsHatchedList();
	
	@GetMapping("/eggsLaidList")
	public List<Integer> getEggsLaidList();

	@GetMapping("/deadAnimals")
	public List<Integer> getDeadAnimals();
	
	@GetMapping("/animalsBought")
	public List<Integer> getAnimalsBought();
	
	@GetMapping("/animalsSold")
	public List<Integer> getAnimalsSold();
	
	@GetMapping("/updateList")
	public List<Integer> getUpdateList();

	public void setChickenEggsLaid(Integer chickenEggsLaid);

	public Integer getDuckEggsLaid();

	public void setDuckEggsLaid(Integer duckEggsLaid);

	public Integer getTurkeyEggsLaid();

	public void setTurkeyEggsLaid(Integer turkeyEggsLaid);

	public List<Animal> getAnimalsEggs();

	public List<Animal> getAnimals();

	public List<Animal> getMatureMaleChickens();

	public List<Animal> getMatureFemaleChickens();

	public List<Animal> getMatureMaleDucks();

	public List<Animal> getMatureFemaleDucks();

	public List<Animal> getMatureMaleTurkeys();

	public List<Animal> getMatureFemaleTurkeys();

	public List<Animal> getYoungMaleChickens();

	public List<Animal> getYoungMaleDucks();

	public List<Animal> getYoungMaleTurkeys();

	public List<Animal> getYoungFemaleChickens();

	public List<Animal> getYoungFemaleDucks();

	public List<Animal> getYoungFemaleTurkeys();

	public List<Animal> getOldMaleChickens();

	public List<Animal> getOldMaleDucks();

	public List<Animal> getOldMaleTurkeys();

	public List<Animal> getOldFemaleChickens();

	public List<Animal> getOldFemaleDucks();

	public List<Animal> getOldFemaleTurkeys();

	public Integer getChickensHatched();

	public void setChickensHatched(Integer chickensHatched);

	public Integer getDucksHatched();

	public void setDucksHatched(Integer ducksHatched);

	public Integer getTurkeysHatched();

	public void setTurkeysHatched(Integer turkeysHatched);

	public Integer getDeadChickens();

	public void setDeadChickens(Integer deadChickens);

	public Integer getDeadDucks();

	public void setDeadDucks(Integer deadDucks);

	public Integer getDeadTurkeys();

	public void setDeadTurkeys(Integer deadTurkeys);

}
