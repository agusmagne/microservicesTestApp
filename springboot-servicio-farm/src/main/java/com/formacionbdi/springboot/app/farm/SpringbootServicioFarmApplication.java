package com.formacionbdi.springboot.app.farm;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.formacionbdi.springboot.app.farm.config.FarmConfigReader;

@EnableFeignClients
@SpringBootApplication
@LoadBalancerClient(name = "farm")
@EnableScheduling
public class SpringbootServicioFarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioFarmApplication.class, args);
	}

}