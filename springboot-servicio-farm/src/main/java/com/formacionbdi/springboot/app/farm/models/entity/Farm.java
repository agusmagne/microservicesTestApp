package com.formacionbdi.springboot.app.farm.models.entity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "farm")
public class Farm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FARM_ID")
	private Long id;

	private String name;

	private Double currentMoney;
	private Integer gramsOfFood = 0;

	private Integer chickenEggsBought = 0;
	private Integer chickenEggsSold = 0;
	private Integer youngMaleChickens = 0;
	private Integer youngFemaleChickens = 0;
	private Integer matureMaleChickens = 0;
	private Integer matureFemaleChickens = 0;
	private Integer oldMaleChickens = 0;
	private Integer oldFemaleChickens = 0;

	private Integer duckEggsBought = 0;
	private Integer duckEggsSold = 0;
	private Integer youngMaleDucks = 0;
	private Integer youngFemaleDucks = 0;
	private Integer matureMaleDucks = 0;
	private Integer matureFemaleDucks = 0;
	private Integer oldMaleDucks = 0;
	private Integer oldFemaleDucks = 0;

	private Integer turkeyEggsBought = 0;
	private Integer turkeyEggsSold = 0;
	private Integer youngMaleTurkeys = 0;
	private Integer youngFemaleTurkeys = 0;
	private Integer matureMaleTurkeys = 0;
	private Integer matureFemaleTurkeys = 0;
	private Integer oldMaleTurkeys = 0;
	private Integer oldFemaleTurkeys = 0;

	@ElementCollection
	private List<Integer> chickensBought = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> ducksBought = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> turkeysBought = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
	
	@ElementCollection
	private List<Integer> chickensSold = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> ducksSold = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> turkeysSold = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
	
	@ElementCollection
	private List<Integer> chickenEggsLaid = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> duckEggsLaid = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> turkeyEggsLaid = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> chickensHatched = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> ducksHatched = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> turkeysHatched = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> deadChickens = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> deadDucks = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	@ElementCollection
	private List<Integer> deadTurkeys = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

	private LocalDate startDate;

	public Farm() {
	}

	public Farm(String name, Double currentMoney) {
		super();
		this.name = name;
		this.currentMoney = currentMoney;
	}

	

	
	public List<Integer> getChickensSold() {
		return chickensSold;
	}

	public void setChickensSold(List<Integer> chickensSold) {
		this.chickensSold = chickensSold;
	}

	public List<Integer> getDucksSold() {
		return ducksSold;
	}

	public void setDucksSold(List<Integer> ducksSold) {
		this.ducksSold = ducksSold;
	}

	public List<Integer> getTurkeysSold() {
		return turkeysSold;
	}

	public void setTurkeysSold(List<Integer> turkeysSold) {
		this.turkeysSold = turkeysSold;
	}

	public List<Integer> getChickensBought() {
		return chickensBought;
	}

	public void setChickensBought(List<Integer> chickensBought) {
		this.chickensBought = chickensBought;
	}

	public List<Integer> getDucksBought() {
		return ducksBought;
	}

	public void setDucksBought(List<Integer> ducksBought) {
		this.ducksBought = ducksBought;
	}

	public List<Integer> getTurkeysBought() {
		return turkeysBought;
	}

	public void setTurkeysBought(List<Integer> turkeysBought) {
		this.turkeysBought = turkeysBought;
	}

	public List<Integer> getChickensHatched() {
		return chickensHatched;
	}

	public void setChickensHatched(List<Integer> chickensHatched) {
		this.chickensHatched = chickensHatched;
	}

	public List<Integer> getDucksHatched() {
		return ducksHatched;
	}

	public void setDucksHatched(List<Integer> ducksHatched) {
		this.ducksHatched = ducksHatched;
	}

	public List<Integer> getTurkeysHatched() {
		return turkeysHatched;
	}

	public void setTurkeysHatched(List<Integer> turkeysHatched) {
		this.turkeysHatched = turkeysHatched;
	}

	public List<Integer> getChickenEggsLaid() {
		return chickenEggsLaid;
	}

	public void setChickenEggsLaid(List<Integer> chickenEggsLaid) {
		this.chickenEggsLaid = chickenEggsLaid;
	}

	public Integer getChickenEggsBought() {
		return chickenEggsBought;
	}

	public void setChickenEggsBought(Integer chickenEggsBought) {
		this.chickenEggsBought = chickenEggsBought;
	}

	public Integer getChickenEggsSold() {
		return chickenEggsSold;
	}

	public void setChickenEggsSold(Integer chickenEggsSold) {
		this.chickenEggsSold = chickenEggsSold;
	}

	public List<Integer> getDuckEggsLaid() {
		return duckEggsLaid;
	}

	public void setDuckEggsLaid(List<Integer> duckEggsLaid) {
		this.duckEggsLaid = duckEggsLaid;
	}

	public Integer getDuckEggsBought() {
		return duckEggsBought;
	}

	public void setDuckEggsBought(Integer duckEggsBought) {
		this.duckEggsBought = duckEggsBought;
	}

	public Integer getDuckEggsSold() {
		return duckEggsSold;
	}

	public void setDuckEggsSold(Integer duckEggsSold) {
		this.duckEggsSold = duckEggsSold;
	}

	public List<Integer> getTurkeyEggsLaid() {
		return turkeyEggsLaid;
	}

	public void setTurkeyEggsLaid(List<Integer> turkeyEggsLaid) {
		this.turkeyEggsLaid = turkeyEggsLaid;
	}

	public Integer getTurkeyEggsBought() {
		return turkeyEggsBought;
	}

	public void setTurkeyEggsBought(Integer turkeyEggsBought) {
		this.turkeyEggsBought = turkeyEggsBought;
	}

	public Integer getTurkeyEggsSold() {
		return turkeyEggsSold;
	}

	public void setTurkeyEggsSold(Integer turkeyEggsSold) {
		this.turkeyEggsSold = turkeyEggsSold;
	}

	public Integer getGramsOfFood() {
		return gramsOfFood;
	}

	public void setGramsOfFood(Integer gramsOfFood) {
		this.gramsOfFood = gramsOfFood;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Double getCurrentMoney() {
		return currentMoney;
	}

	public void setCurrentMoney(Double currentMoney) {
		this.currentMoney = currentMoney;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYoungMaleChickens() {
		return youngMaleChickens;
	}

	public void setYoungMaleChickens(Integer youngMaleChickens) {
		this.youngMaleChickens = youngMaleChickens;
	}

	public Integer getYoungFemaleChickens() {
		return youngFemaleChickens;
	}

	public void setYoungFemaleChickens(Integer youngFemaleChickens) {
		this.youngFemaleChickens = youngFemaleChickens;
	}

	public Integer getMatureMaleChickens() {
		return matureMaleChickens;
	}

	public void setMatureMaleChickens(Integer matureMaleChickens) {
		this.matureMaleChickens = matureMaleChickens;
	}

	public Integer getMatureFemaleChickens() {
		return matureFemaleChickens;
	}

	public void setMatureFemaleChickens(Integer matureFemaleChickens) {
		this.matureFemaleChickens = matureFemaleChickens;
	}

	public Integer getOldMaleChickens() {
		return oldMaleChickens;
	}

	public void setOldMaleChickens(Integer oldMaleChickens) {
		this.oldMaleChickens = oldMaleChickens;
	}

	public Integer getOldFemaleChickens() {
		return oldFemaleChickens;
	}

	public void setOldFemaleChickens(Integer oldFemaleChickens) {
		this.oldFemaleChickens = oldFemaleChickens;
	}

	public Integer getYoungMaleDucks() {
		return youngMaleDucks;
	}

	public void setYoungMaleDucks(Integer youngMaleDucks) {
		this.youngMaleDucks = youngMaleDucks;
	}

	public Integer getYoungFemaleDucks() {
		return youngFemaleDucks;
	}

	public void setYoungFemaleDucks(Integer youngFemaleDucks) {
		this.youngFemaleDucks = youngFemaleDucks;
	}

	public Integer getMatureMaleDucks() {
		return matureMaleDucks;
	}

	public void setMatureMaleDucks(Integer matureMaleDucks) {
		this.matureMaleDucks = matureMaleDucks;
	}

	public Integer getMatureFemaleDucks() {
		return matureFemaleDucks;
	}

	public void setMatureFemaleDucks(Integer matureFemaleDucks) {
		this.matureFemaleDucks = matureFemaleDucks;
	}

	public Integer getOldMaleDucks() {
		return oldMaleDucks;
	}

	public void setOldMaleDucks(Integer oldMaleDucks) {
		this.oldMaleDucks = oldMaleDucks;
	}

	public Integer getOldFemaleDucks() {
		return oldFemaleDucks;
	}

	public void setOldFemaleDucks(Integer oldFemaleDucks) {
		this.oldFemaleDucks = oldFemaleDucks;
	}

	public Integer getYoungMaleTurkeys() {
		return youngMaleTurkeys;
	}

	public void setYoungMaleTurkeys(Integer youngMaleTurkeys) {
		this.youngMaleTurkeys = youngMaleTurkeys;
	}

	public Integer getYoungFemaleTurkeys() {
		return youngFemaleTurkeys;
	}

	public void setYoungFemaleTurkeys(Integer youngFemaleTurkeys) {
		this.youngFemaleTurkeys = youngFemaleTurkeys;
	}

	public Integer getMatureMaleTurkeys() {
		return matureMaleTurkeys;
	}

	public void setMatureMaleTurkeys(Integer matureMaleTurkeys) {
		this.matureMaleTurkeys = matureMaleTurkeys;
	}

	public Integer getMatureFemaleTurkeys() {
		return matureFemaleTurkeys;
	}

	public void setMatureFemaleTurkeys(Integer matureFemaleTurkeys) {
		this.matureFemaleTurkeys = matureFemaleTurkeys;
	}

	public Integer getOldMaleTurkeys() {
		return oldMaleTurkeys;
	}

	public void setOldMaleTurkeys(Integer oldMaleTurkeys) {
		this.oldMaleTurkeys = oldMaleTurkeys;
	}

	public Integer getOldFemaleTurkeys() {
		return oldFemaleTurkeys;
	}

	public void setOldFemaleTurkeys(Integer oldFemaleTurkeys) {
		this.oldFemaleTurkeys = oldFemaleTurkeys;
	}

	public List<Integer> getDeadChickens() {
		return deadChickens;
	}

	public void setDeadChickens(List<Integer> deadChickens) {
		this.deadChickens = deadChickens;
	}

	public List<Integer> getDeadDucks() {
		return deadDucks;
	}

	public void setDeadDucks(List<Integer> deadDucks) {
		this.deadDucks = deadDucks;
	}

	public List<Integer> getDeadTurkeys() {
		return deadTurkeys;
	}

	public void setDeadTurkeys(List<Integer> deadTurkeys) {
		this.deadTurkeys = deadTurkeys;
	}

}
