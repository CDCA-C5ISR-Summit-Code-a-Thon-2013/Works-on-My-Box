package com.geocent.codeathon.bio.info.datastore;

import com.geocent.codeathon.bio.info.enums.ThreatLevel;
import com.geocent.codeathon.bio.info.model.Note;
import com.geocent.codeathon.bio.info.model.Image;
import com.geocent.codeathon.bio.info.model.Suspect;
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

	private Map<UUID, Suspect> people;

	public Database() {
		people = new HashMap<UUID, Suspect>();
		
		Suspect ben = new Suspect();
		UUID benId = UUID.fromString("ced5de28-8618-45db-b99c-cd9c4f8fb7dc");
		ben.setId(benId);
		ben.setName("Ben Burns");
		
		List<String> aliases = new ArrayList<String>();
		aliases.add("Benny");
		aliases.add("B-Money");
		ben.setAliases(aliases);

		create(ben);
		
	}

	public final String create(Suspect suspect) {
		return persist(suspect);
	}
	
	public final void save(Suspect suspect) {
		persist(suspect);
	}

	private String persist(Suspect person) {
		UUID uuid = person.getId();
		if (null == uuid) {
			uuid = UUID.randomUUID();
			person.setId(uuid);
		} 
		people.put(uuid, person);
		return uuid.toString();
	}
	
	public final Suspect getSuspectBy(String id) {
		UUID uuid = UUID.fromString(id);
		Suspect person = people.get(uuid);
		return person;
	}
	
	public Collection<Suspect> getSuspects() {
		return people.values();
	}
	
}
