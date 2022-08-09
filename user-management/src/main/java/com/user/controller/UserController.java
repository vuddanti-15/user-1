package com.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/user/")
@RestController
public class UserController {
	
	@GetMapping
	public String firstPage() {
		return "This is user page";
	}

}
