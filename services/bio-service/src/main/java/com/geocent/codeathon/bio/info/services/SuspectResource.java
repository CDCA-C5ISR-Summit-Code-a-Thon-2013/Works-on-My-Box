package com.geocent.codeathon.bio.info.services;

import com.geocent.codeathon.bio.info.Utils.JsonHelper;
import com.geocent.codeathon.bio.info.dao.PersonDao;
import com.geocent.codeathon.bio.info.model.Person;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author aaronwhitney
 */
@Path("suspect")
@Component
public class SuspectResource {

    @Autowired
    private JsonHelper jsonHelper;
	
	@Autowired
	private PersonDao personDao;

    private Logger logger = Logger.getLogger(this.getClass());   

	@GET
	@Path("id/{id}")
	public Person getPerson(@PathParam("id") String id) {
		if (null == id) {
			return new Person();
		}
		Person person = personDao.getPerson(id);
		return person;
	}
}
