package com.valtech.cq.maventest.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.valtech.cq.maventest.service.HelloWorldService;
import com.valtech.cq.maventest.resource.HelloWorld;

@Component(
		name="com.valtech.cq.maventest.HelloWorldAdapterFactory",
		metatype=false, 
		immediate=true
	)
@Service({AdapterFactory.class})
@Properties({
    @Property(name="service.vendor", value="Valtech S.A."),
    @Property(name="service.description", value="HelloWorld factory adapter"),
    @Property(name="adaptables", value={
    		"org.apache.sling.api.resource.Resource",
    		"org.apache.sling.api.resource.ResourceResolver"
    }),
    @Property(name="adapters", value={
    		"com.valtech.cq.maventest.resource.HelloWorld",
    		"com.valtech.cq.maventest.service.HelloWorldService"
    })
})
public class HelloWorldAdapterFactory implements AdapterFactory
{
	private final static Logger logger = LoggerFactory.getLogger(HelloWorldAdapterFactory.class);
    
    @Reference
    private HelloWorldService helloWorldService;

	@Override
	public <AdapterType> AdapterType getAdapter(Object adaptable,
			Class<AdapterType> type) 
	{
		try 
		{
			// Adapt resources
			if (adaptable instanceof Resource)
				return getResource((Resource)adaptable, type);
			
			// Adapt services (managers)
			if (adaptable instanceof ResourceResolver)
				return getManager(type);
				
		} catch (Exception e) {
			logger.warn("An error occured while adapting resource", e);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private <AdapterType> AdapterType getManager(Class<AdapterType> type)
	{
		if (type.equals(HelloWorldService.class))
			return (AdapterType) helloWorldService;
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private <AdapterType> AdapterType getResource(Resource resource, Class<AdapterType> type)
	{
		try
		{	
			if ((type.equals(HelloWorld.class)
				&&resource.getResourceType().equals(HelloWorld.RESOURCE_TYPE)))
				return (AdapterType) new HelloWorld(resource);
		} 
		catch (Exception e) 
		{
			logger.warn("An error occured while resolving {0}", type, e);
		}
		return null;
	}

}
