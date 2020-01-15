package com.formacionbdi.springboot.app.animals.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.animals.models.Animal;
import com.formacionbdi.springboot.app.animals.repository.AnimalsRepository;

@Service
public class AnimalServiceImpl implements AnimalService {

	@Autowired
	private AnimalsRepository animalsRepository;

	// ------------FARM RELATED------------------

	@Override
	public void StartUp() {
		Integer size = animalsRepository.findAll().size();
		for (int i = 0; i < size; i++) {
			Animal animal = animalsRepository.findAll().get(i);
			animals.add(animal);
			System.out.println(animal.getSpecies() + " " + animal.getSex() + " added");
		}
	}

	@Override
	public void deleteAll() {
		animalsRepository.deleteAll();
		animals.clear();
		animalsEggs.clear();

		matureMaleChickens.clear();
		matureFemaleChickens.clear();
		matureMaleDucks.clear();
		matureFemaleDucks.clear();
		matureMaleTurkeys.clear();
		matureFemaleTurkeys.clear();

		youngMaleChickens.clear();
		youngMaleDucks.clear();
		youngMaleTurkeys.clear();

		oldMaleChickens.clear();
		oldMaleDucks.clear();
		oldMaleTurkeys.clear();

		youngFemaleChickens.clear();
		youngFemaleDucks.clear();
		youngFemaleTurkeys.clear();

		oldFemaleChickens.clear();
		oldFemaleDucks.clear();
		oldFemaleTurkeys.clear();

	}

	@Override
	public List<Integer> getUpdateList() {
		List<Integer> list = new ArrayList<>();
		list.add(youngMaleChickens.size());
		list.add(youngFemaleChickens.size());
		list.add(matureMaleChickens.size());
		list.add(matureFemaleChickens.size());
		list.add(oldMaleChickens.size());
		list.add(oldFemaleChickens.size());

		list.add(youngMaleDucks.size());
		list.add(youngFemaleDucks.size());
		list.add(matureMaleDucks.size());
		list.add(matureFemaleDucks.size());
		list.add(oldMaleDucks.size());
		list.add(oldFemaleDucks.size());

		list.add(youngMaleTurkeys.size());
		list.add(youngFemaleTurkeys.size());
		list.add(matureMaleTurkeys.size());
		list.add(matureFemaleTurkeys.size());
		list.add(oldMaleTurkeys.size());
		list.add(oldFemaleTurkeys.size());

		return list;
	}

	@Override
	public List<Integer> getEggsLaidList() {
		List<Integer> list = new ArrayList<>();
		list.add(chickenEggsLaid);
		list.add(duckEggsLaid);
		list.add(turkeyEggsLaid);
		return list;
	}

	@Override
	public List<Integer> getEggsHatchedList() {
		List<Integer> list = new ArrayList<>();
		list.add(chickensHatched);
		list.add(ducksHatched);
		list.add(turkeysHatched);
		return list;
	}

	@Override
	public List<Integer> getDeadAnimals() {
		List<Integer> list = new ArrayList<>();
		list.add(deadChickens);
		list.add(deadDucks);
		list.add(deadTurkeys);
		return list;
	}

	@Override
	public List<Integer> getAnimalsBought() {
		List<Integer> list = new ArrayList<>();
		list.add(chickensBought);
		list.add(ducksBought);
		list.add(turkeysBought);
		return list;
	}

	@Override
	public List<Integer> getAnimalsSold() {
		// TODO Auto-generated method stub
		return null;
	}

	// -----------ANIMALS RELATED-------------

