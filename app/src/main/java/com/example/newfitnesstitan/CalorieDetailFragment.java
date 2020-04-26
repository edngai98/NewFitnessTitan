package com.example.newfitnesstitan;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newfitnesstitan.Entities.Nutrition;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalorieDetailFragment extends Fragment {
    //Nutrition Calculator Detail Fragment starts here
    private EditText ingredientAmount;
    private EditText servingSize;
    private TextView caloriesText, sugarText, fiberText, carbsText, fatsText;
    private Button button;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private String serving, ingredient, baseUrl, space, url, nutri1, nutri2, nutri3,nutri4,nutri5;

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
        ingredientAmount = rootView.findViewById(R.id.Ingredient);
        servingSize = rootView.findViewById(R.id.serving);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serving = ingredientAmount.getText().toString();
                ingredient = servingSize.getText().toString();
                //Url has to be combined as the API call has a specific format
                baseUrl = "/api/nutrition-data?app_id=acd388f3&app_key=9036e2bad6d87423904f81b14bd275dd&ingr=";
                space = "%20";
                url = baseUrl + serving + space + ingredient;

                /* Start nutrition api call */
                Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://api.edamam.com/").
                        addConverterFactory(GsonConverterFactory.create()).
                        build();
                NutritionAPI nutritionAPI = retrofit2.create(NutritionAPI.class);
                Call<Nutrition> call2 = nutritionAPI.getNutrition(url);
                call2.enqueue(new Callback<Nutrition>() {
                    @Override
                    //Return the response as a list of type Nutrition
                    public void onResponse(Call<Nutrition> call, Response<Nutrition> response) {
                        //Checking if there is an empty response from the API Call
                        if (response.body().getHints() != 0) {
                            System.out.println(response.body().getHints() + " THIS IS THE RESPONSE");
                            nutri1 = df2.format(response.body().getHints());
                            caloriesText.setText(nutri1 + " Kcal");
                            nutri2 = df2.format(response.body().getTotalNutrients().getKcal().getCalories());
                            fatsText.setText(nutri2 + " grams");
                            //Try catch blocks to check if the API call returns PARTIAL information
                            try {
                                nutri3 = df2.format(response.body().getTotalNutrients().getFiber().getCalories());
                            } catch (NullPointerException e) {
                                nutri3 = "N/A";
                            }
                            try {
                                nutri4 = df2.format(response.body().getTotalNutrients().getCarbs().getCalories());
                            } catch (NullPointerException e) {
                                nutri4 = "N/A";
                            }
                            try {
                                nutri5 = df2.format(response.body().getTotalNutrients().getSugar().getCalories());
                            } catch (NullPointerException e) {
                                nutri5 = "N/A";
                            }
                            fiberText.setText(nutri3 + " grams");
                            carbsText.setText(nutri4 + " grams");
                            sugarText.setText(nutri5 + " grams");


                        } else {
                            //Error alert that is sent to user when incorrect ingredients are entered
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                            View mView = getLayoutInflater().inflate(R.layout.calorie_popup, null);
                            Button mAlert = mView.findViewById(R.id.btnAlert);
                            mBuilder.setView(mView);
                            AlertDialog dialog = mBuilder.create();
                            dialog.show();
                            mAlert.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.hide();
                                }
                            });
                            caloriesText.setText("N/A");
                            fatsText.setText("N/A");
                            fiberText.setText("N/A");
                            carbsText.setText("N/A");
                            sugarText.setText("N/A");

                        }

                    }

                    @Override
                    public void onFailure(Call<Nutrition> call, Throwable t) {
                        System.out.println("Enqueue method returned a failure " + t.toString());
                    }
                });
                //Close the keyboard when user clicks generate after entering a number for serving size
                caloriesText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                sugarText.onEditorAction(EditorInfo.IME_ACTION_DONE);

            }
        });

        return rootView;
    }

}
