package com.example.newfitnesstitan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class QuizzesActivity extends AppCompatActivity {

    private static final String TAG = "QuizzesActivity";

    private static final String KEY_RESULTS = "results";
    private static final String KEY_DESCRIPTION = "description";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference quizListRef = db.collection("users/admin/quiz results");
    private DocumentReference quizRef = db.document("users/admin/quiz results/fruit quiz");

    private EditText editTextQuizResult;
    private EditText edit_quiz_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);

        editTextQuizResult = findViewById(R.id.testText);
        edit_quiz_name = findViewById(R.id.quizname);

        Button update_button = findViewById(R.id.SetQuizResult);

        //Update Quiz
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = editTextQuizResult.getText().toString();
                int value=0;
                if (!"".equals(temp)){
                    value=Integer.parseInt(temp);
                }

                String name = edit_quiz_name.getText().toString();
                String doc_ID = edit_quiz_name.getText().toString();

                Quizzes quizzes = new Quizzes(name, value);

                //quizListRef.add(quizzes);

                quizListRef.document(doc_ID).set(quizzes).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(QuizzesActivity.this, "Quiz Result Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuizzesActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }


}
