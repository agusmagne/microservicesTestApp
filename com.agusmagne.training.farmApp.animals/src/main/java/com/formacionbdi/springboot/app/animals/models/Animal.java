package com.formacionbdi.springboot.app.animals.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer lifespan;

	private String species;

	private String sex;

	private String maturity;

	private Integer gramsOfFood;

	private Integer daysToLayEggs;

	private Integer eggsPerDay;
	
	public Animal() {
	}

	public Animal(String species, String maturity, String sex, Integer lifespan, Integer gramsOfFood) {
		super();
		this.species = species;
		this.maturity = maturity;
		this.sex = sex;
		this.lifespan = lifespan;
		this.gramsOfFood = gramsOfFood;
	}

	public Integer getDaysToLayEggs() {
		return daysToLayEggs;
	}

	public void setDaysToLayEggs(Integer daysToLayEggs) {
		this.daysToLayEggs = daysToLayEggs;
	}

	public Integer getEggsPerDay() {
		return eggsPerDay;
	}

	public void setEggsPerDay(Integer eggsPerDay) {
		this.eggsPerDay = eggsPerDay;
	}

	public String getMaturity() {
		return maturity;
	}

	public void setMaturity(String maturity) {
		this.maturity = maturity;
	}

	public Integer getGramsOfFood() {
		return gramsOfFood;
	}

	public void setGramsOfFood(Integer gramsOfFood) {
		this.gramsOfFood = gramsOfFood;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLifespan() {
		return lifespan;
	}

	public void setLifespan(Integer lifespan) {
		this.lifespan = lifespan;
	}

}
