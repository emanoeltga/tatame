package com.correia.bjj.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("status")
public class StatusController {

	@GetMapping
	public String getStatus() {
		System.out.println(new BCryptPasswordEncoder().encode("1234"));
		return "<h1>Status: OK</h1>";
	}
}
