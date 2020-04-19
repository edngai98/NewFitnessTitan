package com.example.newfitnesstitan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newfitnesstitan.UserResults.MyQuizResults;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuizResultActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView result;
    private TextView wrong_answers;
    private TextView quiz_title;

    //intent = what you want to send from one activity to another
    //startActivity(intent)

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
        System.out.println("ALLL DAUIHCIUHEUIWHUCIEWUH");
        Intent intent = getIntent();
        String login = intent.getStringExtra("loginDetails4");
        System.out.println(login);

        DocumentReference quizListRef = db.collection("users").document(login);



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

                MyQuizResults myQuizResults = new MyQuizResults(name, value);


                intent2.putExtra("quizNameQR", name);
                intent2.putExtra("quizResultScore", String.valueOf(value));
                intent2.putExtra("class", "true");
                intent2.putExtra(LoginActivity.KEY_LOGIN_TO_MAIN, login);
                System.out.println(value);
                System.out.println("12341325135");

                startActivity(intent2);


//                quizListRef.update().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(QuizResultActivity.this, "Quiz Result Saved", Toast.LENGTH_SHORT).show();
//
//                        intent2.putExtra("quizNameQR", name);
//                        startActivity(intent2);
//                    }
//                });


            }
        });


    }
}
