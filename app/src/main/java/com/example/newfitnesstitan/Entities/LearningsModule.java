package com.example.newfitnesstitan.Entities;

public class LearningsModule {
    private String description;
    private String name;
    private String summary;
    private String image;


    public LearningsModule(String description, String name, String summary, String image) {
        this.description = description;
        this.name = name;
        this.summary = summary;
        this.image = image;
    }
    public LearningsModule(){

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
