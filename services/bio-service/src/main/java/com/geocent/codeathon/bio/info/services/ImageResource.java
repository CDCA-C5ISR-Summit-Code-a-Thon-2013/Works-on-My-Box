package com.geocent.codeathon.bio.info.services;

import com.geocent.codeathon.bio.info.Utils.JsonHelper;
import com.geocent.codeathon.bio.info.dao.SuspectDao;
import com.geocent.codeathon.bio.info.model.Image;
import com.geocent.codeathon.bio.info.model.Suspect;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author aaronwhitney
 */
@Path("image")
@Component
public class ImageResource {

	@Context
	private UriInfo uriInfo;
	@Autowired
	private SuspectDao suspectDao;

	@POST
	@Path("{suspectId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String addImage(@PathParam("suspectId") String suspectId,
			@FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition fileDetail) {

		StringBuilder uploadedFileLocation = new StringBuilder("images");
		uploadedFileLocation.append(File.separator).append(fileDetail.getFileName());
		writeToFile(uploadedInputStream, uploadedFileLocation.toString());
		StringBuilder url = new StringBuilder(uriInfo.getPath());
		url.append(File.separator).append(uploadedFileLocation);

		Suspect suspect = suspectDao.getSuspect(suspectId);
		Image image = new Image();
		image.setUrl(url.toString());
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

	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(ImageResource.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ImageResource.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				out.close();
			} catch (IOException ex) {
				Logger.getLogger(ImageResource.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
