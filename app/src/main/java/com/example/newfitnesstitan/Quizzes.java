package com.example.newfitnesstitan;

import com.google.firebase.database.Exclude;

public class Quizzes {

    private String documentID;
    private String name;
    private int result;

    public Quizzes(){}

    @Exclude
    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public Quizzes(String name, int result) {
        this.name = name;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
