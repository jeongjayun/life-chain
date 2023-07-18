package com.mysite.sbb.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {
	
	@GetMapping(value = "chart")
	public String chart() {
		return "/chart/chart";
	}
	
	@GetMapping(value = "weather")
	public String weather() {
		return "/weather/weather";
	}

}
