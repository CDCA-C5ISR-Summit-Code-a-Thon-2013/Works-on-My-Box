/*
 * Copyright {YEAR}, Geocent LLC
 */

package com.geocent.wimb.recognition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * TODO: Class Description
 *
 * @author: Ben Burns
 */

public class RecognitionService {

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
        public abstract void onSuccess(List<RecognitionResult> results);

        /**
         * On error, this will be called with a string describing the error
         * which can be shown to the user
         *
         * @param errorMessage
         */
        public abstract void onError(String errorMessage);
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }

        return 0;
    }

    /**
     * This actually does the image recognition and performs the lookup on the
     * resultant entities which are found
     *
     * @param fileUri
     * @param callback
     */
    public static void doRecognition(Uri fileUri, final Callback callback) throws IOException {

        String path = fileUri.toString().replaceAll("file://", "");
        /*RandomAccessFile file = new RandomAccessFile(path, "r");

        byte bytes[] = new byte[(int)file.length()];
        file.read(bytes);

        ExifInterface exif = new ExifInterface(path);

        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int rotationInDegrees = exifToDegrees(rotation);

        Matrix matrix = new Matrix();
        if (rotation != 0f) {matrix.preRotate(rotationInDegrees);};*/

        Bitmap bi = BitmapFactory.decodeFile(path);

        /*Matrix matrix = new Matrix();
        matrix.setScale(-1,1);
        matrix.postTranslate(bi.getWidth(),0);

        int width = bi.getWidth();
        int height = bi.getHeight();

        bi = Bitmap.createBitmap(bi, 0, 0, width, height, matrix, true);*/

        /*bi = Bitmap.createBitmap(bi, 0, 0, width, height, matrix, true);

        double scale;

        if(width > height){
            scale = 800.0/width;
        }else{
            scale = 800.0/height;
        }

        bi = Bitmap.createScaledBitmap(bi, (int)Math.round(width*0.5), (int)Math.round(height*0.5), false);*/

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bi.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] bytes = stream.toByteArray();

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

                String pretty = "";
                try {
                    pretty = response.toString(4);
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                ArrayList<RecognitionResult> resultsList = new ArrayList<RecognitionResult>();
                if(status.equals("Succeed.")){
                    try{
                        JSONArray resultsArr = response.getJSONArray("face_detection");
                        if(resultsArr.length() != 0){
                            for(int i=0;i<resultsArr.length();i++){
                                JSONObject detection = resultsArr.getJSONObject(i);
                                if(!detection.has("matches")){
                                    continue;
                                }

                                JSONArray matchesArray = detection.getJSONArray("matches");
                                for(int k=0;k<matchesArray.length();k++){
                                    JSONObject match = matchesArray.getJSONObject(k);

                                    if(Double.parseDouble(match.getString("score")) < 0.03){
                                        continue;
                                    }

                                    HttpResponse<String> svcResponse = Unirest.get(serviceURL + "suspect/" + match.getString("tag")).asString();
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
