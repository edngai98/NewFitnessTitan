package com.example.newfitnesstitan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.newfitnesstitan.QuizContent.QuizDescriptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuizListActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        FirestoreRecyclerOptions<QuizDescriptions> options = new FirestoreRecyclerOptions.Builder<QuizDescriptions>()
                .setQuery(getQuizDatabase, QuizDescriptions.class)
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

                String path = documentSnapshot.getReference().getPath();
                Intent i = getIntent();
                String login = i.getStringExtra("loginDetails");

                Toast.makeText(QuizListActivity.this, "", Toast.LENGTH_SHORT).show();
                intent.putExtra(KEY_PATH,path);
                intent.putExtra("loginDetails2", login);
                startActivity(intent);

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


}
