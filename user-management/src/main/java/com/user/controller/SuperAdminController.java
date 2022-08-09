package com.user.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.UserRequestDTO;
import com.user.service.UserService;

@RestController
@RequestMapping(value = "/superadmin")
@Validated
public class SuperAdminController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<String> save(@RequestBody UserRequestDTO userRequestDTO) {
		return new ResponseEntity<String>(userService.validateAndSave(userRequestDTO), HttpStatus.OK);
	}

	@PostMapping("/exception")
	public ResponseEntity<String> exception(@RequestBody UserRequestDTO userRequestDTO) {
		return new ResponseEntity<String>(userService.exception(userRequestDTO), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<String> update(@Valid @RequestBody UserRequestDTO e) {
		return new ResponseEntity<String>("upate success", HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<String> get(@RequestParam("param") @Min(5) int param) {
		return new ResponseEntity<String>("succes", HttpStatus.OK);
	}

}
