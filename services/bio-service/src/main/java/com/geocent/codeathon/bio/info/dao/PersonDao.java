package com.geocent.codeathon.bio.info.dao;

import com.geocent.codeathon.bio.info.datastore.Database;
import com.geocent.codeathon.bio.info.model.Person;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author aaronwhitney
 */
@Component
public class PersonDao {
	
	@Autowired
	Database db;
	
	public String createPerson(Person person) {
		return db.create(person);
	}
	
	public Person getPerson(String uuidString) {
		return db.getPersonBy(uuidString);
	}
}
