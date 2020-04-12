package com.example.newfitnesstitan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuizResult extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference quizListRef = db.collection("users/admin/quiz results");
    private DocumentReference quizRef = db.document("users/admin/quiz results/fruit quiz");

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

        //get intent from which user they are using
        //Intent intent = new Intent();
        //String user_path = intent.getStringExtra("userlogin");


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

                //can get rid of this for intent
                String doc_ID = quiz_title.getText().toString();

                MyQuizResults myQuizResults = new MyQuizResults(name, value);


                quizListRef.document(doc_ID).set(myQuizResults).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(QuizResult.this, "Quiz Result Saved", Toast.LENGTH_SHORT).show();
                        startActivity(intent2);
                    }
                });


            }
        });


    }
}
