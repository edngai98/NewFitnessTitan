package com.example.newfitnesstitan;

import com.google.gson.annotations.SerializedName;

public class Quotes {

    @SerializedName("value")
    private String quote;

    public Quotes(){

    }

    public Quotes(String quote) {
        this.quote = quote;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
