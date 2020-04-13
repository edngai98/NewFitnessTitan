package com.example.newfitnesstitan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class StartQuizActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button b1, b2, b3, b4;
    TextView question, quizTitle;

    int total = 1;
    int correct = 0;
    int wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        b1 = findViewById(R.id.option1);
        b2 = findViewById(R.id.option2);
        b3 = findViewById(R.id.option3);
        b4 = findViewById(R.id.option4);
        question = findViewById(R.id.question_name);
        quizTitle = findViewById(R.id.in_quiz_name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String inQuizName = intent.getStringExtra("quiz_name_key");
        quizTitle.setText(inQuizName);
        loadQuestion();
        total = 1;
    }

    public void loadQuestion() {
        if (total > 5) {
            goToQuizResults();
            return;
        }

        Intent intent = getIntent();
        String path = intent.getStringExtra(QuizDescriptionActivity.KEY_START_QUIZ_PATH);
        System.out.println(path);
        CollectionReference questionOn = db.collection(path);
        String qNo = String.valueOf(total);

        questionOn.whereEqualTo("tag",qNo).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                        return;
                }

                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                        final QuizQuestions quizQuestions = documentSnapshot.toObject(QuizQuestions.class);

                        question.setText(quizQuestions.getQuestion());
                        b1.setText(quizQuestions.getOption1());
                        b2.setText(quizQuestions.getOption2());
                        b3.setText(quizQuestions.getOption3());
                        b4.setText(quizQuestions.getOption4());

                        b1.setBackgroundColor(Color.parseColor("#03A9f4"));
                        b2.setBackgroundColor(Color.parseColor("#03A9f4"));
                        b3.setBackgroundColor(Color.parseColor("#03A9f4"));
                        b4.setBackgroundColor(Color.parseColor("#03A9f4"));

                        b1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //answer if correct
                                if(b1.getText().toString().equals(quizQuestions.getAnswer())) {

                                    //change colours here
                                    b1.setBackgroundColor(Color.GREEN);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            correct++;

                                            //change colours here
                                            b1.setBackgroundColor(Color.parseColor("#03A9f4"));

                                            total++;
                                            loadQuestion();

                                        }
                                    }, 1500);
                                }

                                //answer if wrong
                                else {

                                    wrong++;
                                    b1.setBackgroundColor(Color.RED);

                                    if(b2.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b2.setBackgroundColor(Color.GREEN);
                                    }

                                    else if(b3.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b3.setBackgroundColor(Color.GREEN);
                                    }

                                    else if(b4.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b4.setBackgroundColor(Color.GREEN);
                                    }

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            total++;
                                            loadQuestion();

                                        }
                                    },1500);
                                }
                            }
                        });

                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //answer if correct
                                if (b2.getText().toString().equals(quizQuestions.getAnswer())) {

                                    //change colours here
                                    b2.setBackgroundColor(Color.GREEN);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            correct++;

                                            //change colours here
                                            b2.setBackgroundColor(Color.parseColor("#03A9f4"));

                                            total++;
                                            loadQuestion();
                                        }
                                    }, 1500);
                                }

                                //answer if wrong
                                else {

                                    wrong++;
                                    b2.setBackgroundColor(Color.RED);

                                    if (b1.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b1.setBackgroundColor(Color.GREEN);
                                    } else if (b3.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b3.setBackgroundColor(Color.GREEN);
                                    } else if (b4.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b4.setBackgroundColor(Color.GREEN);
                                    }

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            total++;
                                            loadQuestion();

                                        }
                                    }, 1500);
                                }
                            }
                        });

                        b3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //answer if correct
                                if(b3.getText().toString().equals(quizQuestions.getAnswer())) {

                                    //change colours here
                                    b3.setBackgroundColor(Color.GREEN);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            correct++;

                                            //change colours here
                                            b3.setBackgroundColor(Color.parseColor("#03A9f4"));

                                            total++;
                                            loadQuestion();
                                        }
                                    }, 1500);
                                }

                                //answer if wrong
                                else {

                                    wrong++;
                                    b3.setBackgroundColor(Color.RED);

                                    if(b1.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b1.setBackgroundColor(Color.GREEN);
                                    }

                                    else if(b2.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b2.setBackgroundColor(Color.GREEN);
                                    }

                                    else if(b4.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b4.setBackgroundColor(Color.GREEN);
                                    }


                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            total++;
                                            loadQuestion();

                                        }
                                    },1500);
                                }
                            }
                        });

                        b4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //answer if correct
                                if(b4.getText().toString().equals(quizQuestions.getAnswer())) {

                                    //change colours here
                                    b4.setBackgroundColor(Color.GREEN);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            correct++;

                                            //change colours here
                                            b4.setBackgroundColor(Color.parseColor("#03A9f4"));

                                            total++;
                                            loadQuestion();
                                        }
                                    }, 1500);
                                }

                                //answer if wrong
                                else {

                                    wrong++;
                                    b4.setBackgroundColor(Color.RED);

                                    if(b2.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b2.setBackgroundColor(Color.GREEN);
                                    }

                                    else if(b3.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b3.setBackgroundColor(Color.GREEN);
                                    }

                                    else if(b1.getText().toString().equals(quizQuestions.getAnswer())) {
                                        b1.setBackgroundColor(Color.GREEN);
                                    }

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            total++;
                                            loadQuestion();
                                        }
                                    },1500);
                                }
                            }
                        });

                    }
                }
            });

    }

    public void goToQuizResults() {
        Intent intent2 = new Intent(this, QuizResult.class);
        String temp = String.valueOf(correct);
        String temp2 = String.valueOf(wrong);

        intent2.putExtra("title", quizTitle.getText().toString());
        intent2.putExtra("correct", temp);
        intent2.putExtra("wrong", temp2);
        startActivity(intent2);
    }

}

