package com.geocent.codeathon.bio.info.services;

import com.geocent.codeathon.bio.info.Utils.JsonHelper;
import com.geocent.codeathon.bio.info.dao.SuspectDao;
import com.geocent.codeathon.bio.info.model.Note;
import com.geocent.codeathon.bio.info.model.Suspect;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author aaronwhitney
 */
@Path("note")
@Component
public class NoteResource {

    @Autowired
    private JsonHelper jsonHelper;
	
	@Autowired
	private SuspectDao suspectDao;


	@POST
	@Path("{suspectId}")
	public void createNote(@PathParam("suspectId") String suspectId, @FormParam("note") String jsonNote) {
		Suspect suspect = suspectDao.getSuspect(suspectId);
		Note note = (Note)jsonHelper.convertFromJson(jsonNote, Note.class);
		List<Note> notes = suspect.getNotes();
		if (null == notes) {
			notes = new ArrayList<Note>();
		}
		notes.add(note);
		suspect.setNotes(notes);
		suspectDao.saveSuspect(suspect);
	}
}
