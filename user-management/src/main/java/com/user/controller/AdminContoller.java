package com.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/admin")
@RestController
public class AdminContoller {
	
	@GetMapping
	public String firstPage() {
		return "This is admin page";
	}

}
