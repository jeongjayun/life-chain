package com.mysite.sbb.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/map")
@RequiredArgsConstructor
@Controller
public class MapController {
	
	@GetMapping("/aed")
	public String aed() {
		return "/map/aed_map";
	}
}
