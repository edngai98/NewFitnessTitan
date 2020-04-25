package com.example.newfitnesstitan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newfitnesstitan.UserResults.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference createUserRef = db.collection("users");

    private EditText user_first_name, user_last_name, user_id, user_password;
    private Button createAccButton;
    private TextView goToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        user_first_name = findViewById(R.id.etFirst);
        user_last_name = findViewById(R.id.etLast);
        user_id = findViewById(R.id.etUser);
        user_password = findViewById(R.id.etPass);
        createAccButton = findViewById(R.id.btnCreateAcc);
        goToLogin = findViewById(R.id.goBackToLogin);



    }

    @Override
    protected void onStart() {
        super.onStart();

        createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = user_first_name.getText().toString();
                String last = user_last_name.getText().toString();
                String username = user_id.getText().toString();
                String userPass = user_password.getText().toString();

                Users users = new Users(username, userPass, first, last);

                if (!first.equals("") && !last.equals("") && !username.equals("") && !userPass.equals("")) {

                    createUserRef.add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(CreateAccountActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            goToLogin();
                        }
                    });
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Fill in the empty fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

    }

    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
