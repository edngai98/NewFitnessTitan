package com.example.newfitnesstitan;

public class Quizzes {

    private String name;
    private String description;

    public Quizzes () {

    }

    public Quizzes(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
