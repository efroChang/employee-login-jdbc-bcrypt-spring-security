package com.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/showMyLoginPage") // [KEY]: Matches up with the value in DemoSecurityConfig.configure()
	public String showMyLoginPage() {
		
		return "fancy-login";
	}	
	
	
	@GetMapping("/access-denied") // [KEY]: Matches up with the value in DemoSecurityConfig.configure()
	public String showAccessDenied() {
		
		return "access-denied";
	}
}
