package com.example.newfitnesstitan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.newfitnesstitan.UserResults.Users;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference checkAcc = db.collection("users");
    private DocumentReference doc;

    private EditText user_first_name, user_last_name, user_id, user_password;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        user_first_name = findViewById(R.id.etFirstEdit);
        user_last_name = findViewById(R.id.etLastEdit);
        user_id = findViewById(R.id.etUserEdit);
        user_password = findViewById(R.id.etPassEdit);
        saveButton = findViewById(R.id.btnSaveAcc);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String login = intent.getStringExtra("editProfile");

        doc = db.collection("users").document(login);

        doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if(documentSnapshot.exists()) {

                }
            }
        });

        //if statements
        String first = user_first_name.getText().toString();
        String last = user_last_name.getText().toString();
        String username = user_id.getText().toString();
        String userPass = user_password.getText().toString();

        Users users = new Users(username, userPass, first, last);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doc.set(users);
            }
        });


    }
}
