package com.geocent.codeathon.bio.info.datastore;

import com.geocent.codeathon.bio.info.model.Person;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 *
 * @author aaronwhitney
 */
@Component
public class Database {

	private static Map<UUID, Person> PEOPLE = new HashMap<UUID, Person>();
	private static Database db;

	private Database() {
		Person ben = new Person();
		UUID benId = UUID.fromString("ced5de28-8618-45db-b99c-cd9c4f8fb7dc");
		ben.setId(benId);
		ben.setName("Ben Burns");
		PEOPLE.put(benId, ben);
	}

	public static Database instance() {
		if (null == db) {
			db = new Database();
		}
		return db;
	}

	public String create(Person person) {
		return persist(person);
	}
	
	public void save(Person person) {
		persist(person);
	}

	private String persist(Person person) {
		UUID uuid = person.getId();
		if (null == uuid) {
			uuid = UUID.randomUUID();
			person.setId(uuid);
		} 
		PEOPLE.put(uuid, person);
		return uuid.toString();
	}
	
	public Person getPersonBy(String id) {
		UUID uuid = UUID.fromString(id);
		Person person = PEOPLE.get(uuid);
		return person;
	}
	
	public Collection<Person> getPeople() {
		return PEOPLE.values();
	}
}
