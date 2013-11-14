package com.geocent.codeathon.bio.info.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author aaronwhitney
 */
public class Suspects implements Serializable{
	private static final long serialVersionUID = -3328673379291539367L;
	public Map<UUID, Suspect> suspects;
	
	public Suspects() {
		suspects = new HashMap<UUID, Suspect>();
	}
}
