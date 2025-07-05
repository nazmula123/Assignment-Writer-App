package com.example.assignment_writer.Download;

public class DownloadModel {

 private String  projectType,type,title,character,language,date,time,key;


 public DownloadModel(){};

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

   public DownloadModel(String projectType, String type, String title, String character, String language, String date, String time, String key){
     this.projectType=projectType;
     this.type=type;
     this.title=title;
     this.character=character;
     this.language=language;
     this.date=date;
     this.time=time;
     this.key=key;
 }
}
