package com.geocent.codeathon.bio.info.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author aaronwhitney
 */
public class Person {
	private UUID id;
	private String name;
	private List<String> aliases;
	private Map<UUID, Encounter> encounters;
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

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<Encounter> getEncounters() {
		List<Encounter> encounters = new ArrayList<Encounter>();
		encounters.addAll(this.encounters.values());
		return encounters;
	}

	public void setEncounters(List<Encounter> encounters) {
		if (null == encounters) {
			this.encounters = new HashMap<UUID, Encounter>();
		}
		for (Encounter encounter: encounters) {
			UUID uuid = encounter.getId();
			if (null == uuid) {
				uuid = UUID.randomUUID();
			}
			this.encounters.put(uuid, encounter);
		}
	}
	
	public void addEncounters(Encounter encounter) {
		if (null == encounters) {
			this.encounters = new HashMap<UUID, Encounter>();
		}
		this.encounters.put(encounter.getId(),encounter);
	}
}
