package com.example.newfitnesstitan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class QuizResultActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView result;
    private TextView wrong_answers;
    private TextView quiz_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        result = findViewById(R.id.tvCorrect);
        wrong_answers = findViewById(R.id.tvWrong);
        quiz_title = findViewById(R.id.quizname);


    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();

        String correct = intent.getStringExtra("correct");
        String wrong = intent.getStringExtra("wrong");
        String title = intent.getStringExtra("title");

        result.setText(correct);
        wrong_answers.setText(wrong);
        quiz_title.setText(title);

        Button update_button = findViewById(R.id.SetQuizResult);
        Intent intent2 = new Intent(this, MainActivity.class);


        //Update Quiz
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(correct);
                String name = quiz_title.getText().toString();
                Intent intent = getIntent();
                String login = intent.getStringExtra("loginDetails4");

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
                arguments.putString(LoginActivity.KEY_LOGIN_TO_MAIN, login);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DashboardFragment()).commit();

            }
        });


    }
}
