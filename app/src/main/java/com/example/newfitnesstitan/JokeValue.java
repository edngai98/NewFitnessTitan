package com.example.newfitnesstitan;

import com.google.gson.annotations.SerializedName;

public class JokeValue {
    @SerializedName("quoteText")
    private String body;
    public String getInspired(){return body;}

}
