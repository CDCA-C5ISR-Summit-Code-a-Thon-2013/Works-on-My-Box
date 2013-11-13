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
	private String details;
	private Date timestamp;
	private ThreatLevel threatLevel;
	private String lat;
	private String lon;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

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

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}
}
