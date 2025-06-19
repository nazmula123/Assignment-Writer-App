package com.example.assignment_writer.Download;

public class Model {
    String time,text;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Model(String time, String text){

        this.time=time;
        this.text=text;
    }


}
