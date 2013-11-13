package com.geocent.codeathon.bio.info.services;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author aaronwhitney
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
		addRestResourceClasses(resources);
		return resources;
	}

	/**
	 * Do not modify addRestResourceClasses() method.
	 * It is automatically re-generated by NetBeans REST support to populate
	 * given list with all resources defined in the project.
	 */
	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(com.geocent.codeathon.bio.info.services.PersonResource.class);
		resources.add(com.geocent.codeathon.bio.info.services.PersonsResource.class);
		resources.add(com.geocent.codeathon.bio.info.services.SuspectResource.class);
		resources.add(com.geocent.codeathon.bio.info.services.providers.GenericResponseProvider.class);
	}

}