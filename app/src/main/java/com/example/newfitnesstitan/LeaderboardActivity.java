package com.example.newfitnesstitan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity {
    private TextView leaderName;
    private TextView leaderScore;
    private TextView leader2;
    private TextView leaderScore2;
    private TextView leader3;
    private TextView leaderScore3;
    private TextView leaderName4;
    private TextView leaderName5;
    private TextView leaderName6;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference x = db.collection("users");
    private CollectionReference noteRef = db.collection("users");

    private static final String KEY_LEADER = "first";
    private static final String KEY_SCORE = "last";

    HashMap<Integer, String> leadValues = new HashMap<Integer, String>();

    ArrayList<Integer> scoreList = new ArrayList<Integer>();
    ArrayList<People> newList = new ArrayList<People>();
    public Leaderboard leaderboard;
    ArrayList<Leaderboard> peopleList = new ArrayList<>();

    //public People leaderboard;
    //public ScoreSet scoreSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leaderName = findViewById(R.id.leaderName);
        leaderScore = findViewById(R.id.leaderScore);
        leader2 = findViewById(R.id.leaderName2);
        leaderScore2 = findViewById(R.id.leaderScore2);
        leader3 = findViewById(R.id.leaderName3);
        leaderScore3 = findViewById(R.id.leaderScore3);
        leaderName4 = findViewById(R.id.leaderName4);
        leaderName5 = findViewById(R.id.leaderName5);
        leaderName6 = findViewById(R.id.leaderName6);
        noteRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot qs : task.getResult()) {
                        People people = qs.toObject(People.class);
                        String name = people.getUsername();
                        int a = 0;
                        for (Map.Entry<String, Integer> q : people.getQuizResults().entrySet()) {
                              int quiz_result = q.getValue();
                              a = quiz_result + a;
                        }
                        peopleList.add(new Leaderboard(name, a));


                    }

                    Collections.sort(peopleList);
                    leaderName.setText(peopleList.get(1).getName());
                    leaderName5.setText(peopleList.get(1).getName());
                    leaderScore.setText(String.valueOf(peopleList.get(0).getResult()));
                    leader2.setText(peopleList.get(0).getName());
                    leaderName4.setText(peopleList.get(0).getName());
                    leaderScore2.setText(String.valueOf(peopleList.get(1).getResult()));
                    leader3.setText(peopleList.get(2).getName());
                    leaderName6.setText(peopleList.get(2).getName());
                    leaderScore3.setText(String.valueOf(peopleList.get(2).getResult()));


                }
            }
        });

    }
}
