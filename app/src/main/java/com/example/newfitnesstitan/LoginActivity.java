package com.example.newfitnesstitan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.newfitnesstitan.UserResults.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference checkAcc = db.collection("users");

    public static final String KEY_LOGIN_TO_MAIN = "log2main";
    public static final String KEY_LOGIN_TO_QUIZRESULT = "log2result";

    private EditText Name;
    private EditText Password;
    private Button Signin;
    private TextView createAccountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Name = findViewById(R.id.etFirst);
        Password = findViewById(R.id.etUser);
        Signin = findViewById(R.id.btnCreateAcc);
        createAccountText = findViewById(R.id.createAcc);
        createAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateAcccount();
            }
        });

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Name.getText().toString().equals("") && !Password.getText().toString().equals("")) {
                    validate(Name.getText().toString(), Password.getText().toString());

                } else {
                    Toast.makeText(LoginActivity.this, "Fill in the empty fields", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void validate(String userName, String userPassword){

        Intent intent = new Intent(this, MainActivity.class);

        checkAcc.whereEqualTo("username", userName)
                .whereEqualTo("password", userPassword)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() > 0) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    String path = documentSnapshot.getId();
//                                    arguments.putString(KEY_LOGIN_TO_MAIN, path);
//                                    arguments.putString("class", "false");
                                    intent.putExtra(KEY_LOGIN_TO_MAIN, path);
                                    intent.putExtra("secure", "true1");
                                    intent.putExtra("class", "false");
                                    startActivity(intent);

                                }
                                Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Unsuccessful Login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    public void goToCreateAcccount() {
        Intent i = new Intent(this, CreateAccountActivity.class);
        startActivity(i);
    }

}
