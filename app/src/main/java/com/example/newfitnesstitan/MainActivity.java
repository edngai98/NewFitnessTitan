package com.example.newfitnesstitan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.example.newfitnesstitan.UserResults.Users;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DrawerLayout drawerLayout;
    private TextView name;
    private ListenerRegistration registration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar to edit the top bar of view
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Toggle the navbar drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
        String className = intent.getStringExtra("class");

        DocumentReference user = db.document("users/" + login);

        user.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if(documentSnapshot.exists()) {
                    Users users = documentSnapshot.toObject(Users.class);
                    name = findViewById(R.id.nav_name);
                    name.setText(users.getFirst() + " " + users.getLast());
                }
            }
        });
        //Check to see if container is null, if so inflate the view with dashboard fragment
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
    //Switch the fragments based on the navigation item selected
    @Override
    //Implementing the navigation interface
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent = getIntent();
        String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                String className = intent.getStringExtra("class");
                //Create a bundle to pass the login, the className that we want to open as well as a checker class
                //Checker is passed to see if we are coming back to home fragment from somewhere other than Login
                Bundle arguments = new Bundle();
                DashboardFragment fragment = new DashboardFragment();
                fragment.setArguments(arguments);
                arguments.putString("login",login);
                arguments.putString("class", className);
                arguments.putString("checker", "true1");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.nav_leaderboard:
                LeaderboardFragment fragmentLeader = new LeaderboardFragment();
                Bundle argumentsLeader = new Bundle();
                argumentsLeader.putString("checker", "true1" );
                fragmentLeader.setArguments(argumentsLeader);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentLeader).commit();
                break;
            case R.id.nav_quiz:
                Bundle argumentsQuiz = new Bundle();
                QuizListFragment fragmentQuiz = new QuizListFragment();
                fragmentQuiz.setArguments(argumentsQuiz);
                argumentsQuiz.putString("login",login);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentQuiz).commit();
                break;
            case R.id.nav_learnings:
                LearningsFragment fragment1 = new LearningsFragment();
                Bundle arguments2 = new Bundle();
                arguments2.putString("checker", "true1" );
                arguments2.putString("login", login);
                fragment1.setArguments(arguments2);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).commit();
                break;
            case R.id.nav_calorie:
                CalorieFragment calorieFragment = new CalorieFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, calorieFragment).commit();
                break;
            case R.id.nav_edit:
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                Bundle argumentsEdit = new Bundle();
                argumentsEdit.putString("login", login);
                editProfileFragment.setArguments(argumentsEdit);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editProfileFragment).commit();
                break;
            case R.id.nav_logout:
                //Asks if the person is sure they want to logout
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                View mView = getLayoutInflater().inflate(R.layout.logout_builder, null);
                Button mLogOut = mView.findViewById(R.id.btnLogOut);
                Intent intentLogout = new Intent(this, LoginActivity.class);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                mLogOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                        startActivity(intentLogout);
                    }
                });
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //Close nav bar drawer after pressing a button
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
