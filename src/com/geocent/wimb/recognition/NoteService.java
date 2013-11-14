/*
 * Copyright {YEAR}, Geocent LLC
 */

package com.geocent.wimb.recognition;

import com.geocent.wimb.recognition.model.Note;
import com.mashape.unirest.http.Unirest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

/**
 * TODO: Class Description
 *
 * @author: Ben Burns
 */

public class NoteService {

    public static final String serviceURL = "http://162.243.83.189/bio-service/services/";

    public void saveNote(Note note, UUID id){

        JSONObject obj = new JSONObject();

        try {
            obj.put("lat", note.getLat());
            obj.put("lon", note.getLon());

            Calendar timestamp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            obj.put("timestamp", timestamp);
            obj.put("details", note.getDetails());

            switch(note.getThreatLevel()){
                case Note.NO_THREAT:
                    obj.put("threat_level", "none");
                    break;

                case Note.THREAT_LEVEL_UNKNOWN:
                    obj.put("threat_level", "unknown");
                    break;

                case Note.THREAT:
                    obj.put("threat_level", "threat");
                    break;
            }

            //TODO: handle errors to this call, or something...
            Unirest.post(serviceURL + "/note/" + id.toString()).body(obj.toString(4)).asStringAsync();

        } catch (JSONException e) {
            //TODO: this is bad, mmkay? Don't do this at home, kids.
            e.printStackTrace();
        }

    }
}
