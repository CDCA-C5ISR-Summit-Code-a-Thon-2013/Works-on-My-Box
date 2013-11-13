package com.geocent.codeathon.bio.info.dao;

import com.geocent.codeathon.bio.info.datastore.Database;
import com.geocent.codeathon.bio.info.model.Suspect;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author aaronwhitney
 */
@Component
public class SuspectDao {
	
	@Autowired
	Database db;
	
	public String createSuspect(Suspect suspect) {
		return db.create(suspect);
	}
	
	public Suspect getSuspect(String uuidString) {
		return db.getSuspectBy(uuidString);
	}
}
