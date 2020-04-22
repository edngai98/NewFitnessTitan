package com.example.newfitnesstitan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LearningDetail extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String KEY_START_QUIZ_PATH = "start quiz";
    private StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fitnesstitan-5ed50.appspot.com/api.png");

    private TextView name, description;
    private ImageView image;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_detail);
        name = findViewById(R.id.tvQuizName);
        description = findViewById(R.id.quiz_description_text);
        image = findViewById(R.id.imageFromDB);
    }
    @Override
    protected void onStart() {
        super.onStart();


        Intent intent = getIntent();
        String path = intent.getStringExtra("learning");
        System.out.println(path);

        DocumentReference getSpecificLearning = db.document(path);

//        Intent intent2 = new Intent(this, StartQuizActivity.class);

        getSpecificLearning.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot.exists()) {
                    LearningsModule learningDescription = documentSnapshot.toObject(LearningsModule.class);
                    String quiz_name = learningDescription.getName();
                    String quiz_description = learningDescription.getDescription();
                    name.setText(quiz_name);
                    description.setText(quiz_description);

//                    Glide.with(LearningDetail.this)
//                            .load(FirebaseStorage.getInstance().getReferenceFromUrl(a))
//                            .into(image);


                }
            }
        });
    }
}
