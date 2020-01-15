package com.formacionbdi.springboot.app.farm.config;

import java.io.File;
import java.util.Scanner;

public class FarmConfigReader {
	
	private Scanner x;

	public void OpenFarmConfig() {
		try {
			x = new Scanner(new File("C:\\Agus\\chickentest\\chicken-agustin\\springboot-servicio-farm\\src\\main\\resources\\farmconfig.txt"));
		}
		catch(Exception e) {
			System.out.println("Cannot find or open farmconfig.txt");
		}
	}
	
	public Double ReadFarmConfig() {
		Double initialMoney = null;
		while(x.hasNext()) {
			String a = x.next(); // "INITIAL"
			String b = x.next(); // "MONEY"
			String c = x.next(); // "="
			Double d = x.nextDouble();
			initialMoney = d;
		}
		return initialMoney;
	}
	
	public void CloseFarmConfig() {
		x.close();
	}

}
