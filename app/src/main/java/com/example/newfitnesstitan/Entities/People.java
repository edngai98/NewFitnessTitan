package com.example.newfitnesstitan.Entities;

import java.util.HashMap;
//Renamed to users later

public class People {
    String first;
    String last;
    String password;
    HashMap<String, Integer> quizResults = new HashMap<>();
    String username;

    public People(String first, String last, String password, HashMap<String, Integer> quizResults, String username){
        this.first = first;
        this.last = last;
        this.password = password;
        this.quizResults = quizResults;
        this.username = username;

    }
    public People(){

    }


    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, Integer> getQuizResults() {
        return quizResults;
    }

    public void setQuizResults(HashMap<String, Integer> quizResults) {
        this.quizResults = quizResults;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
