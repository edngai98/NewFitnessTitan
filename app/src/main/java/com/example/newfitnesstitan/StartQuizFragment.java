package com.example.newfitnesstitan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newfitnesstitan.QuizContent.QuizQuestions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StartQuizFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListenerRegistration registration;

    Button b1, b2, b3, b4;
    TextView question, quizTitle;

    int total = 1;
    int correct = 0;
    int wrong = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.start_quiz_fragment, container, false);
        b1 = rootView.findViewById(R.id.option1);
        b2 = rootView.findViewById(R.id.option2);
        b3 = rootView.findViewById(R.id.option3);
        b4 = rootView.findViewById(R.id.option4);
        quizTitle = rootView.findViewById(R.id.in_quiz_name);

        Bundle bundle = getArguments();
        String inQuizName = bundle.getString("quiz_name_key");

        quizTitle.setText(inQuizName);

        loadQuestion();

        total = 1;
        question = rootView.findViewById(R.id.question_name);

        return rootView;
    }

    public void loadQuestion() {

        if (total > 5) {
            goToQuizResults();
            return;
        }

        Bundle bundle = getArguments();
        String path = bundle.getString(QuizDescriptionFragment.KEY_START_QUIZ_PATH);

        CollectionReference questionOn = db.collection(path);
        String qNo = String.valueOf(total);

        registration = questionOn.whereEqualTo("tag",qNo).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                    b1.setTextColor(Color.parseColor("#000000"));
                    b2.setTextColor(Color.parseColor("#000000"));
                    b3.setTextColor(Color.parseColor("#000000"));
                    b4.setTextColor(Color.parseColor("#000000"));

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //answer if correct
                            if(b1.getText().toString().equals(quizQuestions.getAnswer())) {

                                //change colours here
                                b1.setTextColor(Color.parseColor("#32CD32"));
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;

                                        //change colours here
                                        b1.setTextColor(Color.parseColor("#32CD32"));

                                        total++;
                                        loadQuestion();

                                    }
                                }, 1500);
                            }

                            //answer if wrong
                            else {

                                wrong++;
                                b1.setTextColor(Color.RED);

                                if(b2.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b2.setTextColor(Color.parseColor("#32CD32"));
                                }

                                else if(b3.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b3.setTextColor(Color.parseColor("#32CD32"));
                                }

                                else if(b4.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b4.setTextColor(Color.parseColor("#32CD32"));
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
                                b2.setTextColor(Color.parseColor("#32CD32"));
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;

                                        //change colours here
                                        b2.setTextColor(Color.parseColor("#32CD32"));

                                        total++;
                                        loadQuestion();
                                    }
                                }, 1500);
                            }

                            //answer if wrong
                            else {

                                wrong++;
                                b2.setTextColor(Color.RED);

                                if (b1.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b1.setTextColor(Color.parseColor("#32CD32"));
                                } else if (b3.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b3.setTextColor(Color.parseColor("#32CD32"));
                                } else if (b4.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b4.setTextColor(Color.parseColor("#32CD32"));
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
                                b3.setTextColor(Color.parseColor("#32CD32"));
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;

                                        //change colours here
                                        b3.setTextColor(Color.parseColor("#32CD32"));

                                        total++;
                                        loadQuestion();
                                    }
                                }, 1500);
                            }

                            //answer if wrong
                            else {

                                wrong++;
                                b3.setTextColor(Color.RED);

                                if(b1.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b1.setTextColor(Color.parseColor("#32CD32"));
                                }

                                else if(b2.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b2.setTextColor(Color.parseColor("#32CD32"));
                                }

                                else if(b4.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b4.setTextColor(Color.parseColor("#32CD32"));
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
                                b4.setTextColor(Color.parseColor("#32CD32"));
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;

                                        //change colours here
                                        b4.setTextColor(Color.parseColor("#32CD32"));

                                        total++;
                                        loadQuestion();
                                    }
                                }, 1500);
                            }

                            //answer if wrong
                            else {

                                wrong++;
                                b4.setTextColor(Color.RED);

                                if(b2.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b2.setTextColor(Color.parseColor("#32CD32"));
                                }

                                else if(b3.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b3.setTextColor(Color.parseColor("#32CD32"));
                                }

                                else if(b1.getText().toString().equals(quizQuestions.getAnswer())) {
                                    b1.setTextColor(Color.parseColor("#32CD32"));
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

    @Override
    public void onStop() {
        super.onStop();
        registration.remove();
    }

    public void goToQuizResults() {
        Bundle bundle = getArguments();
        String login = bundle.getString("loginDetails3");
        String temp = String.valueOf(correct);
        String temp2 = String.valueOf(wrong);

        Bundle arguments = new Bundle();
        QuizResultFragment fragment = new QuizResultFragment();
        fragment.setArguments(arguments);
        arguments.putString("title",quizTitle.getText().toString());
        arguments.putString("correct", temp);
        arguments.putString("wrong", temp2);
        arguments.putString("loginDetails4", login);
        System.out.println("This is StartQuizFragment");
        System.out.println(login);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

    }

}
