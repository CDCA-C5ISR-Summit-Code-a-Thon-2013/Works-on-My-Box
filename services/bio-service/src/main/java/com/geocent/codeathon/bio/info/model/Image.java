package com.geocent.codeathon.bio.info.model;

import java.util.Date;

/**
 *
 * @author aaronwhitney
 */
public class Image {
	private String url;
	private Date timestamp;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
