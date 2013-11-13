package com.geocent.codeathon.bio.info.datastore;

import com.geocent.codeathon.bio.info.enums.ThreatLevel;
import com.geocent.codeathon.bio.info.model.Encounter;
import com.geocent.codeathon.bio.info.model.Image;
import com.geocent.codeathon.bio.info.model.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 *
 * @author aaronwhitney
 */
@Component
public class Database {

	private Map<UUID, Person> people;

	public Database() {
		people = new HashMap<UUID, Person>();
		
		Person ben = new Person();
		UUID benId = UUID.fromString("ced5de28-8618-45db-b99c-cd9c4f8fb7dc");
		ben.setId(benId);
		ben.setName("Ben Burns");
		
		List<String> aliases = new ArrayList<String>();
		aliases.add("Benny");
		aliases.add("B-Money");
		ben.setAliases(aliases);

		create(ben);
		
		createEncounter(ben.getId().toString(), "1st encounter", ThreatLevel.UNKNOWN, null);
//		createEncounter(ben.getId().toString(), "2nd encounter", ThreatLevel.SAFE, null);
		
	}

	public final String create(Person person) {
		return persist(person);
	}
	
	public final void save(Person person) {
		persist(person);
	}

	private String persist(Person person) {
		UUID uuid = person.getId();
		if (null == uuid) {
			uuid = UUID.randomUUID();
			person.setId(uuid);
		} 
		people.put(uuid, person);
		return uuid.toString();
	}
	
	public final Person getPersonBy(String id) {
		UUID uuid = UUID.fromString(id);
		Person person = people.get(uuid);
		return person;
	}
	
	public Collection<Person> getPeople() {
		return people.values();
	}
	
	public final Encounter createEncounter(String personId, String note, ThreatLevel threatLevel, List<Image> images) {
		UUID uuid = UUID.randomUUID();
		Encounter encounter = new Encounter();
		encounter.setId(uuid);
		encounter.setTimestamp(new Date());
		
		encounter.setNote(note);
		encounter.setThreatLevel(threatLevel);
		if (null == images) {
			images = new ArrayList<Image>();
		}
		encounter.setImages(images);
		
		UUID personUUID = UUID.fromString(personId);
		Person person  = this.people.get(personUUID);
		person.addEncounters(encounter);
		
		return encounter;
	}
	
}
