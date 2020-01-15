package com.formacionbdi.springboot.app.animals.schedules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.formacionbdi.springboot.app.animals.clients.FarmFeignClient;
import com.formacionbdi.springboot.app.animals.models.Animal;
import com.formacionbdi.springboot.app.animals.repository.AnimalsRepository;
import com.formacionbdi.springboot.app.animals.service.AnimalService;

@Component
public class AnimalsSchedules {

	@Autowired
	AnimalsRepository repo;

	@Autowired
	private AnimalService animalService;

	@Autowired
	private FarmFeignClient farmFeignClient;

	private static Integer randomNumberInRange(Integer min, Integer max) {
		Random random = new Random();

		return random.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
	}

	public String getRandomSex(Double percent) {
		double d = Math.random();

		if (d < percent) {
			return "female";
		} else {
			return "male";
		}
	}

	public List<Animal> animalsEggs = new ArrayList<>();
	public List<Animal> animals = new ArrayList<>();
	public List<Animal> matureMaleChickens = new ArrayList<>();
	public List<Animal> matureFemaleChickens = new ArrayList<>();
	public List<Animal> matureMaleDucks = new ArrayList<>();
	public List<Animal> matureFemaleDucks = new ArrayList<>();
	public List<Animal> matureMaleTurkeys = new ArrayList<>();
	public List<Animal> matureFemaleTurkeys = new ArrayList<>();

	public Integer newMatureMaleChickens = 0;
	public Integer newMatureFemaleChickens = 0;
	public Integer newOldMaleChickens = 0;
	public Integer newOldFemaleChickens = 0;

	public Integer newMatureMaleDucks = 0;
	public Integer newMatureFemaleDucks = 0;
	public Integer newOldMaleDucks = 0;
	public Integer newOldFemaleDucks = 0;

	public Integer newMatureMaleTurkeys = 0;
	public Integer newMatureFemaleTurkeys = 0;
	public Integer newOldMaleTurkeys = 0;
	public Integer newOldFemaleTurkeys = 0;

	public List<Animal> youngMaleChickens = new ArrayList<>();
	public List<Animal> youngMaleDucks = new ArrayList<>();
	public List<Animal> youngMaleTurkeys = new ArrayList<>();

	public List<Animal> youngFemaleChickens = new ArrayList<>();
	public List<Animal> youngFemaleDucks = new ArrayList<>();
	public List<Animal> youngFemaleTurkeys = new ArrayList<>();

	public List<Animal> oldMaleChickens = new ArrayList<>();
	public List<Animal> oldMaleDucks = new ArrayList<>();
	public List<Animal> oldMaleTurkeys = new ArrayList<>();

	public List<Animal> oldFemaleChickens = new ArrayList<>();
	public List<Animal> oldFemaleDucks = new ArrayList<>();
	public List<Animal> oldFemaleTurkeys = new ArrayList<>();

	public Integer getMinValue(Integer a, Integer b) {
		if (a < b) {
			return a;
		}
		if (a > b) {
			return b;
		} else {
			return a;
		}
	}

