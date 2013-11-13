package com.geocent.codeathon.bio.info.enums;

/**
 *
 * @author aaronwhitney
 */
public enum ThreatLevel {
	UNKNOWN("Person is unknown"),
	TERRORIST("This person is a known terrorist"),
	SAFE("This person has been evaluated and is not a threat"),
	OFFICER("This person is an officer and you should solute");
	
	private String message;
	
	ThreatLevel(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
