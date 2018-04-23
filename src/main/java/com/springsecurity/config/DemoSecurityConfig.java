package com.springsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	// [KEY]: DataSource Injection!
	@Autowired
	private DataSource securityDataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
			.dataSource(securityDataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/")
			.hasRole("EMPLOYEE")
			.antMatchers("/leaders/**")
			.hasRole("MANAGER")
			.antMatchers("/systems/**")
			.hasRole("ADMIN")
			.and()
			.formLogin()
			.loginPage("/showMyLoginPage") // [KEY]: The LoginController
			.loginProcessingUrl("/authenticateTheUser") // [KEY]: Auto-authentication provided by Spring!
			.permitAll() // Everyone can see the login page.
			.and()
			.logout() // Enable logout
			.permitAll()
			.and()
			.exceptionHandling()
			.accessDeniedPage("/access-denied");
	}

	// [KEY] A Bean to handle JDBC for User Details
	@Bean
	public UserDetailsManager userDetailsManager() {

		JdbcUserDetailsManager jdbcUserManager = new JdbcUserDetailsManager();
		jdbcUserManager.setDataSource(securityDataSource);

		return jdbcUserManager;
	}

}
