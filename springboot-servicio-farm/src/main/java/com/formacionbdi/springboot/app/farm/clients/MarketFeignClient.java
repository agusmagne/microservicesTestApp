package com.formacionbdi.springboot.app.farm.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "market")
public interface MarketFeignClient {

	@GetMapping("/eggPrice")
	public Double eggPrice();

	@GetMapping("/chickenPrice")
	public Double chickenPrice();
	
	@GetMapping("/eggSellPrice")
	public Double eggSellPrice();
	
	@GetMapping("/chickenSellPrice")
	public Double chickenSellPrice();

	@PutMapping("/eggPrice/set")
	public void setEggPrice(@RequestBody Double purchaseMoney);

	@PutMapping("/chickenPrice/set")
	public void setChickenPrice(@RequestBody Double purchaseMoney);
	
	@PutMapping("/eggSellPrice/set")
	public void setEggSellPrice(@RequestBody Double purhcaseMoney);
	
	@PutMapping("/chickenSellPrice/set")
	public void setChickenSellPrice(@RequestBody Double purhcaseMoney);

}
