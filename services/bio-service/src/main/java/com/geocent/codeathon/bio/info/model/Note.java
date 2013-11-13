package com.geocent.codeathon.bio.info.model;

import com.geocent.codeathon.bio.info.enums.ThreatLevel;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author aaronwhitney
 */
public class Note {
	private UUID id;
	private String note;
	private Date timestamp;
	private ThreatLevel threatLevel;
	private String latitude;
	private String longitude;

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
