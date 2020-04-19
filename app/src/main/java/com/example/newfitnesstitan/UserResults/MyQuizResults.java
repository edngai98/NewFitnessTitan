package com.example.newfitnesstitan.UserResults;

//import com.google.firebase.database.Exclude;

public class MyQuizResults {

    //private String documentID;
    private String name;
    private int result;

    public MyQuizResults(){}

    //@Exclude
    /*public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }*/

    public MyQuizResults(String name, int result) {
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
