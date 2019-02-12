package com.example.jesunn.twinez;

public class model_developers {

    private int devImage;
    private String devName;
    private String devProgram;
    private String devEmail;

    public model_developers(int devImage,String devName,String devProgram, String devEmail){
        this.devImage = devImage;
        this.devName = devName;
        this.devProgram = devProgram;
        this.devEmail = devEmail;
    }

    public int getDevImage() {
        return devImage;
    }

    public void setDevImage(int devImage) {
        this.devImage = devImage;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevProgram() {
        return devProgram;
    }

    public void setDevProgram(String devProgram) {
        this.devProgram = devProgram;
    }

    public String getDevEmail() {
        return devEmail;
    }

    public void setDevEmail(String devEmail) {
        this.devEmail = devEmail;
    }
}
