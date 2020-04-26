package com.example.newfitnesstitan.Entities;

import com.example.newfitnesstitan.NutritionFood;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nutrition {

    @SerializedName("calories")
    @Expose
    private Integer calories;

    public Integer getHints(){return calories;}

    @SerializedName("totalNutrients")
    private NutritionFood totalNutrients;

    public NutritionFood getTotalNutrients(){return totalNutrients;}


}
