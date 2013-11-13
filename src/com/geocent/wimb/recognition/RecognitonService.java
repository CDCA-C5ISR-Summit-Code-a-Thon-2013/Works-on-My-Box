/*
 * Copyright {YEAR}, Geocent LLC
 */

package com.geocent.wimb.recognition;

import android.net.Uri;
import com.geocent.wimb.recognition.model.Note;
import com.geocent.wimb.recognition.model.RecognitionResult;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rekognition.RekoSDK;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * TODO: Class Description
 *
 * @author: Ben Burns
 */

public class RecognitonService {

    public static final String serviceURL = "http://162.243.83.189/bio-service/services/";
    /**
     * Pass an instance of this to doRecognition
     */
    public static abstract class Callback {
        /**
         * on success this is called with the list of results
         *
         * result list will be empty if no match found
         *
         * @param results
         */
        abstract void onSuccess(List<RecognitionResult> results);

        /**
         * On error, this will be called with a string describing the error
         * which can be shown to the user
         *
         * @param errorMessage
         */
        abstract void onError(String errorMessage);
    }

    /**
     * This actually does the image recognition and performs the lookup on the
     * resultant entities which are found
     *
     * @param fileUri
     * @param callback
     */
    public static void doRecognition(Uri fileUri, final Callback callback) throws IOException {
        RandomAccessFile f = new RandomAccessFile(fileUri.toString().replaceAll("file://",""), "r");
        byte[] bytes = new byte[(int)f.length()];
        f.read(bytes);

        RekoSDK.face_recognize(bytes, new RekoSDK.APICallback() {

            @Override
            public void gotResponse(String sResponse) {
                JSONObject response = null;
                JSONObject usage = null;
                String status = null;
                try {
                    response = new JSONObject(sResponse);
                    usage = response.getJSONObject("usage");
                    status = usage.getString("status");
                } catch (JSONException e) {
                    callback.onError("Improperly formatted response received from recognition server. Please try again.");
                    return;
                }

                ArrayList<RecognitionResult> resultsList = new ArrayList<RecognitionResult>();
                if(status.equals("Succeed.")){
                    try{
                        JSONArray resultsArr = response.getJSONArray("face_detection");
                        if(resultsArr.length() != 0){
                            for(int i=0;i<resultsArr.length();i++){
                                HttpResponse<String> svcResponse = Unirest.get(serviceURL + "suspect").asString();
                                JSONObject imgSVCResponse = new JSONObject(svcResponse.getBody());
                                RecognitionResult recResult = new RecognitionResult();

                                recResult.setId(UUID.fromString(imgSVCResponse.getString("id")));
                                recResult.setName(imgSVCResponse.getString("name"));
                                recResult.setDescription(imgSVCResponse.getString("description"));

                                JSONArray aliases = imgSVCResponse.getJSONArray("aliases");

                                for(int j=0;j<aliases.length();j++){
                                    recResult.getAliases().add(aliases.getString(j));
                                }

                                JSONArray notes = imgSVCResponse.getJSONArray("notes");

                                for(int j=0;j<aliases.length();j++){
                                    Note note = new Note();
                                    JSONObject noteObj = notes.getJSONObject(j);
                                    note.setDetails(noteObj.getString("details"));
                                    note.setLat(noteObj.getString("lat"));
                                    note.setLon(noteObj.getString("lon"));

                                    Calendar timestamp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                                    timestamp.setTimeInMillis(noteObj.getLong("timestamp"));
                                    note.setTimestamp(timestamp);

                                    String threat = noteObj.getString("threat_level");

                                    if(threat.equals("none")){
                                        note.setThreatLevel(Note.NO_THREAT);
                                    }else if(threat.equals("unknown")){
                                        note.setThreatLevel(Note.THREAT_LEVEL_UNKNOWN);
                                    }else if(threat.equals("threat")){
                                        note.setThreatLevel(Note.THREAT);
                                    }

                                    recResult.getNotes().add(note);

                                }

                                resultsList.add(recResult);
                            }

                            callback.onSuccess(resultsList);
                        }else{
                            callback.onSuccess(new ArrayList<RecognitionResult>(0));
                        }
                    } catch (UnirestException e) {
                        callback.onError("Error while contacting image metadata service.");
                        return;
                    } catch (JSONException e) {
                        callback.onError("Improperly formatted response received from metadata server. Please try again.");
                    }
                }else{
                    callback.onError("Error while processing image for recognition.");
                }
            }
        });
    }

    public static void confirmMatch(Uri fileURI, RecognitionResult result){
        //TODO: handle errors
        Unirest.post(serviceURL + "/image/" + result.getId())
                .field("image", new File(fileURI.toString().replaceAll("file://", "")))
        .asStringAsync();
    }
}
