package com.valtech.cq.maventest.resource;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.JSONString;
import com.day.cq.commons.jcr.JcrConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld extends ResourceWrapper implements JSONString 
{
	public final static String RESOURCE_TYPE = "valtech/maventest/components/maventest";
	
	private final static Logger logger = LoggerFactory.getLogger(HelloWorld.class); 
	
	private final ValueMap properties;
	private final String title;
	
	public HelloWorld(Resource resource) 
	{
		super(resource);
		properties = this.adaptTo(ValueMap.class);
		title = properties.get(JcrConstants.JCR_TITLE, String.class);
	}
	
	public String getTitle()
	{
		return this.title;
	}

	@Override
	public String toJSONString() 
	{
		try 
		{
			JSONObject json = new JSONObject();
			if (this.getTitle()!=null)
				json.put("title", this.getTitle());
			return json.length()==0?null:json.toString();
		} 
		catch (JSONException e) 
		{
			logger.warn("Unable to serialize in JSON", e);
		}
		return null;
	}

}
