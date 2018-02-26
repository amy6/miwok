package com.example.android.miwok;

/**
 * Created by mahima on 8/2/18.
 */

public class Word {

    private String defaultTranslation;
    private String miwokTranslation;
    private int imageId = NO_IMAGE_PRESENT;
    private int audioID;

    private static final int NO_IMAGE_PRESENT = -1;

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceID) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.audioID = audioResourceID;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imgResourceId, int audioResourceID) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.imageId = imgResourceId;
        this.audioID = audioResourceID;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public int getImageId() {
        return imageId;
    }

    public int getAudioID() {
        return audioID;
    }

    public boolean hasImage() {
        return imageId != NO_IMAGE_PRESENT;
    }
}
