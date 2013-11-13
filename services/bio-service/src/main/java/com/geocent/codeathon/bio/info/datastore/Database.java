package com.geocent.codeathon.bio.info.datastore;

import com.geocent.codeathon.bio.info.model.Person;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author aaronwhitney
 */
public class Database {
	public static Map<UUID, Person> PEOPLE = new HashMap<UUID, Person>();
	
	{
		Person ben = new Person();
		UUID benId = UUID.fromString("ced5de28-8618-45db-b99c-cd9c4f8fb7dc");
		ben.setId(benId);
		ben.setName("Ben Burns");
		PEOPLE.put(benId, ben);
	}
}
