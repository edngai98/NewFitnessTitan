package com.example.newfitnesstitan.UserResults;


import java.util.ArrayList;
import java.util.HashMap;


public class Users  {

    private HashMap<String, Integer> quizResults = new HashMap<>();
    private String username;
    private String password;
    private String first;
    private String last;

    public Users() {

    }

    public Users(String username, String password, String first, String last) {
        this.username = username;
        this.password = password;
        this.first = first;
        this.last = last;

        this.quizResults.put("Fruit", 0);
        this.quizResults.put("Protein", 0);
        this.quizResults.put("Carbohydrate", 0);
        this.quizResults.put("Dairy", 0);
        this.quizResults.put("Vegetable", 0);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public HashMap<String, Integer> getQuizResults() {
        return quizResults;
    }

    public void setQuizResults(HashMap<String, Integer> quizResults) {
        this.quizResults = quizResults;
    }
}
