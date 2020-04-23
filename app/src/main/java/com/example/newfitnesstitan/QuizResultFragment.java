package com.example.newfitnesstitan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

public class QuizResultFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView result;
    private TextView wrong_answers;
    private TextView quiz_title;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quiz_result_fragment, container, false);
        result = rootView.findViewById(R.id.tvCorrect);
        wrong_answers = rootView.findViewById(R.id.tvWrong);
        quiz_title = rootView.findViewById(R.id.quizname);

        Bundle bundle = getArguments();
        String correct = bundle.getString("correct");
        String wrong = bundle.getString("wrong");
        String title = bundle.getString("title");


//        String correct = intent.getStringExtra("correct");
//        String wrong = intent.getStringExtra("wrong");
//        String title = intent.getStringExtra("title");

        result.setText(correct);
        wrong_answers.setText(wrong);
        quiz_title.setText(title);

        Button update_button = rootView.findViewById(R.id.SetQuizResult);
//        Intent intent2 = new Intent(this, MainActivity.class);


        //Update Quiz
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(correct);
                String name = quiz_title.getText().toString();

                Bundle bundle = getArguments();
                String login = bundle.getString("loginDetails4");


//                Intent intent = getIntent();
//                String login = intent.getStringExtra("loginDetails4");

//                if(findViewById(R.id.fragment_container) == null) {
//
//                    intent2.putExtra("quizNameQR", name);
//                    intent2.putExtra("quizResultScore", String.valueOf(value));
//                    intent2.putExtra("class", "true");
//                    intent2.putExtra("checker", "true1");
//                    intent2.putExtra(LoginActivity.KEY_LOGIN_TO_MAIN, login);
//
//                    startActivity(intent2);
//                }

                Bundle arguments = new Bundle();
                DashboardFragment fragment = new DashboardFragment();
                fragment.setArguments(arguments);
                arguments.putString("quizNameQR", name);
                arguments.putString("quizResultScore", String.valueOf(value));
                arguments.putString("class", "true");
                arguments.putString("checker", "true1");
                arguments.putString("login", login);
                System.out.println("this is the QuizResultFragment");
                System.out.println(login);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();

            }
        });

        return rootView;
    }


}