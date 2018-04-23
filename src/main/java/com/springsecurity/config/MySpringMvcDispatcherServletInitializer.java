package com.springsecurity.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {

		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {

		// [KEY]: Return the Config java file in an array.
		return new Class[] { DemoAppConfig.class };
	}

	@Override
	protected String[] getServletMappings() {

		// [KEY]: Default Servlet mapping
		return new String[] { "/" };
	}
}
