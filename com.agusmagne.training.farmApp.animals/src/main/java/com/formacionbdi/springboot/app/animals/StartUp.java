package com.formacionbdi.springboot.app.animals;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.formacionbdi.springboot.app.animals.service.AnimalService;

// This file exists to load all the objects in the repository at startup.
// It loads objects into the lists used in schedules and in service implement
// I'm having trouble with persisting the information modified in the schedules
// for example when I create a chicken egg it looks like this
// object: (species, sex, etc...) -> chicken egg: ("chickens", "unknown", etc...)
// when the egg hatches, the object looks like this
// young chicken: ("chickens", "male"/"female", etc...)
// that works correctly. problems surges when shutting down the services and then starting them up again
// the data loaded is the chicken egg: ("chickens", "UNKNOWN", etc...), not the young chicken.

@Component
public class StartUp {

	@Autowired
	private AnimalService animalService;

	@PostConstruct
	public void init() {
		animalService.StartUp();
	}

}
