package com.example.newfitnesstitan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newfitnesstitan.Entities.Leaderboard;
import com.example.newfitnesstitan.Entities.People;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class LeaderboardFragment extends Fragment {
    private TextView leaderName;
    private TextView leaderScore;
    private TextView leader2;
    private TextView leaderScore2;
    private TextView leader3;
    private TextView leaderScore3;
    private TextView leaderName4;
    private TextView leaderName5;
    private TextView leaderName6;
    private Button button;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference x = db.collection("users");
    private CollectionReference noteRef = db.collection("users");
    ArrayList<Leaderboard> peopleList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.leaderboard_fragment, container, false);
        Bundle bundle = getArguments();
        String s = bundle.getString("login");
        String temp = bundle.getString("checker");
        if (!temp.equals("true")) {
            leaderName = rootView.findViewById(R.id.leaderName);
            leaderScore = rootView.findViewById(R.id.leaderScore);
            leader2 = rootView.findViewById(R.id.leaderName2);
            leaderScore2 = rootView.findViewById(R.id.leaderScore2);
            leader3 = rootView.findViewById(R.id.leaderName3);
            leaderScore3 = rootView.findViewById(R.id.leaderScore3);
            leaderName4 = rootView.findViewById(R.id.leaderName4);
            leaderName5 = rootView.findViewById(R.id.leaderName5);
            leaderName6 = rootView.findViewById(R.id.leaderName6);

            noteRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    //Check if the task is successful
                    if (task.isSuccessful()) {
                        //Loop through a users results and add them together to get their total results
                        //Put results into a list
                        for (QueryDocumentSnapshot qs : task.getResult()) {
                            //Map the results to a People class
                            People people = qs.toObject(People.class);
                            String name = people.getUsername();
                            int a = 0;
                            //Adding the results together
                            for (Map.Entry<String, Integer> q : people.getQuizResults().entrySet()) {
                                int quiz_result = q.getValue();
                                //Add the results to itself each time it finds a value
                                a = quiz_result + a;
                            }
                            peopleList.add(new Leaderboard(name, a));


                        }
                        //Sorting the arrayList by descending value and getting the top three results
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
        return rootView;

    }
}