	@Override
	@Transactional
	public void createAnimals(String species, String maturity, String sex, Integer amount) {

		Integer lifespan = 0;
		Integer gramsOfFood = 0;

		switch (species) {
		case "chickens":
			switch (maturity) {
			case "young":
				switch (sex) {
				case "male":
					gramsOfFood = 200;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(4, 8);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						chickensBought++;
						youngMaleChickens.add(newAnimal);
					}
					break;
				case "female":
					gramsOfFood = 200;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(4, 8);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						chickensBought++;
						youngFemaleChickens.add(newAnimal);
					}
					break;
				}
				break;
			case "mature":
				switch (sex) {
				case "male":
					gramsOfFood = 400;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(30, 35);
						Animal newAnimal = new Animal(species, maturity, sex, 2, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						chickensBought++;
						matureMaleChickens.add(newAnimal);
					}
					break;
				case "female":
					lifespan = 25;
					gramsOfFood = 350;
					for (int i = 0; i < amount; i++) {
						Animal newAnimal = new Animal(species, maturity, sex, 2, gramsOfFood);
						animalsRepository.save(newAnimal);
						newAnimal.setEggsPerDay(3);
						newAnimal.setDaysToLayEggs(0);
						animals.add(newAnimal);
						chickensBought++;
						matureFemaleChickens.add(newAnimal);
					}
					break;
				}
				break;
			case "old":
				switch (sex) {
				case "male":
					gramsOfFood = 200;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(10, 11);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						chickensBought++;
						oldMaleChickens.add(newAnimal);
					}
					break;
				case "female":
					gramsOfFood = 200;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(10, 11);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						chickensBought++;
						oldFemaleChickens.add(newAnimal);
					}
					break;
				}
				break;
			}
			break;

		case "ducks":
			switch (maturity) {
			case "young":
				switch (sex) {
				case "male":
					gramsOfFood = 250;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(6, 9);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						ducksBought++;
						youngMaleDucks.add(newAnimal);
					}
					break;
				case "female":
					gramsOfFood = 250;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(6, 9);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						ducksBought++;
						youngFemaleDucks.add(newAnimal);
					}
					break;
				}
				break;
			case "mature":
				switch (sex) {
				case "male":
					gramsOfFood = 500;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(37, 40);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						ducksBought++;
						matureMaleDucks.add(newAnimal);
					}
					break;
				case "female":
					lifespan = 30;
					gramsOfFood = 450;
					for (int i = 0; i < amount; i++) {
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						newAnimal.setEggsPerDay(1);
						newAnimal.setDaysToLayEggs(2);
						animals.add(newAnimal);
						ducksBought++;
						matureFemaleDucks.add(newAnimal);
					}
					break;
				}
				break;
			case "old":
				switch (sex) {
				case "male":
					gramsOfFood = 250;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(6, 9);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						ducksBought++;
						oldMaleDucks.add(newAnimal);
					}
					break;
				case "female":
					gramsOfFood = 250;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(6, 9);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						ducksBought++;
						oldFemaleDucks.add(newAnimal);
					}
					break;
				}
				break;
			}
			break;

		case "turkeys":
			switch (maturity) {
			case "young":
				switch (sex) {
				case "male":
					gramsOfFood = 550;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(10, 12);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						turkeysBought++;
						youngMaleTurkeys.add(newAnimal);
					}
					break;
				case "female":
					gramsOfFood = 550;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(10, 12);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						youngFemaleTurkeys.add(newAnimal);
					}
					break;
				}
				break;
			case "mature":
				switch (sex) {
				case "male":
					gramsOfFood = 650;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(46, 50);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						turkeysBought++;
						matureMaleTurkeys.add(newAnimal);
					}
					break;
				case "female":
					lifespan = 45;
					gramsOfFood = 600;
					for (int i = 0; i < amount; i++) {
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						newAnimal.setEggsPerDay(2);
						newAnimal.setDaysToLayEggs(8);
						animals.add(newAnimal);
						turkeysBought++;
						matureFemaleTurkeys.add(newAnimal);
					}
					break;
				}
				break;
			case "old":
				switch (sex) {
				case "male":
					gramsOfFood = 350;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(30, 33);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						turkeysBought++;
						oldMaleTurkeys.add(newAnimal);
					}
					break;
				case "female":
					gramsOfFood = 350;
					for (int i = 0; i < amount; i++) {
						lifespan = randomNumberInRange(22, 25);
						Animal newAnimal = new Animal(species, maturity, sex, lifespan, gramsOfFood);
						animalsRepository.save(newAnimal);
						animals.add(newAnimal);
						turkeysBought++;
						oldFemaleTurkeys.add(newAnimal);
					}
					break;
				}
				break;
			}
			break;
		}
	}

	@Override
	public void createEggs(String species, Integer amount) {
		switch (species) {
		case "chickens":
			for (int i = 0; i < amount; i++) {
				Animal newChickenEgg = new Animal(species, "eggs", "unknown", randomNumberInRange(2, 5), 0);
				animalsRepository.save(newChickenEgg);
				animalsEggs.add(newChickenEgg);
				chickensBought++;
			}
			break;

		case "ducks":
			for (int i = 0; i < amount; i++) {
				Animal newDuckEgg = new Animal(species, "eggs", "unknown", randomNumberInRange(5, 7), 0);
				animalsRepository.save(newDuckEgg);
				animalsEggs.add(newDuckEgg);
				ducksBought++;
			}
			break;

		case "turkeys":
			for (int i = 0; i < amount; i++) {
				Animal newTurkeyEgg = new Animal(species, "eggs", "unkown", randomNumberInRange(8, 10), 0);
				animalsRepository.save(newTurkeyEgg);
				animalsEggs.add(newTurkeyEgg);
				turkeysBought++;
			}
			break;
		}
	}

	@Override
	@Transactional
	public void deleteAnimals(String species, String maturity, String sex, Integer amount) throws Exception {

		switch (species) {
		case "chickens":
			switch (maturity) {
			case "young":
				switch (sex) {
				case "male":
					if (youngMaleChickens.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal chickenToDelete = youngMaleChickens.stream().findFirst().get();
							youngMaleChickens.remove(chickenToDelete);
							animalsRepository.delete(chickenToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;

				case "female":
					if (youngFemaleChickens.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal chickenToDelete = youngFemaleChickens.stream().findFirst().get();
							youngFemaleChickens.remove(chickenToDelete);
							animalsRepository.delete(chickenToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;
				}
				break;

			case "mature":
				switch (sex) {
				case "male":
					if (matureMaleChickens.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal chickenToDelete = matureMaleChickens.stream().findFirst().get();
							matureMaleChickens.remove(chickenToDelete);
							animalsRepository.delete(chickenToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;

				case "female":
					if (matureFemaleChickens.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal chickenToDelete = matureFemaleChickens.stream().findFirst().get();
							matureFemaleChickens.remove(chickenToDelete);
							animalsRepository.delete(chickenToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;
				}
				break;

			case "old":
				switch (sex) {
				case "male":
					if (oldMaleChickens.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal chickenToDelete = oldMaleChickens.stream().findFirst().get();
							oldMaleChickens.remove(chickenToDelete);
							animalsRepository.delete(chickenToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;

				case "female":
					if (oldFemaleChickens.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal chickenToDelete = oldFemaleChickens.stream().findFirst().get();
							oldFemaleChickens.remove(chickenToDelete);
							animalsRepository.delete(chickenToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;
				}
				break;
			}

			break;

		case "ducks":
			switch (maturity) {
			case "young":
				switch (sex) {
				case "male":
					if (youngMaleDucks.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal duckToDelete = youngMaleDucks.stream().findFirst().get();
							youngMaleDucks.remove(duckToDelete);
							animalsRepository.delete(duckToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;

				case "female":
					if (youngFemaleDucks.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal duckToDelete = youngFemaleDucks.stream().findFirst().get();
							youngFemaleDucks.remove(duckToDelete);
							animalsRepository.delete(duckToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;
				}
				break;

			case "mature":
				switch (sex) {
				case "male":
					if (matureMaleDucks.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal duckToDelete = matureMaleDucks.stream().findFirst().get();
							matureMaleDucks.remove(duckToDelete);
							animalsRepository.delete(duckToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;

				case "female":
					if (matureFemaleDucks.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal duckToDelete = matureFemaleDucks.stream().findFirst().get();
							matureFemaleDucks.remove(duckToDelete);
							animalsRepository.delete(duckToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;
				}
				break;

			case "old":
				switch (sex) {
				case "male":
					if (oldMaleDucks.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal duckToDelete = oldMaleDucks.stream().findFirst().get();
							oldMaleDucks.remove(duckToDelete);
							animalsRepository.delete(duckToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;

				case "female":
					if (oldFemaleDucks.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal duckToDelete = oldFemaleDucks.stream().findFirst().get();
							oldFemaleDucks.remove(duckToDelete);
							animalsRepository.delete(duckToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;
				}
				break;
			}
			break;

		case "turkeys":
			switch (maturity) {
			case "young":
				switch (sex) {
				case "male":
					if (youngMaleTurkeys.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal turkeyToDelete = youngMaleTurkeys.stream().findFirst().get();
							youngMaleTurkeys.remove(turkeyToDelete);
							animalsRepository.delete(turkeyToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;

				case "female":
					if (youngFemaleTurkeys.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal turkeyToDelete = youngFemaleTurkeys.stream().findFirst().get();
							youngFemaleTurkeys.remove(turkeyToDelete);
							animalsRepository.delete(turkeyToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;
				}
				break;

			case "mature":
				switch (sex) {
				case "male":
					if (matureMaleTurkeys.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal turkeyToDelete = matureMaleTurkeys.stream().findFirst().get();
							matureMaleTurkeys.remove(turkeyToDelete);
							animalsRepository.delete(turkeyToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;

				case "female":
					if (matureFemaleTurkeys.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal turkeyToDelete = matureFemaleTurkeys.stream().findFirst().get();
							matureFemaleTurkeys.remove(turkeyToDelete);
							animalsRepository.delete(turkeyToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;
				}
				break;

			case "old":
				switch (sex) {
				case "male":
					if (oldMaleTurkeys.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal turkeyToDelete = oldMaleTurkeys.stream().findFirst().get();
							oldMaleTurkeys.remove(turkeyToDelete);
							animalsRepository.delete(turkeyToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;

				case "female":
					if (oldFemaleTurkeys.size() >= amount) {
						for (int i = 0; i < amount; i++) {
							Animal turkeyToDelete = oldFemaleTurkeys.stream().findFirst().get();
							oldFemaleTurkeys.remove(turkeyToDelete);
							animalsRepository.delete(turkeyToDelete);
						}
					} else {
						throw new Exception("error");
					}
					break;
				}
				break;
			}
			break;
		}

	}

	@Override
	@Transactional
	public void deleteAnimalByEntity(Animal animal) {
		animalsRepository.delete(animal);
		animals.remove(animal);

		switch (animal.getSpecies()) {
		case "chickens":
			switch (animal.getMaturity()) {
			case "young":
				switch (animal.getSex()) {
				case "male":
					youngMaleChickens.remove(animal);
					break;
				case "female":
					youngFemaleChickens.remove(animal);
					break;
				}
				break;
			case "mature":
				switch (animal.getSex()) {
				case "male":
					matureMaleChickens.remove(animal);
					break;
				case "female":
					matureFemaleChickens.remove(animal);
					break;
				}
				break;
			case "old":
				switch (animal.getSex()) {
				case "male":
					oldMaleChickens.remove(animal);
					break;
				case "female":
					oldFemaleChickens.remove(animal);
					break;
				}
				break;
			}
			break;
		case "ducks":
			switch (animal.getMaturity()) {
			case "young":
				switch (animal.getSex()) {
				case "male":
					youngMaleDucks.remove(animal);
					break;
				case "female":
					youngFemaleDucks.remove(animal);
					break;
				}
				break;
			case "mature":
				switch (animal.getSex()) {
				case "male":
					matureMaleDucks.remove(animal);
					break;
				case "female":
					matureFemaleDucks.remove(animal);
					break;
				}
				break;
			case "old":
				switch (animal.getSex()) {
				case "male":
					oldMaleDucks.remove(animal);
					break;
				case "female":
					oldFemaleDucks.remove(animal);
					break;
				}
				break;
			}
			break;
		case "turkeys":
			switch (animal.getMaturity()) {
			case "young":
				switch (animal.getSex()) {
				case "male":
					youngMaleTurkeys.remove(animal);
					break;
				case "female":
					youngFemaleTurkeys.remove(animal);
					break;
				}
				break;
			case "mature":
				switch (animal.getSex()) {
				case "male":
					matureMaleTurkeys.remove(animal);
					break;
				case "female":
					matureFemaleTurkeys.remove(animal);
					break;
				}
				break;
			case "old":
				switch (animal.getSex()) {
				case "male":
					oldMaleTurkeys.remove(animal);
					break;
				case "female":
					oldFemaleTurkeys.remove(animal);
					break;
				}
				break;
			}
			break;
		}

	}

	@Override
	@Transactional
	public void deleteEggs(String species, Integer amount) throws Exception {
		switch (species) {
		case "chickens":

			List<Animal> farmChickenEggs = new ArrayList<>();
			farmChickenEggs.addAll(
					animalsEggs.stream().filter(x -> x.getSpecies().equals("chickens")).collect(Collectors.toList()));
			Integer chickenEggsSize = farmChickenEggs.size();
			if ((chickenEggsSize >= amount) && (chickenEggsSize != 0)) {
				for (int i = 0; i < amount; i++) {
					Animal eggToDelete = farmChickenEggs.stream().findFirst().get();
					farmChickenEggs.remove(eggToDelete);
					animalsEggs.remove(eggToDelete);
					animalsRepository.delete(eggToDelete);
				}
				System.out.println("CHICKENS EGGS SIZE: " + farmChickenEggs.size());

			} else if (chickenEggsSize == 0) {
				throw new Exception("error");
			} else {
				throw new Exception("error");
			}
			break;

		case "ducks":

			List<Animal> farmDuckEggs = new ArrayList<>();
			farmDuckEggs.addAll(
					animalsEggs.stream().filter(x -> x.getSpecies().equals("ducks")).collect(Collectors.toList()));

			Integer duckEggsSize = farmDuckEggs.size();
			if (duckEggsSize >= amount && duckEggsSize != 0) {
				for (int i = 0; i < amount; i++) {
					Animal eggToDelete = farmDuckEggs.stream().findFirst().get();
					farmDuckEggs.remove(eggToDelete);
					animalsEggs.remove(eggToDelete);
					animalsRepository.delete(eggToDelete);
				}
			} else if (duckEggsSize == 0) {
				throw new Exception("error");
			} else {
				throw new Exception("error");
			}
			break;

		case "turkeys":

			List<Animal> farmTurkeyEggs = new ArrayList<>();
			farmTurkeyEggs.addAll(
					animalsEggs.stream().filter(x -> x.getSpecies().equals("turkeys")).collect(Collectors.toList()));

			Integer turkeyEggsSize = farmTurkeyEggs.size();
			if (turkeyEggsSize >= amount && turkeyEggsSize != 0) {
				for (int i = 0; i < amount; i++) {
					Animal eggToDelete = farmTurkeyEggs.get(i);
					animalsEggs.remove(eggToDelete);
					animalsRepository.delete(eggToDelete);
				}
			} else if (turkeyEggsSize == 0) {
				throw new Exception("error");
			} else {
				throw new Exception("error");
			}
			break;
		}

	}

	// -------------GETTERS AND SETTERS---------------

	private static Integer randomNumberInRange(Integer min, Integer max) {
		Random random = new Random();

		return random.ints(min, (max + 1)).limit(1).findFirst().getAsInt();
	}

	public List<Animal> animalsEggs = new ArrayList<>();
	public List<Animal> animals = new ArrayList<>();
	public List<Animal> matureMaleChickens = new ArrayList<>();
	public List<Animal> matureFemaleChickens = new ArrayList<>();
	public List<Animal> matureMaleDucks = new ArrayList<>();
	public List<Animal> matureFemaleDucks = new ArrayList<>();
	public List<Animal> matureMaleTurkeys = new ArrayList<>();
	public List<Animal> matureFemaleTurkeys = new ArrayList<>();

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

	public Integer chickensBought = 0;
	public Integer ducksBought = 0;
	public Integer turkeysBought = 0;

	public Integer chickensSold = 0;
	public Integer ducksSold = 0;
	public Integer turkeysSold = 0;

	public Integer deadChickens = 0;
	public Integer deadDucks = 0;
	public Integer deadTurkeys = 0;

	public Integer chickensHatched = 0;
	public Integer ducksHatched = 0;
	public Integer turkeysHatched = 0;

	public Integer chickenEggsLaid = 0;
	public Integer duckEggsLaid = 0;
	public Integer turkeyEggsLaid = 0;

	public Integer getChickensBought() {
		return chickensBought;
	}

	public Integer getChickensSold() {
		return chickensSold;
	}

	public void setChickensSold(Integer chickensSold) {
		this.chickensSold = chickensSold;
	}

	public Integer getDucksSold() {
		return ducksSold;
	}

	public void setDucksSold(Integer ducksSold) {
		this.ducksSold = ducksSold;
	}

	public Integer getTurkeysSold() {
		return turkeysSold;
	}

	public void setTurkeysSold(Integer turkeysSold) {
		this.turkeysSold = turkeysSold;
	}

	public void setChickensBought(Integer chickensBought) {
		this.chickensBought = chickensBought;
	}

	public Integer getDucksBought() {
		return ducksBought;
	}

	public void setDucksBought(Integer ducksBought) {
		this.ducksBought = ducksBought;
	}

	public Integer getTurkeysBought() {
		return turkeysBought;
	}

	public void setTurkeysBought(Integer turkeysBought) {
		this.turkeysBought = turkeysBought;
	}

	public Integer getDeadChickens() {
		return deadChickens;
	}

	public void setDeadChickens(Integer deadChickens) {
		this.deadChickens = deadChickens;
	}

	public Integer getDeadDucks() {
		return deadDucks;
	}

	public void setDeadDucks(Integer deadDucks) {
		this.deadDucks = deadDucks;
	}

	public Integer getDeadTurkeys() {
		return deadTurkeys;
	}

	public void setDeadTurkeys(Integer deadTurkeys) {
		this.deadTurkeys = deadTurkeys;
	}

	public Integer getChickenEggsLaid() {
		return chickenEggsLaid;
	}

	public void setChickenEggsLaid(Integer chickenEggsLaid) {
		this.chickenEggsLaid = chickenEggsLaid;
	}

	public Integer getDuckEggsLaid() {
		return duckEggsLaid;
	}

	public void setDuckEggsLaid(Integer duckEggsLaid) {
		this.duckEggsLaid = duckEggsLaid;
	}

	public Integer getTurkeyEggsLaid() {
		return turkeyEggsLaid;
	}

	public void setTurkeyEggsLaid(Integer turkeyEggsLaid) {
		this.turkeyEggsLaid = turkeyEggsLaid;
	}

	public List<Animal> getAnimalsEggs() {
		return animalsEggs;
	}

	public List<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	public List<Animal> getMatureMaleChickens() {
		return matureMaleChickens;
	}

	public void setMatureMaleChickens(List<Animal> matureMaleChickens) {
		this.matureMaleChickens = matureMaleChickens;
	}

	public List<Animal> getMatureFemaleChickens() {
		return matureFemaleChickens;
	}

	public void setMatureFemaleChickens(List<Animal> matureFemaleChickens) {
		this.matureFemaleChickens = matureFemaleChickens;
	}

	public List<Animal> getMatureMaleDucks() {
		return matureMaleDucks;
	}

	public void setMatureMaleDucks(List<Animal> matureMaleDucks) {
		this.matureMaleDucks = matureMaleDucks;
	}

	public List<Animal> getMatureFemaleDucks() {
		return matureFemaleDucks;
	}

	public void setMatureFemaleDucks(List<Animal> matureFemaleDucks) {
		this.matureFemaleDucks = matureFemaleDucks;
	}

	public List<Animal> getMatureMaleTurkeys() {
		return matureMaleTurkeys;
	}

	public void setMatureMaleTurkeys(List<Animal> matureMaleTurkeys) {
		this.matureMaleTurkeys = matureMaleTurkeys;
	}

	public List<Animal> getMatureFemaleTurkeys() {
		return matureFemaleTurkeys;
	}

	public void setMatureFemaleTurkeys(List<Animal> matureFemaleTurkeys) {
		this.matureFemaleTurkeys = matureFemaleTurkeys;
	}

	public List<Animal> getYoungMaleChickens() {
		return youngMaleChickens;
	}

	public void setYoungMaleChickens(List<Animal> youngMaleChickens) {
		this.youngMaleChickens = youngMaleChickens;
	}

	public List<Animal> getYoungMaleDucks() {
		return youngMaleDucks;
	}

	public void setYoungMaleDucks(List<Animal> youngMaleDucks) {
		this.youngMaleDucks = youngMaleDucks;
	}

	public List<Animal> getYoungMaleTurkeys() {
		return youngMaleTurkeys;
	}

	public void setYoungMaleTurkeys(List<Animal> youngMaleTurkeys) {
		this.youngMaleTurkeys = youngMaleTurkeys;
	}

	public List<Animal> getYoungFemaleChickens() {
		return youngFemaleChickens;
	}

	public void setYoungFemaleChickens(List<Animal> youngFemaleChickens) {
		this.youngFemaleChickens = youngFemaleChickens;
	}

	public List<Animal> getYoungFemaleDucks() {
		return youngFemaleDucks;
	}

	public void setYoungFemaleDucks(List<Animal> youngFemaleDucks) {
		this.youngFemaleDucks = youngFemaleDucks;
	}

	public List<Animal> getYoungFemaleTurkeys() {
		return youngFemaleTurkeys;
	}

	public void setYoungFemaleTurkeys(List<Animal> youngFemaleTurkeys) {
		this.youngFemaleTurkeys = youngFemaleTurkeys;
	}

	public List<Animal> getOldMaleChickens() {
		return oldMaleChickens;
	}

	public void setOldMaleChickens(List<Animal> oldMaleChickens) {
		this.oldMaleChickens = oldMaleChickens;
	}

	public List<Animal> getOldMaleDucks() {
		return oldMaleDucks;
	}

	public void setOldMaleDucks(List<Animal> oldMaleDucks) {
		this.oldMaleDucks = oldMaleDucks;
	}

	public List<Animal> getOldMaleTurkeys() {
		return oldMaleTurkeys;
	}

	public void setOldMaleTurkeys(List<Animal> oldMaleTurkeys) {
		this.oldMaleTurkeys = oldMaleTurkeys;
	}

	public List<Animal> getOldFemaleChickens() {
		return oldFemaleChickens;
	}

	public void setOldFemaleChickens(List<Animal> oldFemaleChickens) {
		this.oldFemaleChickens = oldFemaleChickens;
	}

	public List<Animal> getOldFemaleDucks() {
		return oldFemaleDucks;
	}

	public void setOldFemaleDucks(List<Animal> oldFemaleDucks) {
		this.oldFemaleDucks = oldFemaleDucks;
	}

	public List<Animal> getOldFemaleTurkeys() {
		return oldFemaleTurkeys;
	}

	public void setOldFemaleTurkeys(List<Animal> oldFemaleTurkeys) {
		this.oldFemaleTurkeys = oldFemaleTurkeys;
	}

	public Integer getChickensHatched() {
		return chickensHatched;
	}

	public void setChickensHatched(Integer chickensHatched) {
		this.chickensHatched = chickensHatched;
	}

	public Integer getDucksHatched() {
		return ducksHatched;
	}

	public void setDucksHatched(Integer ducksHatched) {
		this.ducksHatched = ducksHatched;
	}

	public Integer getTurkeysHatched() {
		return turkeysHatched;
	}

	public void setTurkeysHatched(Integer turkeysHatched) {
		this.turkeysHatched = turkeysHatched;
	}

	// ----------------------------------------------------------------------//

}