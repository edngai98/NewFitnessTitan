package com.example.newfitnesstitan;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeAPI {

    @GET("food/jokes/random?apiKey=c63eef442d7a4d6fb11f9c0771f1ca23")
    Call<Joke> getQuote();
}
