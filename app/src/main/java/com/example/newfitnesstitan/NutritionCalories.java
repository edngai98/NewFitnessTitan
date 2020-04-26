package com.example.newfitnesstitan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//Get second layer of API call with getCalories() method
public class NutritionCalories {
    @SerializedName("quantity")
    @Expose
    private float calories;

    public float getCalories(){return calories;}
}
