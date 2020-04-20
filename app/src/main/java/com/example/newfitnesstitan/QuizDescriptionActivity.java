package com.example.newfitnesstitan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.newfitnesstitan.QuizContent.QuizDescriptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class QuizDescriptionActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String KEY_START_QUIZ_PATH = "start quiz";

    private TextView name, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_description);
        name = findViewById(R.id.tvQuizName);
        description = findViewById(R.id.quiz_description_text);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String path = intent.getStringExtra(QuizListActivity.KEY_PATH);
        System.out.println(path);

        DocumentReference getSpecificQuiz = db.document(path);

        Intent intent2 = new Intent(this, StartQuizActivity.class);

        getSpecificQuiz.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot.exists()) {
                    QuizDescriptions quizDescriptions = documentSnapshot.toObject(QuizDescriptions.class);
                    String quiz_name = quizDescriptions.getName();
                    String quiz_description = quizDescriptions.getDescription();
                    name.setText(quiz_name);
                    description.setText(quiz_description);

                    Button button = findViewById(R.id.StartQuiz);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String path2 = documentSnapshot.getReference().getPath() +"/Details";
                            Intent i = getIntent();
                            String login = i.getStringExtra("loginDetails2");

                            intent2.putExtra(KEY_START_QUIZ_PATH, path2);
                            intent2.putExtra("quiz_name_key", quiz_name);
                            intent2.putExtra("loginDetails3", login);

                            startActivity(intent2);

                        }
                    });

                }
            }
        });
    }


}
