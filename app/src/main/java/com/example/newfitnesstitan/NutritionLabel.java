package com.example.newfitnesstitan;

import com.google.gson.annotations.SerializedName;

public class NutritionLabel {
    @SerializedName("foodId")
    private NutritionCalories nutrients;

    public NutritionCalories getNutrients(){return nutrients;}
}
