/*
 * Copyright {YEAR}, Geocent LLC
 */

package com.geocent.wimb.recognition.model;

import java.util.Calendar;

/**
 * TODO: Class Description
 *
 * @author: Ben Burns
 */

public class Note {
    public static final int NO_THREAT = 0;
    public static final int THREAT_LEVEL_UNKNOWN = 1;
    public static final int THREAT = 2;

    private String lat;
    private String lon;
    private Calendar timestamp;
    private int threatLevel;
    private String details;


    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public int getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(int threatLevel) {
        this.threatLevel = threatLevel;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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

