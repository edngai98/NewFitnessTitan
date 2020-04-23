package com.example.newfitnesstitan;

import com.google.gson.annotations.SerializedName;

public class NutritionFood {
    @SerializedName("FAT")
    private NutritionCalories fat;

    public NutritionCalories getKcal(){return fat;}

    @SerializedName("CHOCDF")
    private NutritionCalories carbs;

    public NutritionCalories getCarbs(){return carbs;}

    @SerializedName("FIBTG")
    private NutritionCalories fiber;
    public NutritionCalories getFiber(){return fiber;}

    @SerializedName("SUGAR")
    private NutritionCalories sugar;
    public NutritionCalories getSugar(){return sugar;}
}
