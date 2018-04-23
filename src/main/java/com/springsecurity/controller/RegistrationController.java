package com.springsecurity.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springsecurity.entity.CompanyUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	// [KEY]: Inject UserDetailManager defined in DemoSecurityConfig
	@Autowired
	private UserDetailsManager userDetailsManager;

	// [KEY]: Bcrypt Password Encription
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// It is used in the form validation process. Here we add support to trim empty
	// strings to null.
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// --------------------------------------
	// GET: Show Registration Form
	// --------------------------------------
	@GetMapping("/showRegistrationForm")
	public String showRegistrationForm(Model theModel) {
		theModel.addAttribute("companyUser", new CompanyUser());

		return "registration-form";
	}

	// --------------------------------------
	// POST: Process Registration Form
	// --------------------------------------
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("companyUser") CompanyUser companyUser, BindingResult theBindingResult,
			Model theModel) {

		String userName = companyUser.getUserName();

		// form validation
		if (theBindingResult.hasErrors()) {

			theModel.addAttribute("companyUser", new CompanyUser());
			theModel.addAttribute("registrationError", "User name/password can not be empty.");

			return "registration-form";
		}

		// check the database if user already exists
		boolean userExists = doesUserExist(userName);

		if (userExists) {
			theModel.addAttribute("companyUser", new CompanyUser());
			theModel.addAttribute("registrationError", "User name already exists.");

			return "registration-form";
		}

		// encrypt the password
		String encodedPassword = passwordEncoder.encode(companyUser.getPassword());

		// prepend the encoding algorithm id
		encodedPassword = "{bcrypt}" + encodedPassword;

		// give user default role of "employee"
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");

		// create user object (from Spring Security framework)
		User tempUser = new User(userName, encodedPassword, authorities);

		// save user in the database
		userDetailsManager.createUser(tempUser);

		return "registration-confirmation";
	}

	private boolean doesUserExist(String userName) {

		// check the database if the user already exists
		boolean exists = userDetailsManager.userExists(userName);

		return exists;
	}
}
