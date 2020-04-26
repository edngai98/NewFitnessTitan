package com.example.newfitnesstitan.Entities;

public class Leaderboard implements Comparable<Leaderboard>{
    String name;
    int result;



    public Leaderboard(String name, int result) {
        this.name = name;
        this.result = result;
    }
    public Leaderboard(){

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
    @Override
    public String toString(){
        return (this.name + " " + this.result);
    }

    @Override
    public int compareTo(Leaderboard leaderboard) {
        int compareResult = ((Leaderboard) leaderboard).getResult();

        return compareResult - this.result;
    }

}
