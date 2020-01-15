package com.formacionbdi.springboot.app.animals.clients;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "farm")
public interface FarmFeignClient {


	@GetMapping("/getFarmFood")
	public Integer getFarmFood();
	
	@GetMapping("/farmsTotal")
	public Long getFarmsTotal();

	@PutMapping("/setFarmFood")
	public void setFarmFood(@RequestBody Integer amount);
	
	@PutMapping("/updateFarm")
	public void updateFarm();
}
