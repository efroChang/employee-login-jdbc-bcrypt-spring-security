package com.springsecurity.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.springsecurity")
@PropertySource("classpath:persistence-mysql.properties") // [KEY]: to load the file for injection.
public class DemoAppConfig {

	// [KEY] PropertySource Injection
	@Autowired
	Environment environment;

	private Logger logger = Logger.getLogger(getClass().getName()); // to display JDBC connectivity

	// -----------------------------------------
	// 1. Bean for View Resolver
	// -----------------------------------------
	@Bean
	public ViewResolver viewResolver() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	// -----------------------------------------
	// 1. Bean for Security DataSource
	// -----------------------------------------
	@Bean
	public DataSource securityDataSource() {

		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

		try {
			securityDataSource.setDriverClass(environment.getProperty("jdbc.driver"));

		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}

		logger.info(" >>> jdbc.url = " + environment.getProperty("jdbc.url"));
		logger.info(" >>> jdbc.user = " + environment.getProperty("jdbc.user"));

		// Connecto to DB
		securityDataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
		securityDataSource.setUser(environment.getProperty("jdbc.user"));
		securityDataSource.setPassword(environment.getProperty("jdbc.password"));

		// Connection Pool settings
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

		return securityDataSource;
	}

	// Helper method to conver String to Int
	private int getIntProperty(String propName) {

		return Integer.parseInt(environment.getProperty(propName));
	}

}
