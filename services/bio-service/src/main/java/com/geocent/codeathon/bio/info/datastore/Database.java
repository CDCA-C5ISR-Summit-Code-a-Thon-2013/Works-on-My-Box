package com.geocent.codeathon.bio.info.datastore;

import com.geocent.codeathon.bio.info.enums.ThreatLevel;
import com.geocent.codeathon.bio.info.model.Note;
import com.geocent.codeathon.bio.info.model.Suspect;
import java.util.ArrayList;
import java.util.Collection;
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
		
		Suspect ben = createMockSuspect("ced5de28-8618-45db-b99c-cd9c4f8fb7dc", "Ben");
		create(ben);
		
		Suspect john = createMockSuspect("07969aa2-b3bd-4177-a13f-09e27f7a631d", "John");
		create(john);
		
		Suspect aaron = createMockSuspect("982c4a67-bb39-41f9-9872-88356822b7ad", "Aaron");
		create(aaron);
		
	}
	
	public static Suspect createMockSuspect(String uuid, String name) {
		Suspect suspect = new Suspect();
		UUID id = UUID.fromString(uuid);
		suspect.setId(id);
		suspect.setName(name);
		
		List<String> aliases = new ArrayList<String>();
		aliases.add(name+"y");
		aliases.add(name.substring(0, 1)+"-Money");
		suspect.setAliases(aliases);

		Note note = createMockNote(name+"'s note", ThreatLevel.SAFE, "1", "2");
		List<Note> notes = new ArrayList<Note>();
		notes.add(note);
		suspect.setNotes(notes);
		
		return suspect;
	}

	
	public static Note createMockNote(String details, ThreatLevel threat, String lat, String lon) {
		Note note = new Note();
		note.setId(UUID.randomUUID());
		note.setDetails(details);
		note.setThreatLevel(threat);
		note.setLat(lat);
		note.setLon(lon);
		return note;
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
