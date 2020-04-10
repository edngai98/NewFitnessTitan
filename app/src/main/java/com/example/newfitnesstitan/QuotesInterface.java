package com.example.newfitnesstitan;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuotesInterface {

    @GET ("/api/qotd")
    Call<Quotes> getQuotes();


}
