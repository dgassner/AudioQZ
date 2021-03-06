package com.davidgassner.audioqz.model;

public class Cue {

    public static final String CUE_TYPE_AUDIO = "AUDIO";
    public static final String CUE_TYPE_STOP = "STOP";
    public static final String CUE_TYPE_FADE = "FADE";

    private int cueId;
    private int cueIndex;
    private String cueNumber;
    private String title;
    private String targetFile;
    private String type;

    public Cue() {
    }

    public int getCueId() {
        return cueId;
    }

    public void setCueId(int cueId) {
        this.cueId = cueId;
    }

    public int getCueIndex() {
        return cueIndex;
    }

    public void setCueIndex(int cueIndex) {
        this.cueIndex = cueIndex;
    }

    public String getCueNumber() {
        return cueNumber;
    }

    public void setCueNumber(String cueNumber) {
        this.cueNumber = cueNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Cue{" +
                "cueIndex=" + cueIndex +
                ", targetFile='" + targetFile + '\'' +
                ", cueNumber='" + cueNumber + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}