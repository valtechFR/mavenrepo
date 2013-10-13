package com.valtech.cq.maventest.impl.service;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.valtech.cq.maventest.service.HelloWorldService;
import com.valtech.cq.maventest.impl.service.HelloWorldServiceImpl;

public class HelloWorldServiceImplTestCase 
{

	private static HelloWorldService helloWorldService;
 	
	@BeforeClass
	public static void setUpClass()
	{
		helloWorldService = new HelloWorldServiceImpl();
	}
	
	@Test
	public void testGestServiceNames() throws java.lang.Exception 
	{
		Assert.assertEquals(helloWorldService.getGreetings("test"), "Hello test");
	}
}
