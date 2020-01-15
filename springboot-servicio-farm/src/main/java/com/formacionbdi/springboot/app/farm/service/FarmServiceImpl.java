package com.formacionbdi.springboot.app.farm.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.type.descriptor.sql.JdbcTypeFamilyInformation.Family;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.formacionbdi.springboot.app.farm.clients.AnimalsFeignClient;
import com.formacionbdi.springboot.app.farm.clients.MarketFeignClient;
import com.formacionbdi.springboot.app.farm.config.FarmConfigReader;
import com.formacionbdi.springboot.app.farm.models.entity.Farm;
import com.formacionbdi.springboot.app.farm.repository.FarmRepository;
import com.netflix.client.http.HttpResponse;

@Service
public class FarmServiceImpl implements FarmService {

	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private AnimalsFeignClient animalsFeignClient;

	private List<Integer> animalsSold = Arrays.asList(0, 0, 0);

	private List<Integer> animalsBought = Arrays.asList(0, 0, 0);

	// -----------------------create farm----------------------------------

	@Override
	public ResponseEntity<Map<String, Object>> createFarm(String name) {
		Map<String, Object> map = new HashMap<>();

		Long farmsTotal = farmRepository.count();
		if (farmsTotal >= 1) {
			map.put("ERROR", "¡You already have a farm! Delete if first.");
		} else {

			FarmConfigReader farmConfig = new FarmConfigReader();
			farmConfig.OpenFarmConfig();
			Double initialMoney = farmConfig.ReadFarmConfig();
			farmConfig.CloseFarmConfig();

			Farm farm = new Farm(name, initialMoney);
			farm.setStartDate(LocalDate.now());

			farmRepository.save(farm);

			String info = "¡You've created you farm, welcome to: " + name + "!";
			map.put("SUCCESS", info);
		}
		return new ResponseEntity<>(map, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<Map<String, Object>> deleteFarm() {
		Map<String, Object> map = new HashMap<>();
		if (farmRepository.count() != 0) {
			farmRepository.deleteAll();
			animalsFeignClient.deleteAll();
			map.put("SUCCESS", "¡You've deleted your farm, go create a new one!");
		} else {
			map.put("ERROR", "You have no farm yet to delete");
		}
		return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
	}

	@Override
	@Transactional
	public void updateFarm() {
		Farm farm = farmRepository.findAll().stream().findFirst().get();
		List<Integer> updateList = animalsFeignClient.getUpdateList();

		List<Integer> eggsLaidUpdateList = animalsFeignClient.getEggsLaidList();

		List<Integer> eggsHatchedUpdateList = animalsFeignClient.getEggsHatchedList();

		List<Integer> deadAnimalsUpdateList = animalsFeignClient.getDeadAnimals();

		farm.getChickensSold().set(0, farm.getChickensSold().get(1));
		farm.getChickensSold().set(1, farm.getChickensSold().get(2));
		farm.getChickensSold().set(2, farm.getChickensSold().get(3));
		farm.getChickensSold().set(3, farm.getChickensSold().get(4));
		farm.getChickensSold().set(4, farm.getChickensSold().get(5));
		farm.getChickensSold().set(5, farm.getChickensSold().get(6));
		farm.getChickensSold().set(6, animalsSold.get(0));

		farm.getDucksSold().set(0, farm.getDucksSold().get(1));
		farm.getDucksSold().set(1, farm.getDucksSold().get(2));
		farm.getDucksSold().set(2, farm.getDucksSold().get(3));
		farm.getDucksSold().set(3, farm.getDucksSold().get(4));
		farm.getDucksSold().set(4, farm.getDucksSold().get(5));
		farm.getDucksSold().set(5, farm.getDucksSold().get(6));
		farm.getDucksSold().set(6, animalsSold.get(1));

		farm.getTurkeysSold().set(0, farm.getTurkeysSold().get(1));
		farm.getTurkeysSold().set(1, farm.getTurkeysSold().get(2));
		farm.getTurkeysSold().set(2, farm.getTurkeysSold().get(3));
		farm.getTurkeysSold().set(3, farm.getTurkeysSold().get(4));
		farm.getTurkeysSold().set(4, farm.getTurkeysSold().get(5));
		farm.getTurkeysSold().set(5, farm.getTurkeysSold().get(6));
		farm.getTurkeysSold().set(6, animalsSold.get(2));

		farm.getChickensBought().set(0, farm.getChickensBought().get(1));
		farm.getChickensBought().set(1, farm.getChickensBought().get(2));
		farm.getChickensBought().set(2, farm.getChickensBought().get(3));
		farm.getChickensBought().set(3, farm.getChickensBought().get(4));
		farm.getChickensBought().set(4, farm.getChickensBought().get(5));
		farm.getChickensBought().set(5, farm.getChickensBought().get(6));
		farm.getChickensBought().set(6, animalsBought.get(0));

		farm.getDucksBought().set(0, farm.getDucksBought().get(1));
		farm.getDucksBought().set(1, farm.getDucksBought().get(2));
		farm.getDucksBought().set(2, farm.getDucksBought().get(3));
		farm.getDucksBought().set(3, farm.getDucksBought().get(4));
		farm.getDucksBought().set(4, farm.getDucksBought().get(5));
		farm.getDucksBought().set(5, farm.getDucksBought().get(6));
		farm.getDucksBought().set(6, animalsBought.get(1));

		farm.getTurkeysBought().set(0, farm.getTurkeysBought().get(1));
		farm.getTurkeysBought().set(1, farm.getTurkeysBought().get(2));
		farm.getTurkeysBought().set(2, farm.getTurkeysBought().get(3));
		farm.getTurkeysBought().set(3, farm.getTurkeysBought().get(4));
		farm.getTurkeysBought().set(4, farm.getTurkeysBought().get(5));
		farm.getTurkeysBought().set(5, farm.getTurkeysBought().get(6));
		farm.getTurkeysBought().set(6, animalsBought.get(2));

		farm.getChickensHatched().set(0, farm.getChickensHatched().get(1));
		farm.getChickensHatched().set(1, farm.getChickensHatched().get(2));
		farm.getChickensHatched().set(2, farm.getChickensHatched().get(3));
		farm.getChickensHatched().set(3, farm.getChickensHatched().get(4));
		farm.getChickensHatched().set(4, farm.getChickensHatched().get(5));
		farm.getChickensHatched().set(5, farm.getChickensHatched().get(6));
		farm.getChickensHatched().set(6, eggsHatchedUpdateList.get(0));

		farm.getDucksHatched().set(0, farm.getDucksHatched().get(1));
		farm.getDucksHatched().set(1, farm.getDucksHatched().get(2));
		farm.getDucksHatched().set(2, farm.getDucksHatched().get(3));
		farm.getDucksHatched().set(3, farm.getDucksHatched().get(4));
		farm.getDucksHatched().set(4, farm.getDucksHatched().get(5));
		farm.getDucksHatched().set(5, farm.getDucksHatched().get(6));
		farm.getDucksHatched().set(6, eggsHatchedUpdateList.get(1));

		farm.getTurkeysHatched().set(0, farm.getTurkeysHatched().get(1));
		farm.getTurkeysHatched().set(1, farm.getTurkeysHatched().get(2));
		farm.getTurkeysHatched().set(2, farm.getTurkeysHatched().get(3));
		farm.getTurkeysHatched().set(3, farm.getTurkeysHatched().get(4));
		farm.getTurkeysHatched().set(4, farm.getTurkeysHatched().get(5));
		farm.getTurkeysHatched().set(5, farm.getTurkeysHatched().get(6));
		farm.getTurkeysHatched().set(6, eggsHatchedUpdateList.get(2));

		farm.getChickenEggsLaid().set(0, farm.getChickenEggsLaid().get(1));
		farm.getChickenEggsLaid().set(1, farm.getChickenEggsLaid().get(2));
		farm.getChickenEggsLaid().set(2, farm.getChickenEggsLaid().get(3));
		farm.getChickenEggsLaid().set(3, farm.getChickenEggsLaid().get(4));
		farm.getChickenEggsLaid().set(4, farm.getChickenEggsLaid().get(5));
		farm.getChickenEggsLaid().set(5, farm.getChickenEggsLaid().get(6));
		farm.getChickenEggsLaid().set(6, eggsLaidUpdateList.get(0));

		farm.getDuckEggsLaid().set(0, farm.getDuckEggsLaid().get(1));
		farm.getDuckEggsLaid().set(1, farm.getDuckEggsLaid().get(2));
		farm.getDuckEggsLaid().set(2, farm.getDuckEggsLaid().get(3));
		farm.getDuckEggsLaid().set(3, farm.getDuckEggsLaid().get(4));
		farm.getDuckEggsLaid().set(4, farm.getDuckEggsLaid().get(5));
		farm.getDuckEggsLaid().set(5, farm.getDuckEggsLaid().get(6));
		farm.getDuckEggsLaid().set(6, eggsLaidUpdateList.get(1));

		farm.getTurkeyEggsLaid().set(0, farm.getTurkeyEggsLaid().get(1));
		farm.getTurkeyEggsLaid().set(1, farm.getTurkeyEggsLaid().get(2));
		farm.getTurkeyEggsLaid().set(2, farm.getTurkeyEggsLaid().get(3));
		farm.getTurkeyEggsLaid().set(3, farm.getTurkeyEggsLaid().get(4));
		farm.getTurkeyEggsLaid().set(4, farm.getTurkeyEggsLaid().get(5));
		farm.getTurkeyEggsLaid().set(5, farm.getTurkeyEggsLaid().get(6));
		farm.getTurkeyEggsLaid().set(6, eggsLaidUpdateList.get(2));

		farm.setYoungMaleChickens(updateList.get(0));
		farm.setYoungFemaleChickens(updateList.get(1));
		farm.setMatureMaleChickens(updateList.get(2));
		farm.setMatureFemaleChickens(updateList.get(3));
		farm.setOldMaleChickens(updateList.get(4));
		farm.setOldFemaleChickens(updateList.get(5));

		farm.setYoungMaleDucks(updateList.get(6));
		farm.setYoungFemaleDucks(updateList.get(7));
		farm.setMatureMaleDucks(updateList.get(8));
		farm.setMatureFemaleDucks(updateList.get(9));
		farm.setOldMaleDucks(updateList.get(10));
		farm.setOldFemaleDucks(updateList.get(11));

		farm.setYoungMaleTurkeys(updateList.get(12));
		farm.setYoungFemaleTurkeys(updateList.get(13));
		farm.setMatureMaleTurkeys(updateList.get(14));
		farm.setMatureFemaleTurkeys(updateList.get(15));
		farm.setOldMaleTurkeys(updateList.get(16));
		farm.setOldFemaleTurkeys(updateList.get(17));

		farm.getDeadChickens().set(0, farm.getDeadChickens().get(1));
		farm.getDeadChickens().set(1, farm.getDeadChickens().get(2));
		farm.getDeadChickens().set(2, farm.getDeadChickens().get(3));
		farm.getDeadChickens().set(3, farm.getDeadChickens().get(4));
		farm.getDeadChickens().set(4, farm.getDeadChickens().get(5));
		farm.getDeadChickens().set(5, farm.getDeadChickens().get(6));
		farm.getDeadChickens().set(6, deadAnimalsUpdateList.get(0));

		farm.getDeadDucks().set(0, farm.getDeadDucks().get(1));
		farm.getDeadDucks().set(1, farm.getDeadDucks().get(2));
		farm.getDeadDucks().set(2, farm.getDeadDucks().get(3));
		farm.getDeadDucks().set(3, farm.getDeadDucks().get(4));
		farm.getDeadDucks().set(4, farm.getDeadDucks().get(5));
		farm.getDeadDucks().set(5, farm.getDeadDucks().get(6));
		farm.getDeadDucks().set(6, deadAnimalsUpdateList.get(1));

		farm.getDeadTurkeys().set(0, farm.getDeadTurkeys().get(1));
		farm.getDeadTurkeys().set(1, farm.getDeadTurkeys().get(2));
		farm.getDeadTurkeys().set(2, farm.getDeadTurkeys().get(3));
		farm.getDeadTurkeys().set(3, farm.getDeadTurkeys().get(4));
		farm.getDeadTurkeys().set(4, farm.getDeadTurkeys().get(5));
		farm.getDeadTurkeys().set(5, farm.getDeadTurkeys().get(6));
		farm.getDeadTurkeys().set(6, deadAnimalsUpdateList.get(2));

		animalsSold = Arrays.asList(0, 0, 0);
		animalsBought = Arrays.asList(0, 0, 0);

		System.out.println("DAY PASSED-----------------------------------");
		System.out.println("BOUGHT");
		System.out.println(farm.getChickensBought());
		System.out.println(farm.getDucksBought());
		System.out.println(farm.getTurkeysBought());
		System.out.println("");
		System.out.println("SOLD");
		System.out.println(farm.getChickensSold());
		System.out.println(farm.getDucksSold());
		System.out.println(farm.getTurkeysSold());
		System.out.println("");
		System.out.println("LAID");
		System.out.println(farm.getChickenEggsLaid());
		System.out.println(farm.getDuckEggsLaid());
		System.out.println(farm.getTurkeyEggsLaid());
		System.out.println("");
		System.out.println("HATCHED");
		System.out.println(farm.getChickensHatched());
		System.out.println(farm.getDucksHatched());
		System.out.println(farm.getTurkeysHatched());
		System.out.println("");
		System.out.println("DEAD");
		System.out.println(farm.getDeadChickens());
		System.out.println(farm.getDeadDucks());
		System.out.println(farm.getDeadTurkeys());

	}

	// -----------------------animals----------------------------------

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> buyAnimals(String species, String maturity, String sex, Integer amount) {
		Map<String, Object> map = new HashMap<>();
		HttpStatus status = null;
		Farm farm = farmRepository.findAll().stream().findFirst().get();
		String info = null;
		String result = null;
		Integer purchaseAmount = null;
		switch (maturity) {
		case "young":
			purchaseAmount = amount * 130;
			break;
		case "old":
			purchaseAmount = amount * 30;
			break;
		case "mature":
			switch (sex) {
			case "male":
				purchaseAmount = amount * 135;
				break;
			case "female":
				purchaseAmount = amount * 150;
				break;
			}
			break;
		}
		Double currentMoney = farm.getCurrentMoney();
		if (currentMoney >= purchaseAmount && amount > 0) {
			farm.setCurrentMoney(currentMoney - purchaseAmount);
			animalsFeignClient.createAnimals(species, maturity, sex, amount);
			info = amount + " " + maturity + " " + sex + " " + species + " purchased for $" + purchaseAmount;
			status = HttpStatus.ACCEPTED;
			result = "SUCCESS";

			switch (species) {
			case "chickens":
				animalsBought.set(0, animalsBought.get(0) + amount);
				switch (maturity) {
				case "young":
					switch (sex) {
					case "male":
						farm.setYoungMaleChickens(farm.getYoungMaleChickens() + amount);
						break;
					case "female":
						farm.setYoungFemaleChickens(farm.getYoungFemaleChickens() + amount);
						break;
					}
					break;
				case "mature":
					switch (sex) {
					case "male":
						farm.setMatureMaleChickens(farm.getMatureMaleChickens() + amount);
						break;
					case "female":
						farm.setMatureFemaleChickens(farm.getMatureFemaleChickens() + amount);
						break;
					}
					break;
				case "old":
					switch (sex) {
					case "male":
						farm.setOldMaleChickens(farm.getOldMaleChickens() + amount);
						break;
					case "female":
						farm.setOldFemaleChickens(farm.getOldFemaleChickens() + amount);
						break;
					}
					break;
				}
				break;
			case "ducks":
				animalsBought.set(1, animalsBought.get(1) + amount);
				switch (maturity) {
				case "young":
					switch (sex) {
					case "male":
						farm.setYoungMaleDucks(farm.getYoungMaleDucks() + amount);
						break;
					case "female":
						farm.setYoungFemaleDucks(farm.getYoungFemaleDucks() + amount);
						break;
					}
					break;
				case "mature":
					switch (sex) {
					case "male":
						farm.setMatureMaleDucks(farm.getMatureMaleDucks() + amount);
						break;
					case "female":
						farm.setMatureFemaleDucks(farm.getMatureFemaleDucks() + amount);
						break;
					}
				case "old":
					switch (sex) {
					case "male":
						farm.setOldMaleDucks(farm.getOldMaleDucks() + amount);
						break;
					case "female":
						farm.setOldFemaleDucks(farm.getOldFemaleDucks() + amount);
						break;
					}
					break;
				}
				break;
			case "turkeys":
				animalsBought.set(2, animalsBought.get(2) + amount);
				switch (maturity) {
				case "young":
					switch (sex) {
					case "male":
						farm.setYoungMaleTurkeys(farm.getYoungMaleTurkeys() + amount);
						break;
					case "female":
						farm.setYoungFemaleTurkeys(farm.getYoungFemaleTurkeys() + amount);
						break;
					}
					break;
				case "mature":
					switch (sex) {
					case "male":
						farm.setMatureMaleTurkeys(farm.getMatureMaleTurkeys() + amount);
						break;
					case "female":
						farm.setMatureFemaleTurkeys(farm.getMatureFemaleTurkeys() + amount);
						break;
					}
					break;
				case "old":
					switch (sex) {
					case "male":
						farm.setOldMaleTurkeys(farm.getOldMaleTurkeys() + amount);
						break;
					case "female":
						farm.setOldFemaleTurkeys(farm.getOldFemaleTurkeys() + amount);
						break;
					}
					break;
				}
				break;
			}

		} else if (amount == 0) {
			info = "You can't buy 0 animals";
			status = HttpStatus.FORBIDDEN;
			result = "ERROR";
		} else {
			info = "You need $" + (purchaseAmount - currentMoney) + " more to make this transaction";
			status = HttpStatus.BAD_REQUEST;
			result = "ERROR";
		}

		map.put(result, info);
		return new ResponseEntity<>(map, status);
	}

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> sellAnimals(String species, String maturity, String sex,
			Integer amount) {
		Farm farm = farmRepository.findAll().stream().findFirst().get();
		Map<String, Object> map = new HashMap<>();
		HttpStatus status = null;
		String info1 = null;
		String info0 = null;
		try {
			animalsFeignClient.deleteAnimals(species, maturity, sex, amount);
			Integer moneyEarned = null;

			switch (species) {
			case "chickens":
				animalsSold.set(0, animalsSold.get(0) + amount);
				switch (maturity) {
				case "young":
					switch (sex) {
					case "male":
						moneyEarned = amount * 30;
						farm.setYoungMaleChickens(farm.getYoungMaleChickens() - amount);
						break;
					case "female":
						moneyEarned = amount * 30;
						farm.setYoungFemaleChickens(farm.getYoungFemaleChickens() - amount);
						break;
					}
					break;
				case "mature":
					switch (sex) {
					case "male":
						moneyEarned = amount * 35;
						farm.setMatureMaleChickens(farm.getMatureMaleChickens() - amount);
						break;
					case "female":
						moneyEarned = amount * 45;
						farm.setMatureFemaleChickens(farm.getMatureFemaleChickens() - amount);
						break;
					}
					break;
				case "old":
					switch (sex) {
					case "male":
						moneyEarned = amount * 5;
						farm.setOldMaleChickens(farm.getOldMaleChickens() - amount);
						break;
					case "female":
						moneyEarned = amount * 5;
						farm.setOldFemaleChickens(farm.getOldFemaleChickens() - amount);
						break;
					}
					break;
				}
				break;
			case "ducks":
				animalsSold.set(1, animalsSold.get(1) + amount);
				switch (maturity) {
				case "young":
					switch (sex) {
					case "male":
						moneyEarned = amount * 30;
						farm.setYoungMaleDucks(farm.getYoungMaleDucks() - amount);
						break;
					case "female":
						moneyEarned = amount * 30;
						farm.setYoungFemaleDucks(farm.getYoungFemaleDucks() - amount);
						break;
					}
					break;

				case "mature":
					switch (sex) {
					case "male":
						moneyEarned = amount * 35;
						farm.setMatureMaleDucks(farm.getMatureMaleDucks() - amount);
						break;
					case "female":
						moneyEarned = amount * 45;
						farm.setMatureFemaleDucks(farm.getMatureFemaleDucks() - amount);
						break;
					}
					break;

				case "old":
					switch (sex) {
					case "male":
						moneyEarned = amount * 5;
						farm.setOldMaleDucks(farm.getOldMaleDucks() - amount);
						break;
					case "female":
						moneyEarned = amount * 5;
						farm.setOldFemaleDucks(farm.getOldFemaleDucks() - amount);
						break;
					}
					break;
				}
				break;

			case "turkeys":
				animalsSold.set(2, animalsSold.get(2) + amount);
				switch (maturity) {
				case "young":
					switch (sex) {
					case "male":
						moneyEarned = amount * 30;
						farm.setYoungMaleTurkeys(farm.getYoungMaleTurkeys() - amount);
						break;
					case "female":
						moneyEarned = amount * 30;
						farm.setYoungFemaleTurkeys(farm.getYoungFemaleTurkeys() - amount);
						break;
					}
					break;

				case "mature":
					switch (sex) {
					case "male":
						moneyEarned = amount * 35;
						farm.setMatureMaleTurkeys(farm.getMatureMaleTurkeys() - amount);
						break;
					case "female":
						moneyEarned = amount * 45;
						farm.setMatureFemaleTurkeys(farm.getMatureFemaleTurkeys() - amount);
						break;
					}
					break;

				case "old":
					switch (sex) {
					case "male":
						moneyEarned = amount * 5;
						farm.setOldMaleTurkeys(farm.getOldMaleTurkeys() - amount);
						break;
					case "female":
						moneyEarned = amount * 5;
						farm.setOldFemaleTurkeys(farm.getOldFemaleTurkeys() - amount);
						break;
					}
					break;
				}
				break;
			}

			farm.setCurrentMoney(farm.getCurrentMoney() + moneyEarned);
			status = HttpStatus.ACCEPTED;
			info0 = "SUCCESS";
			info1 = "You sold " + amount + " " + maturity + " " + sex + " " + species + " and earned $" + moneyEarned;

		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			info0 = "ERROR";
			info1 = "You don't have " + amount + " " + maturity + " " + sex + " " + species;
		}

		map.put(info0, info1);
		return new ResponseEntity<>(map, status);
	}

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> buyEggs(String species, Integer amount) {
		Map<String, Object> map = new HashMap<>();
		String result = null;
		String info = null;
		HttpStatus status = null;
		Farm farm = farmRepository.findAll().stream().findFirst().get();
		Double currentMoney = farm.getCurrentMoney();
		Integer purchaseMoney = amount * 100;

		if (currentMoney >= purchaseMoney) {
			animalsFeignClient.createEggs(species, amount);
			farm.setCurrentMoney(currentMoney - purchaseMoney);
			result = "SUCCESS";
			info = amount + " " + species + " purchased for $" + purchaseMoney;
			status = HttpStatus.ACCEPTED;

			switch (species) {
			case "chickens":
				animalsBought.set(0, animalsBought.get(0) + amount);
				break;
			case "ducks":
				animalsBought.set(1, animalsBought.get(1) + amount);
				break;
			case "turkeys":
				animalsBought.set(2, animalsBought.get(2) + amount);
			}
		} else {
			result = "ERROR";
			info = "You need $" + (purchaseMoney - currentMoney) + " more to purchase " + amount + " " + species
					+ " eggs";
			status = HttpStatus.BAD_REQUEST;
		}
		map.put(result, info);
		return new ResponseEntity<>(map, status);
	}

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> sellEggs(String species, Integer amount) {
		Map<String, Object> map = new HashMap<>();
		Farm farm = farmRepository.findAll().stream().findFirst().get();
		String result = null;
		String info = null;
		HttpStatus status = null;
		Double currentMoney = farm.getCurrentMoney();
		Integer purchaseMoney = amount * 20;

		try {
			animalsFeignClient.deleteEggs(species, amount);
			farm.setCurrentMoney(currentMoney + purchaseMoney);
			result = "SUCCESS";
			info = amount + " " + species + " eggs sold for $" + purchaseMoney;
			status = HttpStatus.ACCEPTED;

			switch (species) {
			case "chickens":
				animalsSold.set(0, animalsSold.get(0) + amount);
				break;
			case "ducks":
				animalsSold.set(1, animalsSold.get(1) + amount);
				break;
			case "turkeys":
				animalsSold.set(2, animalsSold.get(2) + amount);
			}
		} catch (Exception e) {
			result = "ERROR";
			info = "You don't have " + amount + " " + species + " eggs";
			status = HttpStatus.BAD_REQUEST;
		}

		map.put(result, info);
		return new ResponseEntity<>(map, status);
	}

	// -----------------------show reports----------------------------------

	@Override
	public ResponseEntity<Map<String, Object>> writeReport() throws IOException {
		Map<String, Object> map = new HashMap<>();
		Farm farm = farmRepository.findAll().stream().findFirst().get();

		List<Integer> chickensHatched = farm.getChickensHatched();
		Integer chickensHatchedInLastSevenDays = chickensHatched.get(0) + chickensHatched.get(1)
				+ chickensHatched.get(2) + chickensHatched.get(3) + chickensHatched.get(4) + chickensHatched.get(5)
				+ chickensHatched.get(6);

		List<Integer> ducksHatched = farm.getDucksHatched();
		Integer ducksHatchedInLastSevenDays = ducksHatched.get(0) + ducksHatched.get(1) + ducksHatched.get(2)
				+ ducksHatched.get(3) + ducksHatched.get(4) + ducksHatched.get(5) + ducksHatched.get(6);

		List<Integer> turkeysHatched = farm.getTurkeysHatched();
		Integer turkeysHatchedInLastSevenDays = turkeysHatched.get(0) + turkeysHatched.get(1) + turkeysHatched.get(2)
				+ turkeysHatched.get(3) + turkeysHatched.get(4) + turkeysHatched.get(5) + turkeysHatched.get(6);

		List<Integer> chickenEggsLied = farm.getChickenEggsLaid();
		Integer chickenEggsLiedLastSevenDays = chickenEggsLied.get(0) + chickenEggsLied.get(1) + chickenEggsLied.get(2)
				+ chickenEggsLied.get(3) + chickenEggsLied.get(4) + chickenEggsLied.get(5) + chickenEggsLied.get(6);

		List<Integer> duckEggsLied = farm.getDuckEggsLaid();
		Integer duckEggsLiedLastSevenDays = duckEggsLied.get(0) + duckEggsLied.get(1) + duckEggsLied.get(2)
				+ duckEggsLied.get(3) + duckEggsLied.get(4) + duckEggsLied.get(5) + duckEggsLied.get(6);

		List<Integer> turkeyEggsLied = farm.getTurkeyEggsLaid();
		Integer turkeyEggsLiedLastSevenDays = turkeyEggsLied.get(0) + turkeyEggsLied.get(1) + turkeyEggsLied.get(2)
				+ turkeyEggsLied.get(3) + turkeyEggsLied.get(4) + turkeyEggsLied.get(5) + turkeyEggsLied.get(6);

		List<Integer> deadChickens = farm.getDeadChickens();
		Integer deadChickensLastSevenDays = deadChickens.get(0) + deadChickens.get(1) + deadChickens.get(2)
				+ deadChickens.get(3) + deadChickens.get(4) + deadChickens.get(5) + deadChickens.get(6);

		List<Integer> deadDucks = farm.getDeadDucks();
		Integer deadDucksLastSevenDays = deadDucks.get(0) + deadDucks.get(1) + deadDucks.get(2) + deadDucks.get(3)
				+ deadDucks.get(4) + deadDucks.get(5) + deadDucks.get(6);

		List<Integer> deadTurkeys = farm.getDeadTurkeys();
		Integer deadTurkeysLastSevenDays = deadTurkeys.get(0) + deadTurkeys.get(1) + deadTurkeys.get(2)
				+ deadTurkeys.get(3) + deadTurkeys.get(4) + deadTurkeys.get(5) + deadTurkeys.get(6);

		List<Integer> chickensBought = farm.getChickensBought();
		Integer chickensBoughtLastSevenDays = chickensBought.get(0) + chickensBought.get(1) + chickensBought.get(2)
				+ chickensBought.get(3) + chickensBought.get(4) + chickensBought.get(5) + chickensBought.get(6);

		List<Integer> ducksBought = farm.getChickensBought();
		Integer ducksBoughtLastSevenDays = ducksBought.get(0) + ducksBought.get(1) + ducksBought.get(2)
				+ ducksBought.get(3) + ducksBought.get(4) + ducksBought.get(5) + ducksBought.get(6);

		List<Integer> turkeysBought = farm.getTurkeysBought();
		Integer turkeysBoughtLastSevenDays = turkeysBought.get(0) + turkeysBought.get(1) + turkeysBought.get(2)
				+ turkeysBought.get(3) + turkeysBought.get(4) + turkeysBought.get(5) + turkeysBought.get(6);

		List<Integer> chickensSold = farm.getChickensSold();
		Integer chickensSoldLastSevenDays = chickensSold.get(0) + chickensSold.get(1) + chickensSold.get(2)
				+ chickensSold.get(3) + chickensSold.get(4) + chickensSold.get(5) + chickensSold.get(6);

		List<Integer> ducksSold = farm.getDucksSold();
		Integer ducksSoldLastSevenDays = ducksSold.get(0) + ducksSold.get(1) + ducksSold.get(2) + ducksSold.get(3)
				+ ducksSold.get(4) + ducksSold.get(5) + ducksSold.get(6);

		List<Integer> turkeysSold = farm.getTurkeysSold();
		Integer turkeysSoldLastSevenDays = turkeysSold.get(0) + turkeysSold.get(1) + turkeysSold.get(2)
				+ turkeysSold.get(3) + turkeysSold.get(4) + turkeysSold.get(5) + turkeysSold.get(6);

		LocalDate date = LocalDate.now();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(date.toString());

		HSSFRow nameROW = sheet.createRow(0);
		nameROW.createCell(0).setCellValue("FARM NAME");
		nameROW.createCell(1).setCellValue(farm.getName());

		HSSFRow dateROW = sheet.createRow(1);
		dateROW.createCell(0).setCellValue("CREATION DATE");
		dateROW.createCell(1).setCellValue(farm.getStartDate().toString());

		HSSFRow mainROW = sheet.createRow(3);
		mainROW.createCell(3).setCellValue("CHICKENS");
		mainROW.createCell(4).setCellValue("DUCKS");
		mainROW.createCell(5).setCellValue("TURKEYS");

		HSSFRow eggsLaidROW = sheet.createRow(4);
		eggsLaidROW.createCell(2).setCellValue("EGGS LAID");
		eggsLaidROW.createCell(3).setCellValue(chickenEggsLiedLastSevenDays);
		eggsLaidROW.createCell(4).setCellValue(duckEggsLiedLastSevenDays);
		eggsLaidROW.createCell(5).setCellValue(turkeyEggsLiedLastSevenDays);

		HSSFRow eggsHatchedROW = sheet.createRow(5);
		eggsHatchedROW.createCell(2).setCellValue("EGGS HATCHED");
		eggsHatchedROW.createCell(3).setCellValue(chickensHatchedInLastSevenDays);
		eggsHatchedROW.createCell(4).setCellValue(ducksHatchedInLastSevenDays);
		eggsHatchedROW.createCell(5).setCellValue(turkeysHatchedInLastSevenDays);

		HSSFRow youngMaleROW = sheet.createRow(7);
		youngMaleROW.createCell(2).setCellValue("YOUNG MALE");
		youngMaleROW.createCell(3).setCellValue(farm.getYoungMaleChickens());
		youngMaleROW.createCell(4).setCellValue(farm.getYoungMaleDucks());
		youngMaleROW.createCell(5).setCellValue(farm.getYoungMaleTurkeys());

		HSSFRow youngFemaleROW = sheet.createRow(8);
		youngFemaleROW.createCell(2).setCellValue("YOUNG FEMALE");
		youngFemaleROW.createCell(3).setCellValue(farm.getYoungFemaleChickens());
		youngFemaleROW.createCell(4).setCellValue(farm.getYoungFemaleDucks());
		youngFemaleROW.createCell(5).setCellValue(farm.getYoungFemaleTurkeys());

		HSSFRow matureMaleROW = sheet.createRow(10);
		matureMaleROW.createCell(2).setCellValue("MATURE MALE");
		matureMaleROW.createCell(3).setCellValue(farm.getMatureMaleChickens());
		matureMaleROW.createCell(4).setCellValue(farm.getMatureMaleDucks());
		matureMaleROW.createCell(5).setCellValue(farm.getMatureMaleTurkeys());

		HSSFRow matureFemaleROW = sheet.createRow(11);
		matureFemaleROW.createCell(2).setCellValue("MATURE FEMALE");
		matureFemaleROW.createCell(3).setCellValue(farm.getMatureFemaleChickens());
		matureFemaleROW.createCell(4).setCellValue(farm.getMatureFemaleDucks());
		matureFemaleROW.createCell(5).setCellValue(farm.getMatureFemaleTurkeys());

		HSSFRow oldMaleROW = sheet.createRow(13);
		oldMaleROW.createCell(2).setCellValue("OLD MALE");
		oldMaleROW.createCell(3).setCellValue(farm.getOldMaleChickens());
		oldMaleROW.createCell(4).setCellValue(farm.getOldMaleDucks());
		oldMaleROW.createCell(5).setCellValue(farm.getOldMaleTurkeys());

		HSSFRow oldFemaleROW = sheet.createRow(14);
		oldFemaleROW.createCell(2).setCellValue("OLD FEMALE");
		oldFemaleROW.createCell(3).setCellValue(farm.getOldFemaleChickens());
		oldFemaleROW.createCell(4).setCellValue(farm.getOldFemaleDucks());
		oldFemaleROW.createCell(5).setCellValue(farm.getOldFemaleTurkeys());

		HSSFRow deadROW = sheet.createRow(16);
		deadROW.createCell(2).setCellValue("DEAD");
		deadROW.createCell(3).setCellValue(deadChickensLastSevenDays);
		deadROW.createCell(4).setCellValue(deadDucksLastSevenDays);
		deadROW.createCell(5).setCellValue(deadTurkeysLastSevenDays);

		HSSFRow boughtROW = sheet.createRow(18);
		boughtROW.createCell(2).setCellValue("BOUGHT");
		boughtROW.createCell(3).setCellValue(chickensBoughtLastSevenDays);
		boughtROW.createCell(4).setCellValue(ducksBoughtLastSevenDays);
		boughtROW.createCell(5).setCellValue(turkeysBoughtLastSevenDays);

		HSSFRow soldROW = sheet.createRow(19);
		soldROW.createCell(2).setCellValue("SOLD");
		soldROW.createCell(3).setCellValue(chickensSoldLastSevenDays);
		soldROW.createCell(4).setCellValue(ducksSoldLastSevenDays);
		soldROW.createCell(5).setCellValue(turkeysSoldLastSevenDays);

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);

		workbook.write(new FileOutputStream("report.xls"));
		workbook.close();

		return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<Map<String, Object>> showReport() {
		Farm farm = farmRepository.findAll().stream().findFirst().get();
		Map<String, Object> map = new HashMap<>();
		map.put("OLD FEMALE CHICKENS", farm.getOldFemaleChickens());
		return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
	}

	@Override
	public Integer getFarmFood() {
		if (farmRepository.findAll().isEmpty()) {
			return 3;
		} else {
			Farm farm = farmRepository.findAll().stream().findFirst().get();
			return farm.getGramsOfFood();
		}
	}

	@Override
	@Transactional
	public void setFarmFood(Integer amount) {
		Farm farm = farmRepository.findAll().stream().findFirst().get();
		farm.setGramsOfFood(amount);
	}

	@Override
	@Transactional
	public ResponseEntity<Map<String, Object>> buyFood(Integer amount) {
		Map<String, Object> map = new HashMap<>();
		if (amount % 1000 == 0 && amount != 0) {
			Farm farm = farmRepository.findAll().stream().findFirst().get();
			System.out.println(farm.getGramsOfFood());
			Double farmMoney = farm.getCurrentMoney();
			Double requestedFoodPrice = amount.doubleValue() * 5;
			if (farmMoney < requestedFoodPrice) {
				map.put("ERROR", "You don't have enough money");
			} else {
				farm.setCurrentMoney(farm.getCurrentMoney() - requestedFoodPrice);
				farm.setGramsOfFood(farm.getGramsOfFood() + amount);
				String info = "You've purchased " + amount + " grams of food for $" + requestedFoodPrice;
				map.put("SUCCESS", info);
			}
		} else {
			map.put("ERROR", "You can purchase by the 1000's");
		}
		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
	}

	public List<Integer> getAnimalsSold() {
		return animalsSold;
	}

	public void setAnimalsSold(List<Integer> animalsSold) {
		this.animalsSold = animalsSold;
	}

	public List<Integer> getAnimalsBought() {
		return animalsBought;
	}

	public void setAnimalsBought(List<Integer> animalsBought) {
		this.animalsBought = animalsBought;
	}

}
