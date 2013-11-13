package com.geocent.codeathon.bio.info.model;

import com.geocent.codeathon.bio.info.enums.ThreatLevel;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author aaronwhitney
 */
public class Encounter {
	private UUID id;
	private String note;
	private Date timestamp;
	private ThreatLevel threatLevel;
	private List<Image> images;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public ThreatLevel getThreatLevel() {
		return threatLevel;
	}

	public void setThreatLevel(ThreatLevel threatLevel) {
		this.threatLevel = threatLevel;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
