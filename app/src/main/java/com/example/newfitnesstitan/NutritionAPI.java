package com.example.newfitnesstitan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NutritionAPI {
    @GET
    Call<Nutrition> getNutrition(
            @Url String url
    );

}



