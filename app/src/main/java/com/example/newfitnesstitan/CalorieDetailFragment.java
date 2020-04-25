package com.example.newfitnesstitan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalorieDetailFragment extends Fragment {
    private EditText ingredientAmount;
    private EditText servingSize;
    private TextView caloriesText, sugarText, fiberText, carbsText, fatsText;
    private Button button;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.calorie_fragment, container, false);
        button = rootView.findViewById(R.id.StartQuiz);
        caloriesText = rootView.findViewById(R.id.calories);
        sugarText = rootView.findViewById(R.id.sugar);
        fiberText = rootView.findViewById(R.id.fiber);
        carbsText = rootView.findViewById(R.id.carbohydrates);
        fatsText = rootView.findViewById(R.id.fats);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientAmount = rootView.findViewById(R.id.Ingredient);
                servingSize = rootView.findViewById(R.id.serving);
                String serving = ingredientAmount.getText().toString();
                String ingredient = servingSize.getText().toString();
                String baseurl = "/api/nutrition-data?app_id=acd388f3&app_key=9036e2bad6d87423904f81b14bd275dd&ingr=";
                String space = "%20";
                String url = baseurl + serving + space + ingredient;
                /* Start nutrition api call */
                Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://api.edamam.com/").
                        addConverterFactory(GsonConverterFactory.create()).
                        build();
                NutritionAPI nutritionAPI = retrofit2.create(NutritionAPI.class);
                Call<Nutrition> call2 = nutritionAPI.getNutrition(url);
                call2.enqueue(new Callback<Nutrition>() {
                    @Override
                    public void onResponse(Call<Nutrition> call, Response<Nutrition> response) {
                        String nutri1 = df2.format(response.body().getHints());
                        caloriesText.setText(nutri1 + " Kcal");
                        String nutri2 = df2.format(response.body().getTotalNutrients().getKcal().getCalories());
                        fatsText.setText(nutri2 + " grams");
                        String nutri3 = df2.format(response.body().getTotalNutrients().getFiber().getCalories());
                        String nutri4 = df2.format(response.body().getTotalNutrients().getCarbs().getCalories());
                        String nutri5 = df2.format(response.body().getTotalNutrients().getSugar().getCalories());
                        System.out.println(nutri3 + "  " + nutri4 + "  " + nutri5 + " LAST 3 NUTRI TEXT");
                        fiberText.setText(nutri3 + " grams");
                        carbsText.setText(nutri4 + " grams");
                        sugarText.setText(nutri5 + " grams");
                    }

                    @Override
                    public void onFailure(Call<Nutrition> call, Throwable t) {

                    }
                });
                caloriesText.onEditorAction(EditorInfo.IME_ACTION_DONE);

            }
        });

        return rootView;
    }


}
