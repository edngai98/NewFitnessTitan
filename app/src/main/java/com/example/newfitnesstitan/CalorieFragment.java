package com.example.newfitnesstitan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalorieFragment extends Fragment {
    private Button button;
    //Start the calorie fragment screen from here
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Find the layout that this fragment is attached to
        View rootView = inflater.inflate(R.layout.calorie_start_info, container, false);
        //Find the buttons on the screen
        button = rootView.findViewById(R.id.StartQuiz);
        //Used an on click listener to open up a new activity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                CalorieDetailFragment fragment = new CalorieDetailFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
        return  rootView;
    }
}
