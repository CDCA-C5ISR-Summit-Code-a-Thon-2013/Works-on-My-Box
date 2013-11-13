/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geocent.codeathon.bio.info.services;

import com.geocent.codeathon.bio.info.dao.PersonDao;
import com.geocent.codeathon.bio.info.model.Person;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;

/**
 * REST Web Service
 *
 * @author aaronwhitney
 */
public class PersonResource {

	private String id;
	private Person person;
	
	/**
	 * Creates a new instance of PersonResource
	 */
	private PersonResource(String id) {
		this.id = id;
	}

	/**
	 * Get instance of the PersonResource
	 */
	public static Person getInstance(String id) {
		PersonDao personDao = new PersonDao();
		Person person = personDao.getPerson(id);
		return person;
	}

	/**
	 * Retrieves representation of an instance of com.geocent.codeathon.bio.info.dao.PersonResource
	 * @return an instance of java.lang.String
	 */
	@GET
    @Produces("application/xml")
	public String getXml() {
		//TODO return proper representation object
		throw new UnsupportedOperationException();
	}

	/**
	 * PUT method for updating or creating an instance of PersonResource
	 * @param content representation for the resource
	 * @return an HTTP response with content of the updated or created resource.
	 */
	@PUT
    @Consumes("application/xml")
	public void putXml(String content) {
	}

	/**
	 * DELETE method for resource PersonResource
	 */
	@DELETE
	public void delete() {
	}
}