	@Scheduled(fixedDelay = 10000)
	@Transactional
	public void OneDayPassed() {

	
		///////////////////////////// EGG HATCHING///////////////////////////
		Integer newChickens = 0;
		Integer newDucks = 0;
		Integer newTurkeys = 0;

		animalsEggs.clear();
		animalsEggs.addAll(animalService.getAnimalsEggs());
		int animalsEggsSize = animalsEggs.size();

		for (int i = 0; i < animalsEggsSize; i++) {
			Animal egg = animalsEggs.get(i);
			if (egg.getLifespan() != 0) {
				egg.setLifespan(egg.getLifespan() - 1);

			} else {
				egg.setMaturity("young");

				switch (egg.getSpecies()) {
				case "chickens":
					String chickenSex = getRandomSex(0.7);
					egg.setGramsOfFood(200);
					egg.setLifespan(randomNumberInRange(4, 8));
					egg.setSex(chickenSex);
					newChickens++;

					switch (chickenSex) {
					case "male":
						animalService.getYoungMaleChickens().add(egg);
						System.out.println("male chicken born");
						break;
					case "female":
						animalService.getYoungFemaleChickens().add(egg);
						System.out.println("female chicken born");
						break;
					}
					break;

				case "ducks":
					String duckSex = getRandomSex(0.5);
					egg.setGramsOfFood(250);
					egg.setLifespan(randomNumberInRange(6, 9));
					egg.setSex(duckSex);
					newDucks++;

					switch (duckSex) {
					case "male":
						animalService.getYoungMaleDucks().add(egg);
						System.out.println("male duck born");
						break;
					case "female":
						animalService.getYoungFemaleDucks().add(egg);
						System.out.println("female duck born");
						break;
					}
					break;

				case "turkeys":
					String turkeySex = getRandomSex(0.4);
					egg.setGramsOfFood(550);
					egg.setLifespan(2);
					randomNumberInRange(10, 12);
					egg.setSex(turkeySex);
					newTurkeys++;

					switch (turkeySex) {
					case "male":
						animalService.getYoungMaleTurkeys().add(egg);
						System.out.println("male turkey born");
						break;
					case "female":
						animalService.getYoungFemaleTurkeys().add(egg);
						System.out.println("female turkey born");
						break;
					}

					break;
				}
				animalService.getAnimalsEggs().remove(egg);
			}
		}
		animalService.setChickensHatched(newChickens);
		animalService.setDucksHatched(newDucks);
		animalService.setTurkeysHatched(newTurkeys);

		///////////////////////////// ANIMALS
		///////////////////////////// GROWTH////////////////////////////////////////
		Integer deadChickens = 0;
		Integer deadDucks = 0;
		Integer deadTurkeys = 0;
		// ------------------young male chickens to mature chickens
		youngMaleChickens.clear();
		youngMaleChickens.addAll(animalService.getYoungMaleChickens());
		Integer youngMaleChickensSize = youngMaleChickens.size();
		for (int i = 0; i < youngMaleChickensSize; i++) {
			Animal newMatureChicken = youngMaleChickens.get(i);
			if (newMatureChicken.getLifespan() != 0) {
				newMatureChicken.setLifespan(newMatureChicken.getLifespan() - 1);
			} else {
				newMatureChicken.setMaturity("mature");
				newMatureChicken.setGramsOfFood(400);
				newMatureChicken.setLifespan(randomNumberInRange(30, 35));
				animalService.getYoungMaleChickens().remove(newMatureChicken);
				animalService.getMatureMaleChickens().add(newMatureChicken);
				System.out.println("young male chicken to mature male chicken");
			}
		}

		// ------------------young female chickens to mature chickens
		youngFemaleChickens.clear();
		youngFemaleChickens.addAll(animalService.getYoungFemaleChickens());
		Integer youngFemaleChickensSize = youngFemaleChickens.size();
		for (int i = 0; i < youngFemaleChickensSize; i++) {
			Animal newMatureChicken = youngFemaleChickens.get(i);
			if (newMatureChicken.getLifespan() != 0) {
				newMatureChicken.setLifespan(newMatureChicken.getLifespan() - 1);
			} else {
				newMatureChicken.setMaturity("mature");
				newMatureChicken.setGramsOfFood(350);
				newMatureChicken.setLifespan(25);
				newMatureChicken.setDaysToLayEggs(0);
				newMatureChicken.setEggsPerDay(3);
				animalService.getYoungFemaleChickens().remove(newMatureChicken);
				animalService.getMatureFemaleChickens().add(newMatureChicken);
				System.out.println("young female chicken to mature female chicken");
			}
		}
		// -------------------mature male chickens to old chickens
		matureMaleChickens.clear();
		matureMaleChickens.addAll(animalService.getMatureMaleChickens());
		Integer matureMaleChickensSize = matureMaleChickens.size();
		for (int i = 0; i < matureMaleChickensSize; i++) {
			Animal newOldMaleChicken = matureMaleChickens.get(i);
			if (newOldMaleChicken.getLifespan() != 0) {
				newOldMaleChicken.setLifespan(newOldMaleChicken.getLifespan() - 1);
			} else {
				newOldMaleChicken.setMaturity("old");
				newOldMaleChicken.setLifespan(randomNumberInRange(10, 11));
				newOldMaleChicken.setGramsOfFood(200);
				animalService.getMatureMaleChickens().remove(newOldMaleChicken);
				animalService.getOldMaleChickens().add(newOldMaleChicken);
				System.out.println("mature male chicken to old male chicken");
			}
		}

		// -------------------mature female chickens to old chickens
		matureFemaleChickens.clear();
		matureFemaleChickens.addAll(animalService.getMatureFemaleChickens());
		Integer matureFemaleChickensSize = matureFemaleChickens.size();
		for (int i = 0; i < matureFemaleChickensSize; i++) {
			Animal newOldFemaleChicken = matureFemaleChickens.get(i);
			if (newOldFemaleChicken.getLifespan() != 0) {
				newOldFemaleChicken.setLifespan(newOldFemaleChicken.getLifespan() - 1);
			} else {
				newOldFemaleChicken.setMaturity("old");
				newOldFemaleChicken.setLifespan(randomNumberInRange(10, 11));
				newOldFemaleChicken.setGramsOfFood(200);
				animalService.getMatureFemaleChickens().remove(newOldFemaleChicken);
				animalService.getOldFemaleChickens().add(newOldFemaleChicken);
				System.out.println("mature female chicken to old female chicken");
			}
		}

		// -------------------old male chickens to dead chickens
		oldMaleChickens.clear();
		oldMaleChickens.addAll(animalService.getOldMaleChickens());
		Integer oldMaleChickensSize = oldMaleChickens.size();
		for (int i = 0; i < oldMaleChickensSize; i++) {
			Animal deadChicken = oldMaleChickens.get(i);
			if (deadChicken.getLifespan() != 0) {
				deadChicken.setLifespan(deadChicken.getLifespan() - 1);
			} else {
				animalService.getOldMaleChickens().remove(deadChicken);
				animalService.deleteAnimalByEntity(deadChicken);
				animalService.setDeadChickens(animalService.getDeadChickens() + 1);
				deadChickens++;
				System.out.println("male chicken dead");
			}
		}

		// -------------------old female chickens to dead chickens
		oldFemaleChickens.clear();
		oldFemaleChickens.addAll(animalService.getOldFemaleChickens());
		Integer oldFemaleChickensSize = oldFemaleChickens.size();
		for (int i = 0; i < oldFemaleChickensSize; i++) {
			Animal deadChicken = oldFemaleChickens.get(i);
			if (deadChicken.getLifespan() != 0) {
				deadChicken.setLifespan(deadChicken.getLifespan() - 1);
			} else {
				animalService.getOldFemaleChickens().remove(deadChicken);
				animalService.deleteAnimalByEntity(deadChicken);
				animalService.setDeadChickens(animalService.getDeadChickens() + 1);
				deadChickens++;
				System.out.println("female chicken dead");
			}
		}

		// -------------------young male ducks to mature ducks
		youngMaleDucks.clear();
		youngMaleDucks.addAll(animalService.getYoungMaleDucks());
		Integer youngMaleDucksSize = youngMaleDucks.size();

		for (int i = 0; i < youngMaleDucksSize; i++) {
			Animal newMatureDuck = youngMaleDucks.get(i);
			if (newMatureDuck.getLifespan() != 0) {
				newMatureDuck.setLifespan(newMatureDuck.getLifespan() - 1);
			} else {
				newMatureDuck.setMaturity("mature");
				newMatureDuck.setLifespan(randomNumberInRange(37, 40));
				newMatureDuck.setGramsOfFood(500);
				animalService.getYoungMaleDucks().remove(newMatureDuck);
				animalService.getMatureMaleDucks().add(newMatureDuck);
				System.out.println("young male duck to mature male duck");
			}
		}

		// -------------------young female ducks to mature ducks
		youngFemaleDucks.clear();
		youngFemaleDucks.addAll(animalService.getYoungFemaleDucks());
		Integer youngFemaleDucksSize = youngFemaleDucks.size();
		for (int i = 0; i < youngFemaleDucksSize; i++) {
			Animal newMatureDuck = youngFemaleDucks.get(i);
			if (newMatureDuck.getLifespan() != 0) {
				newMatureDuck.setLifespan(newMatureDuck.getLifespan() - 1);
			} else {
				newMatureDuck.setMaturity("mature");
				newMatureDuck.setGramsOfFood(450);
				newMatureDuck.setLifespan(30);
				newMatureDuck.setDaysToLayEggs(2);
				newMatureDuck.setEggsPerDay(1);
				animalService.getYoungFemaleDucks().remove(newMatureDuck);
				animalService.getMatureFemaleDucks().add(newMatureDuck);
				System.out.println("young female duck to mature female duck");
			}
		}

		// -------------------mature male ducks to old ducks
		matureMaleDucks.clear();
		matureMaleDucks.addAll(animalService.getMatureMaleDucks());
		Integer matureMaleDucksSize = matureMaleDucks.size();
		for (int i = 0; i < matureMaleDucksSize; i++) {
			Animal newOldMaleDuck = matureMaleDucks.get(i);
			if (newOldMaleDuck.getLifespan() != 0) {
				newOldMaleDuck.setLifespan(newOldMaleDuck.getLifespan() - 1);
			} else {
				newOldMaleDuck.setMaturity("old");
				newOldMaleDuck.setGramsOfFood(250);
				newOldMaleDuck.setLifespan(randomNumberInRange(12, 15));
				animalService.getMatureMaleDucks().remove(newOldMaleDuck);
				animalService.getOldMaleDucks().add(newOldMaleDuck);
				System.out.println("mature male duck to old male ducks");
			}
		}

		// -------------------mature female ducks to old ducks
		matureFemaleDucks.clear();
		matureFemaleDucks.addAll(animalService.getMatureFemaleDucks());
		Integer matureFemaleDucksSize = matureFemaleDucks.size();
		for (int i = 0; i < matureFemaleDucksSize; i++) {
			Animal newOldFemaleDuck = matureFemaleDucks.get(i);
			if (newOldFemaleDuck.getLifespan() != 0) {
				newOldFemaleDuck.setLifespan(newOldFemaleDuck.getLifespan() - 1);
			} else {
				newOldFemaleDuck.setMaturity("old");
				newOldFemaleDuck.setGramsOfFood(250);
				newOldFemaleDuck.setLifespan(randomNumberInRange(12, 15));
				animalService.getMatureFemaleDucks().remove(newOldFemaleDuck);
				animalService.getOldFemaleDucks().add(newOldFemaleDuck);
				System.out.println("mature female duck to old female duck");
			}
		}

		// --------------------old male ducks to dead ducks
		oldMaleDucks.clear();
		oldMaleDucks.addAll(animalService.getOldMaleDucks());
		Integer oldMaleDucksSize = oldMaleDucks.size();
		for (int i = 0; i < oldMaleDucksSize; i++) {
			Animal deadMaleDuck = oldMaleDucks.get(i);
			if (deadMaleDuck.getLifespan() != 0) {
				deadMaleDuck.setLifespan(deadMaleDuck.getLifespan() - 1);
			} else {
				animalService.deleteAnimalByEntity(deadMaleDuck);
				animalService.getOldMaleDucks().remove(deadMaleDuck);
				animalService.setDeadDucks(animalService.getDeadDucks() + 1);
				deadDucks++;
				System.out.println("male duck dead");
			}
		}

		// --------------------old female ducks to dead ducks
		oldFemaleDucks.clear();
		oldFemaleDucks.addAll(animalService.getOldFemaleDucks());
		Integer oldFemaleDucksSize = oldFemaleDucks.size();
		for (int i = 0; i < oldFemaleDucksSize; i++) {
			Animal deadFemaleDuck = oldFemaleDucks.get(i);
			if (deadFemaleDuck.getLifespan() != 0) {
				deadFemaleDuck.setLifespan(deadFemaleDuck.getLifespan() - 1);
			} else {
				animalService.deleteAnimalByEntity(deadFemaleDuck);
				animalService.getOldFemaleDucks().remove(deadFemaleDuck);
				animalService.setDeadDucks(animalService.getDeadDucks() + 1);
				deadDucks++;
				System.out.println("female duck dead");
			}
		}

		// --------------------young male turkeys to mature turkeys
		youngMaleTurkeys.clear();
		youngMaleTurkeys.addAll(animalService.getYoungMaleTurkeys());
		Integer youngMaleTurkeysSize = youngMaleTurkeys.size();
		for (int i = 0; i < youngMaleTurkeysSize; i++) {
			Animal newMatureMaleTurkey = youngMaleTurkeys.get(i);
			if (newMatureMaleTurkey.getLifespan() != 0) {
				newMatureMaleTurkey.setLifespan(newMatureMaleTurkey.getLifespan() - 1);
			} else {
				newMatureMaleTurkey.setMaturity("mature");
				newMatureMaleTurkey.setGramsOfFood(650);
				newMatureMaleTurkey.setLifespan(randomNumberInRange(46, 50));
				animalService.getYoungMaleTurkeys().remove(newMatureMaleTurkey);
				animalService.getMatureMaleTurkeys().add(newMatureMaleTurkey);
				System.out.println("young male turkey to mature male turkey");
			}
		}

		// --------------------young female turkeys to mature turkeys
		youngFemaleTurkeys.clear();
		youngFemaleTurkeys.addAll(animalService.getYoungFemaleTurkeys());
		Integer youngFemaleTurkeysSize = youngFemaleTurkeys.size();
		for (int i = 0; i < youngFemaleTurkeysSize; i++) {
			Animal newMatureFemaleTurkey = youngFemaleTurkeys.get(i);
			if (newMatureFemaleTurkey.getLifespan() != 0) {
				newMatureFemaleTurkey.setLifespan(newMatureFemaleTurkey.getLifespan() - 1);
			} else {
				newMatureFemaleTurkey.setMaturity("mature");
				newMatureFemaleTurkey.setGramsOfFood(600);
				newMatureFemaleTurkey.setLifespan(45);
				newMatureFemaleTurkey.setDaysToLayEggs(8);
				newMatureFemaleTurkey.setEggsPerDay(2);
				animalService.getYoungFemaleTurkeys().remove(newMatureFemaleTurkey);
				animalService.getMatureFemaleTurkeys().add(newMatureFemaleTurkey);
				System.out.println("young female turkey to mature female turkey");
			}
		}

		// --------------------mature male turkeys to old turkeys
		matureMaleTurkeys.clear();
		matureMaleTurkeys.addAll(animalService.getMatureMaleTurkeys());
		Integer matureMaleTurkeysSize = matureMaleTurkeys.size();
		for (int i = 0; i < matureMaleTurkeysSize; i++) {
			Animal newOldMaleTurkey = matureMaleTurkeys.get(i);
			if (newOldMaleTurkey.getLifespan() != 0) {
				newOldMaleTurkey.setLifespan(newOldMaleTurkey.getLifespan() - 1);
			} else {
				newOldMaleTurkey.setMaturity("old");
				newOldMaleTurkey.setLifespan(randomNumberInRange(30, 33));
				newOldMaleTurkey.setGramsOfFood(350);
				animalService.getMatureMaleTurkeys().remove(newOldMaleTurkey);
				animalService.getOldMaleTurkeys().add(newOldMaleTurkey);
				System.out.println("mature male turkey to old male turkey");
			}
		}

		// --------------------mature female turkeys to old turkeys
		matureFemaleTurkeys.clear();
		matureFemaleTurkeys.addAll(animalService.getMatureFemaleTurkeys());
		Integer matureFemaleTurkeysSize = matureFemaleTurkeys.size();
		for (int i = 0; i < matureFemaleTurkeysSize; i++) {
			Animal newOldFemaleTurkey = matureFemaleTurkeys.get(i);
			if (newOldFemaleTurkey.getLifespan() != 0) {
				newOldFemaleTurkey.setLifespan(newOldFemaleTurkey.getLifespan());
			} else {
				newOldFemaleTurkey.setMaturity("old");
				newOldFemaleTurkey.setGramsOfFood(350);
				newOldFemaleTurkey.setLifespan(randomNumberInRange(22, 25));
				animalService.getMatureFemaleTurkeys().remove(newOldFemaleTurkey);
				animalService.getOldFemaleTurkeys().add(newOldFemaleTurkey);
				System.out.println("mature female turkey to old female turkey");
			}
		}

		// --------------------old male turkeys to dead turkeys
		oldMaleTurkeys.clear();
		oldMaleTurkeys.addAll(animalService.getOldMaleTurkeys());
		Integer oldMaleTurkeysSize = oldMaleTurkeys.size();
		for (int i = 0; i < oldMaleTurkeysSize; i++) {
			Animal deadMaleTurkey = oldMaleTurkeys.get(i);
			if (deadMaleTurkey.getLifespan() != 0) {
				deadMaleTurkey.setLifespan(deadMaleTurkey.getLifespan() - 1);
			} else {
				animalService.getOldMaleTurkeys().remove(deadMaleTurkey);
				animalService.deleteAnimalByEntity(deadMaleTurkey);
				animalService.setDeadTurkeys(animalService.getDeadTurkeys() + 1);
				deadTurkeys++;
				System.out.println("male turkey dead");
			}
		}

		// --------------------old female turkeys to dead turkeys
		oldFemaleTurkeys.clear();
		oldFemaleTurkeys.addAll(animalService.getOldFemaleTurkeys());
		Integer oldFemaleTurkeysSize = oldFemaleTurkeys.size();
		for (int i = 0; i < oldFemaleTurkeysSize; i++) {
			Animal deadFemaleTurkey = oldFemaleTurkeys.get(i);
			if (deadFemaleTurkey.getLifespan() != 0) {
				deadFemaleTurkey.setLifespan(deadFemaleTurkey.getLifespan() - 1);
			} else {
				animalService.getOldFemaleTurkeys().remove(deadFemaleTurkey);
				animalService.deleteAnimalByEntity(deadFemaleTurkey);
				animalService.setDeadTurkeys(animalService.getDeadTurkeys() + 1);
				deadTurkeys++;
				System.out.println("female turkey dead");
			}
		}
		
		animalService.setDeadChickens(deadChickens);
		animalService.setDeadDucks(deadDucks);
		animalService.setDeadTurkeys(deadTurkeys);

		/////////////////////////////////// EGG
		/////////////////////////////////// LAYING/////////////////////////////////////
		// CHICKENS
		/////////////////////////////////// ----------------------------------------------------------------------------
		// update lists
		matureMaleChickens.clear();
		matureFemaleChickens.clear();
		matureMaleChickens.addAll(animalService.getMatureMaleChickens());
		matureFemaleChickens.addAll(animalService.getMatureFemaleChickens());

		// chickens lay 1 egg every day (if there's at least 1 mature male chicken in
		// the farm)
		// I iterate over the minimum between the total of mature female chickens or the
		// total of mature male chickens times eight (which is the maximum male chickens
		// would be inseminating)

		Integer inseminatedFemaleChickens = matureMaleChickens.size() * 8;
		Integer chickensReadyToLayAmount = getMinValue(matureFemaleChickens.size(), inseminatedFemaleChickens);

		for (int i = 0; i < chickensReadyToLayAmount; i++) {
			Animal chicken = matureFemaleChickens.get(i);
			animalService.createEggs(chicken.getSpecies(), chicken.getEggsPerDay());
			System.out.println("chicken egg laid");
		}
		animalService.setChickenEggsLaid(chickensReadyToLayAmount * 3);

		// DUCKS
		// ----------------------------------------------------------------------------
		// update lists
		matureMaleDucks.clear();
		matureFemaleDucks.clear();
		matureMaleDucks.addAll(animalService.getMatureMaleDucks());
		matureFemaleDucks.addAll(animalService.getMatureFemaleDucks());
		Integer ducksReadyToLay = 0;

//		 first i'm getting all ducks that are ready to lay
		for (int i = 0; i < matureFemaleDucks.size(); i++) {
			Animal duck = matureFemaleDucks.get(i);
			if (duck.getDaysToLayEggs() != 0) {
				duck.setDaysToLayEggs(duck.getDaysToLayEggs() - 1);
			} else {
				ducksReadyToLay++;
				duck.setDaysToLayEggs(2);
			}
		}
		// then i'm iterating over those ready ducks, and my boundary is either the max
		// of ducks ready, or the max of mature males
		Integer ducksLayingToday = getMinValue(ducksReadyToLay, matureMaleDucks.size());
		for (int i = 0; i < ducksLayingToday; i++) {
			animalService.createEggs("ducks", 1);
			System.out.println("duck egg laid");
		}

		animalService.setDuckEggsLaid(ducksLayingToday);
		// ----------------------------------------------------------------------------

		// TURKEYS
		matureMaleTurkeys.clear();
		matureFemaleTurkeys.clear();
		matureMaleTurkeys.addAll(animalService.getMatureMaleTurkeys());
		matureFemaleTurkeys.addAll(animalService.getMatureFemaleTurkeys());
		Integer turkeysReadyToLay = 0;

		for (int i = 0; i < matureFemaleTurkeys.size(); i++) {
			Animal turkey = matureFemaleTurkeys.get(i);
			if (turkey.getDaysToLayEggs() == 0) {
				turkeysReadyToLay++;
				turkey.setDaysToLayEggs(8);
			} else {
				turkey.setDaysToLayEggs(turkey.getDaysToLayEggs() - 1);
			}
		}

		Integer turkeysLayingToday = getMinValue(turkeysReadyToLay, matureMaleTurkeys.size());
		for (int i = 0; i < turkeysLayingToday; i++) {
			animalService.createEggs("turkeys", 2);
			System.out.println("turkey egg laid");
		}

		animalService.setTurkeyEggsLaid(turkeysReadyToLay * 2);
		// ----------------------------------------------------------------------------

//		/////////////////////////ANIMALS EAT/////////////////////////////////////
		animals.clear();
		youngMaleChickens.clear();
		youngFemaleChickens.clear();
		matureMaleChickens.clear();
		matureFemaleChickens.clear();
		oldMaleChickens.clear();
		oldFemaleChickens.clear();
		youngMaleDucks.clear();
		youngFemaleDucks.clear();
		matureMaleDucks.clear();
		matureFemaleDucks.clear();
		oldMaleDucks.clear();
		oldFemaleDucks.clear();
		youngMaleTurkeys.clear();
		youngFemaleTurkeys.clear();
		matureMaleTurkeys.clear();
		matureFemaleTurkeys.clear();
		oldMaleTurkeys.clear();
		oldFemaleTurkeys.clear();

		youngMaleChickens.addAll(animalService.getYoungMaleChickens());
		youngFemaleChickens.addAll(animalService.getYoungFemaleChickens());
		youngMaleDucks.addAll(animalService.getYoungMaleDucks());
		youngFemaleDucks.addAll(animalService.getYoungFemaleDucks());
		youngMaleTurkeys.addAll(animalService.getYoungMaleTurkeys());
		youngFemaleTurkeys.addAll(animalService.getYoungFemaleTurkeys());
		matureMaleChickens.addAll(animalService.getMatureMaleChickens());
		matureFemaleChickens.addAll(animalService.getMatureFemaleChickens());
		matureMaleDucks.addAll(animalService.getMatureMaleDucks());
		matureFemaleDucks.addAll(animalService.getMatureFemaleDucks());
		matureMaleTurkeys.addAll(animalService.getMatureMaleTurkeys());
		matureFemaleTurkeys.addAll(animalService.getMatureFemaleTurkeys());
		oldMaleChickens.addAll(animalService.getOldMaleChickens());
		oldFemaleChickens.addAll(animalService.getOldFemaleChickens());
		oldMaleDucks.addAll(animalService.getOldMaleDucks());
		oldFemaleDucks.addAll(animalService.getOldFemaleDucks());
		oldMaleTurkeys.addAll(animalService.getOldMaleTurkeys());
		oldFemaleTurkeys.addAll(animalService.getOldFemaleTurkeys());

		animals.addAll(youngMaleChickens);
		animals.addAll(youngFemaleChickens);
		animals.addAll(youngMaleDucks);
		animals.addAll(youngFemaleDucks);
		animals.addAll(youngMaleTurkeys);
		animals.addAll(youngFemaleTurkeys);
		animals.addAll(matureMaleChickens);
		animals.addAll(matureFemaleChickens);
		animals.addAll(matureMaleDucks);
		animals.addAll(matureFemaleDucks);
		animals.addAll(matureMaleTurkeys);
		animals.addAll(matureFemaleTurkeys);
		animals.addAll(oldMaleChickens);
		animals.addAll(oldFemaleChickens);
		animals.addAll(oldMaleDucks);
		animals.addAll(oldFemaleDucks);
		animals.addAll(oldMaleTurkeys);
		animals.addAll(oldFemaleTurkeys);

		Integer starvedAnimals = 0;
		Integer farmFood = farmFeignClient.getFarmFood();

		Integer youngMaleChickensNEED = youngMaleChickens.size() * 200;
		if (youngMaleChickensNEED > farmFood) {
			Integer newStarvedAnimals = (youngMaleChickensNEED - farmFood) / 200;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - youngMaleChickensNEED;
		}

		Integer youngFemaleChickensNEED = youngFemaleChickens.size() * 200;
		if (youngFemaleChickensNEED > farmFood) {
			Integer newStarvedAnimals = (youngFemaleChickensNEED - farmFood) / 200;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - youngFemaleChickensNEED;
		}

		Integer youngMaleDucksNEED = youngMaleDucks.size() * 250;
		if (youngMaleDucksNEED > farmFood) {
			Integer newStarvedAnimals = (youngMaleDucksNEED - farmFood) / 250;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - youngMaleDucksNEED;
		}

		Integer youngFemaleDucksNEED = youngFemaleDucks.size() * 250;
		if (youngFemaleDucksNEED > farmFood) {
			Integer newStarvedAnimals = (youngFemaleDucksNEED - farmFood) / 250;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - youngFemaleDucksNEED;
		}

		Integer youngMaleTurkeysNEED = youngMaleTurkeys.size() * 550;
		if (youngMaleTurkeysNEED > farmFood) {
			Integer newStarvedAnimals = (youngMaleTurkeysNEED - farmFood) / 550;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - youngMaleTurkeysNEED;
		}

		Integer youngFemaleTurkeysNEED = youngFemaleTurkeys.size() * 550;
		if (youngFemaleTurkeysNEED > farmFood) {
			Integer newStarvedAnimals = (youngFemaleTurkeysNEED - farmFood) / 550;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - youngFemaleTurkeysNEED;
		}

		Integer matureMaleChickensNEED = matureMaleChickens.size() * 400;
		if (matureMaleChickensNEED > farmFood) {
			Integer newStarvedAnimals = (matureMaleChickensNEED - farmFood) / 400;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - matureMaleChickensNEED;
		}

		Integer matureFemaleChickensNEED = matureFemaleChickens.size() * 350;
		if (matureFemaleChickensNEED > farmFood) {
			Integer newStarvedAnimals = (matureFemaleChickensNEED - farmFood) / 350;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - matureFemaleChickensNEED;
		}

		Integer matureMaleDucksNEED = matureMaleDucks.size() * 500;
		if (matureMaleDucksNEED > farmFood) {
			Integer newStarvedAnimals = (matureMaleDucksNEED - farmFood) / 500;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - matureMaleDucksNEED;
		}

		Integer matureFemaleDucksNEED = matureFemaleDucks.size() * 450;
		if (matureFemaleDucksNEED > farmFood) {
			Integer newStarvedAnimals = (matureFemaleDucksNEED - farmFood) / 450;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - matureFemaleDucksNEED;
		}

		Integer matureMaleTurkeysNEED = matureMaleTurkeys.size() * 650;
		if (matureMaleTurkeysNEED > farmFood) {
			Integer newStarvedAnimals = (matureMaleTurkeysNEED - farmFood) / 650;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - matureMaleTurkeysNEED;
		}

		Integer matureFemaleTurkeysNEED = matureFemaleTurkeys.size() * 600;
		if (matureFemaleTurkeysNEED > farmFood) {
			Integer newStarvedAnimals = (matureFemaleTurkeysNEED - farmFood) / 600;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - matureFemaleTurkeysNEED;
		}

		Integer oldMaleChickensNEED = oldMaleChickens.size() * 200;
		if (oldMaleChickensNEED > farmFood) {
			Integer newStarvedAnimals = (oldMaleChickensNEED - farmFood) / 200;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - oldMaleChickensNEED;
		}

		Integer oldFemaleChickensNEED = oldFemaleChickens.size() * 200;
		if (oldFemaleChickensNEED > farmFood) {
			Integer newStarvedAnimals = (oldFemaleChickensNEED - farmFood) / 200;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - oldFemaleChickensNEED;
		}

		Integer oldMaleDucksNEED = oldMaleDucks.size() * 250;
		if (oldMaleDucksNEED > farmFood) {
			Integer newStarvedAnimals = (oldMaleDucksNEED - farmFood) / 250;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - oldMaleDucksNEED;
		}

		Integer oldFemaleDucksNEED = oldFemaleDucks.size() * 250;
		if (oldFemaleDucksNEED > farmFood) {
			Integer newStarvedAnimals = (oldFemaleDucksNEED - farmFood) / 250;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - oldFemaleDucksNEED;
		}

		Integer oldMaleTurkeysNEED = oldMaleTurkeys.size() * 350;
		if (oldMaleTurkeysNEED > farmFood) {
			Integer newStarvedAnimals = (oldMaleTurkeysNEED - farmFood) / 350;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - oldMaleTurkeysNEED;
		}

		Integer oldFemaleTurkeysNEED = oldFemaleTurkeys.size() * 350;
		if (oldFemaleTurkeysNEED > farmFood) {
			Integer newStarvedAnimals = (oldFemaleTurkeysNEED - farmFood) / 350;
			starvedAnimals = starvedAnimals + newStarvedAnimals;
			farmFood = 0;
		} else {
			farmFood = farmFood - oldFemaleTurkeysNEED;
		}

		for (int i = 0; i < starvedAnimals; i++) {
			Integer randomPosition = randomNumberInRange(0, animals.size() - 1);
			Animal starvedAnimal = animals.get(randomPosition);
			animalService.deleteAnimalByEntity(starvedAnimal);
			animals.remove(starvedAnimal);
			System.out.println(starvedAnimal.getSpecies() + " starved");
		}

		farmFeignClient.setFarmFood(farmFood);
		System.out.println("Food after animnals eat: " + farmFood);

		System.out.println(animalService.getChickensHatched());
		
		farmFeignClient.updateFarm();

	}
}
