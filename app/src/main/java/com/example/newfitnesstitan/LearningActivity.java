package com.example.newfitnesstitan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LearningActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference getLearningsDatabase = db.collection("modules");

    private LearningAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylearnings);
        setUpRecyclerView();

    }
    private void setUpRecyclerView() {

        FirestoreRecyclerOptions<LearningsModule> options = new FirestoreRecyclerOptions.Builder<LearningsModule>()
                .setQuery(getLearningsDatabase, LearningsModule.class)
                .build();


        adapter = new LearningAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.learnings_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

       Intent intent = new Intent (this, LearningDetail.class);



        adapter.setOnItemClickListener(new LearningAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                String path = documentSnapshot.getReference().getPath();
                Bundle bundle = new Bundle();
                bundle.putString("learning", path);

                intent.putExtra("learning", path);
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
