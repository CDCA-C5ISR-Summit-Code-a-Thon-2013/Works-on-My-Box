/*
 * Copyright {YEAR}, Geocent LLC
 */

package com.geocent.wimb.recognition.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * TODO: Class Description
 *
 * @author: Ben Burns
 */

public class RecognitionResult {
    private UUID id;
    private String name;
    private List<String> aliases = null;
    private String description;
    private List<Note> notes = null;
    private List<Bitmap> images = null;

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
        if(aliases == null){
            aliases = new ArrayList<String>();
        }

        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Note> getNotes() {
        if(notes == null){
            notes = new ArrayList<Note>();
        }

        return notes;
    }

    public List<Bitmap> getImages() {
        if(images == null){
            images = new ArrayList<Bitmap>();
        }

        return images;
    }
}
