/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geocent.codeathon.bio.info.services;

import com.geocent.codeathon.bio.info.model.Suspect;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author aaronwhitney
 */
@Path("/items")
public class PersonsResource {

	@Context
	private UriInfo context;

	/**
	 * Creates a new instance of PersonsResource
	 */
	public PersonsResource() {
	}

	/**
	 * Retrieves representation of an instance of com.geocent.codeathon.bio.info.dao.PersonsResource
	 * @return an instance of java.lang.String
	 */
	@GET
    @Produces("application/xml")
	public String getXml() {
		return "Test";
	}

	/**
	 * POST method for creating an instance of PersonResource
	 * @param content representation for the new resource
	 * @return an HTTP response with content of the created resource
	 */
	@POST
    @Consumes("application/xml")
    @Produces("application/xml")
	public Response postXml(String content) {
		//TODO
		return Response.created(context.getAbsolutePath()).build();
	}

	/**
	 * Sub-resource locator method for {id}
	 */
	@Path("{id}")
	public Suspect getPersonItemResource(@PathParam("id") String id) {
		return PersonResource.getInstance(id);
	}
}
