package com.example.newfitnesstitan;

import com.google.gson.annotations.SerializedName;

//Get first layer of Nutrition API call

public class NutritionLabel {
    @SerializedName("foodId")
    private NutritionCalories nutrients;

    public NutritionCalories getNutrients(){return nutrients;}
}
