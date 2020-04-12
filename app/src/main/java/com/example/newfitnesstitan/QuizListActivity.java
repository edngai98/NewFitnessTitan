package com.example.newfitnesstitan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class QuizListActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //need to create reference for each quiz that is done
    private CollectionReference quizListRef = db.collection("users/admin/quiz results");
    private CollectionReference getQuizDatabase = db.collection("quizzes");

    public static final String KEY_PATH = "path needed";

    private QuizAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list_activity);

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        //Query query = quizListRef.orderBy("result", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Quizzes> options = new FirestoreRecyclerOptions.Builder<Quizzes>()
                .setQuery(getQuizDatabase, Quizzes.class)
                .build();

        adapter = new QuizAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Intent intent = new Intent (this, QuizDescriptionActivity.class);

        adapter.setOnItemClickListener(new QuizAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Quizzes quiz = documentSnapshot.toObject(Quizzes.class);
                String path = documentSnapshot.getReference().getPath();
                System.out.println(path);
                Toast.makeText(QuizListActivity.this, "", Toast.LENGTH_SHORT).show();
                intent.putExtra(KEY_PATH,path);
                startActivity(intent);
                //goToQuizDescription();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void goToQuizDescription() {
        Intent intent = new Intent(this, QuizDescriptionActivity.class);
        startActivity(intent);
    }
}
