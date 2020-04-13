package com.example.newfitnesstitan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Signin;
    // private TextView tvRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Name = findViewById(R.id.etPassword);
        Password = findViewById(R.id.etPassword);
        Signin = findViewById(R.id.btnSignin);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDashBoard();

                //validate(Name.getText().toString(),Password.getText().toString());
            }
        });
    }

    private void validate(String userName, String userPassword){
        if((userName.equals("titan")) && (userPassword.equals("1234"))){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            //login failure, let user try again
            System.out.println("TRY AGAIN");
        }
    }

    public void goToDashBoard() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
