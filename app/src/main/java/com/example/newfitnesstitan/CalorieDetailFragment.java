package com.example.newfitnesstitan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
    String serving, ingredient, baseUrl, space, url;

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
                baseUrl = "/api/nutrition-data?app_id=acd388f3&app_key=9036e2bad6d87423904f81b14bd275dd&ingr=";
                space = "%20";
                url = baseUrl + serving + space + ingredient;
               // new CalorieTask().execute();
//                /* Start nutrition api call */
                    Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://api.edamam.com/").
                            addConverterFactory(GsonConverterFactory.create()).
                            build();
                    NutritionAPI nutritionAPI = retrofit2.create(NutritionAPI.class);
                    Call<Nutrition> call2 = nutritionAPI.getNutrition(url);
                    call2.enqueue(new Callback<Nutrition>() {
                        @Override
                        public void onResponse(Call<Nutrition> call, Response<Nutrition> response) {
                            if ( response.body().getHints() != 0  ){
                                System.out.println(response.body().getHints() + " THIS IS THE RESPONSE");
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


                            } else {
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                                View mView = getLayoutInflater().inflate(R.layout.calorie_popup, null);
                                Button mAlert = mView.findViewById(R.id.btnAlert);
                                //Intent intentLogout = new Intent(this, LoginActivity.class);
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


                               // Toast.makeText(, "The server returned an error, please try again", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<Nutrition> call, Throwable t) {
                            System.out.println("Enqeue method returned a failure " + t.toString());
                        }
                    });


                caloriesText.onEditorAction(EditorInfo.IME_ACTION_DONE);

            }
        });

        return rootView;
    }

}
