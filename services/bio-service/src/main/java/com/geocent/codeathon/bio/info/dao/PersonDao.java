package com.geocent.codeathon.bio.info.dao;

import com.geocent.codeathon.bio.info.datastore.Database;
import com.geocent.codeathon.bio.info.model.Person;
import java.util.UUID;

/**
 *
 * @author aaronwhitney
 */
public class PersonDao {
	public static String createPerson(Person person) {
		UUID uuid = person.getId();
		if (null == uuid) {
			uuid = UUID.randomUUID();
		}
		Database.PEOPLE.put(uuid, person);
		return uuid.toString();
	}
	
	public Person getPerson(String uuidString) {
		UUID uuid = UUID.fromString(uuidString);
		Person person = Database.PEOPLE.get(uuid);
		return person;
	}
}
