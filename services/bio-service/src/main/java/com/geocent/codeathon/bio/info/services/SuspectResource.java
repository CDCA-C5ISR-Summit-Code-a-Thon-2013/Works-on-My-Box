package com.geocent.codeathon.bio.info.services;

import com.geocent.codeathon.bio.info.Utils.JsonHelper;
import com.geocent.codeathon.bio.info.dao.SuspectDao;
import com.geocent.codeathon.bio.info.model.Suspect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	private SuspectDao suspectDao;

	@GET
	@Path("id/{id}")
	public Suspect getSuspect(@PathParam("id") String id) {
		if (null == id) {
			return new Suspect();
		}
		Suspect person = suspectDao.getSuspect(id);
		return person;
	}
	
	@GET
	@Path("ids/{idCsv}")
	public List<Suspect> getSuspects(@PathParam("idCsv") String idCsv) {
		List<Suspect> suspects = new ArrayList<Suspect>();
		List<String> ids = Arrays.asList(idCsv.split("\\s*,\\s*"));
		for (String id: ids) {
			Suspect suspect = suspectDao.getSuspect(id);
			suspects.add(suspect);
		}
		
		return suspects;
	}
	
	@POST
	public void createSuspect(@FormParam("suspect") String jsonSuspect) {
		Suspect suspect = (Suspect) jsonHelper.convertFromJson(jsonSuspect, Suspect.class);
		String id = suspectDao.createSuspect(suspect);
	}
	
}
