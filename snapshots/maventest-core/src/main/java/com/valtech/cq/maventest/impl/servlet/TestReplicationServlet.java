package com.valtech.cq.maventest.impl.servlet;

import java.io.IOException;
import java.util.Iterator;

import javax.jcr.Session;
import javax.jcr.Node;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.json.io.JSONWriter;


import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.Replicator;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.AgentFilter;
import com.day.cq.replication.Agent;
import com.day.cq.replication.AgentManager;

import org.apache.sling.jcr.api.SlingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo servlet for maventest
 * 
 * @author vivek
 */
@SlingServlet(
		methods="GET",
		paths="/bin/uat/replicate"
)
public class TestReplicationServlet extends SlingSafeMethodsServlet 
{
	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(TestReplicationServlet.class);
	
	@Reference
	private Replicator replicator;
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private AgentManager agentManager;
	
	private Session session;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException
	{		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		//String pathToActivate = "/content/vivek";
		try 
		{
			String pathToActivate = request.getParameter("path");
			if(!"".equals(pathToActivate) || pathToActivate!=null){
				session = repository.loginAdministrative(null);
				ReplicationOptions opts = new ReplicationOptions();
				opts.setSuppressStatusUpdate(true);
				opts.setFilter(
						       new AgentFilter(){
						    	   String replicationAgentName = "agent-uat";
						    	   public boolean isIncluded(final Agent agent) {
						    		   return replicationAgentName.equals(agent.getId());
						    	   }
						       }
				);
				activate(pathToActivate, request, opts);
			}
		}
		catch (Exception e) 
		{
			log.error("An error occured", e);
			response.sendError(500, e.getMessage());
		}finally{
			
			if(session!=null)
				session.logout();
		}
	}
	
	protected void activate(String path, SlingHttpServletRequest request, ReplicationOptions opts){
		
		ResourceResolver resourceResolver = request.getResourceResolver();
		Resource resource = resourceResolver.getResource(path);
		Iterator<Resource> iterator = resourceResolver.listChildren(resource);
        while (iterator.hasNext()) {
        	String contentPath = iterator.next().getPath();
	         try{
	        	  replicator.replicate(session, ReplicationActionType.ACTIVATE, contentPath, opts);
	        	  activate(contentPath, request, opts);
	         }catch(Exception e) {
	        	 e.printStackTrace();
	         }
         }
     }
}