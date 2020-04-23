package com.example.newfitnesstitan;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference getQuizDatabase = db.collection("quizzes");
    public static final String KEY_PATH = "path needed";
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
        String className = intent.getStringExtra("class");

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            DashboardFragment fragment = new DashboardFragment();
            fragment.setArguments(arguments);
            arguments.putString("login",login);
            arguments.putString("class", className);
            arguments.putString("checker", "true1");
            System.out.println(arguments.toString());
            System.out.println(className);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent = getIntent();
        String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intent = getIntent();
                String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
                String className = intent.getStringExtra("class");
                Bundle arguments = new Bundle();
                DashboardFragment fragment = new DashboardFragment();
                fragment.setArguments(arguments);
                arguments.putString("login",login);
                arguments.putString("class", className);
                arguments.putString("checker", "true1");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.nav_leaderboard:
                LeaderboardFragment fragment = new LeaderboardFragment();
                Bundle arguments = new Bundle();
                arguments.putString("checker", "true1" );
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.nav_quiz:
                Bundle argumentsQuiz = new Bundle();
                QuizListFragment fragmentQuiz = new QuizListFragment();
                fragmentQuiz.setArguments(argumentsQuiz);
                Intent intentQuiz = getIntent();
                String loginQuiz = intentQuiz.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
                argumentsQuiz.putString("login",loginQuiz);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentQuiz).commit();
                break;
            case R.id.nav_learnings:
                LearningsFragment fragment1 = new LearningsFragment();
                Bundle arguments2 = new Bundle();
                arguments2.putString("checker", "true1" );
                fragment1.setArguments(arguments2);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).commit();
                break;
//            case R.id.nav_calorie:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
//                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }






}
