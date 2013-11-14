package com.geocent.codeathon.bio.info.services;

import com.geocent.codeathon.bio.info.Utils.JsonHelper;
import com.geocent.codeathon.bio.info.dao.SuspectDao;
import com.geocent.codeathon.bio.info.model.Image;
import com.geocent.codeathon.bio.info.model.Suspect;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
@Path("image")
@Component
public class ImageResource {

    @Autowired
    private JsonHelper jsonHelper;
	
	@Autowired
	private SuspectDao suspectDao;

	@POST
	@Path("{suspectId}")
	public String addImage(@PathParam("suspectId") String suspectId, @FormParam("image") String imageString) {
		Suspect suspect = suspectDao.getSuspect(suspectId);
		Image image = new Image();
		image.setTimestamp(new Date());
		UUID uuid = UUID.randomUUID();
		image.setId(uuid);
		List<Image> images = suspect.getImages();
		if (null == images) {
			images = new ArrayList<Image>();
		}
		images.add(image);
		suspect.setImages(images);
		suspectDao.saveSuspect(suspect);
		return uuid.toString();
	}
}
