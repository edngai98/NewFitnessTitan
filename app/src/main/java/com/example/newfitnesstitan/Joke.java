package com.example.newfitnesstitan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Joke {

    @SerializedName("text")
    @Expose
    private String quote;

    public String getQuote(){return quote;}

}
