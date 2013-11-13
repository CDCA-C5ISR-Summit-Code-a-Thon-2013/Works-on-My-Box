package com.geocent.codeathon.bio.info.model;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author aaronwhitney
 */
public class Person {
	private UUID id;
	private String name;
	private List<String> aliases;
	private List<Note> notes;
	private String bio;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
}
