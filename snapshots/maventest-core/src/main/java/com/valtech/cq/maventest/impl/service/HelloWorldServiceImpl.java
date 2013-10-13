package com.valtech.cq.maventest.impl.service;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;

import com.valtech.cq.maventest.service.HelloWorldService;

/**
 * Demo OSGI service impl for Maven Test Project
 * 
 * @author valtech
 */
@Component(
		name = "com.valtech.cq.maventest.service.HelloWorldService",
		immediate = true,
		metatype = true,
		label = "Maven Test Project Hello World Service", 
		description = "Demo OSGI service impl for maventest")
@Service
@Properties( {
    @Property(name = Constants.SERVICE_DESCRIPTION, value = "Maven Test Project Hello World Service"),
    @Property(name = Constants.SERVICE_VENDOR, value = "Valtech S.A.")
})
public class HelloWorldServiceImpl implements HelloWorldService
{
	@Override
	public String getGreetings(String username)
	{
		return "Hello "+username;
	}

}