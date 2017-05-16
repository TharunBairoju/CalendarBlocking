package com.wavelabs.service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import io.swagger.jaxrs.config.BeanConfig;

/**
 * This class will configure the swagger with servlet
 * 
 * @author tharunkumarb
 *
 */
public class SwaggerConfiguration extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setTitle("Calendar blocking");
		beanConfig.setVersion("1.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("10.9.9.50:8080");
		beanConfig.setBasePath("/DynamicCalendarBooking/webapi");
		beanConfig.setResourcePackage("com.wavelabs.resources");
		beanConfig.setScan(true);
		beanConfig.setDescription("Calendar booking");
	}
}
