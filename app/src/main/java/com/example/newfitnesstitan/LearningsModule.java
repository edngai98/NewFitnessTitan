package com.example.newfitnesstitan;

public class LearningsModule {
    private String description;
    private String name;
    private String summary;


    public LearningsModule(String description, String name, String summary) {
        this.description = description;
        this.name = name;
        this.summary = summary;
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
}
