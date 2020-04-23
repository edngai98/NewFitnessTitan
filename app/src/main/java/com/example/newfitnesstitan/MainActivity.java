package com.example.newfitnesstitan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
//import com.bumptech.glide.Glide;
import com.example.newfitnesstitan.UserResults.Users;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//import com.anychart.sample.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference quizResultRef;
    private Cartesian cartesian;
    private AnyChartView anyChartView;

    private TextView tvHelloMate;
    private TextView testViewData;
    private ImageView image;

    public Users users;
    String name;
    int a;
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
                break;
            case R.id.nav_leaderboard:
                LeaderboardFragment fragment = new LeaderboardFragment();
                Bundle arguments = new Bundle();
                arguments.putString("checker", "true1" );
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                break;
            case R.id.nav_quiz:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuizFragment()).commit();
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

//    @Override
//    protected void onStart() {
//        super.onStart();

//            Intent intent = getIntent();
//            String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
//
//
//            quizResultRef = db.document("users/" + login);
//
//            Intent i = new Intent(this, EditProfileActivity.class);
//
//            anyChartView = findViewById(R.id.any_chart_view);
//            anyChartView.setProgressBar(findViewById(R.id.progress_bar));
//            cartesian = AnyChart.column();

//            readData(new FirestoreCallback() {
//                @Override
//                public void onCallback(List<DataEntry> list) {
//                    list.toString();
//                }
//            });
//
//            image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = getIntent();
//                    String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
//                    i.putExtra("editProfile", login);
//                    startActivity(i);
//                }
//            });


//        }

    public void goToQuizzes() {
        Intent intent = getIntent();
        String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
        Intent i = new Intent(this, QuizListActivity.class);
        i.putExtra("loginDetails", login);
        startActivity(i);
    }

//    private void readData(FirestoreCallback firestoreCallback) {
//        quizResultRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    return;
//                }
//                Intent intent = getIntent();
//                String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
//                List<DataEntry> data = new ArrayList<>();
//                if (documentSnapshot.exists()) {
//                    users = documentSnapshot.toObject(Users.class);
//                    System.out.println(users.getFirst());
//                    String className = getIntent().getStringExtra("class");
//                    tvHelloMate.setText("Hello " + users.getFirst() +",");
//                    if(className.equals("true")) {
//
//                        for(Map.Entry<String, Integer> q : users.getQuizResults().entrySet()) {
//
//                            if(q.getKey().equals(getIntent().getStringExtra("quizNameQR"))) {
//
//                                name = getIntent().getStringExtra("quizNameQR");
//                                a = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("quizResultScore")));
//
//                                if(q.getValue() == a){
//                                    return;
//                                } else {
//                                    q.setValue(a);
//                                    db.collection("users").document(login).update("quizResults." + name, a);
//                                }
//
//                            }
//                        }
//
//                    }
//
//                    for (Map.Entry<String, Integer> q : users.getQuizResults().entrySet()) {
//                        String quiz_name = q.getKey();
//                        int quiz_result = q.getValue();
//                        data.add(new ValueDataEntry(quiz_name, quiz_result));
//                    }
//                    Column column = cartesian.column(data);
//
//                    column.tooltip()
//                            .titleFormat("{%X}")
//                            .position(Position.CENTER_BOTTOM)
//                            .anchor(Anchor.CENTER_BOTTOM)
//                            .offsetX(0)
//                            .offsetY(5)
//                            .format("{%Value}{groupsSeparator: }");
//
//                    column.fill("red");
//                    column.stroke("green");
//
////                        column.fill("function() {" +
////                                "if (this.value > 5) return 'red';\n" +
////                                "return 'green';}");
//
//                    //cartesian.animation(true);
//                    cartesian.title("My Progress");
//
//                    //cartesian.yScale().minimum(0);
//                    cartesian.yScale().maximum(5);
//
//                    //cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");
//
//                    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
//                    cartesian.interactivity().hoverMode(HoverMode.BY_X);
//
//                    //cartesian.xAxis(0).title("Product");
//                    //cartesian.yAxis(0).title("Revenue");
//
//                    anyChartView.setChart(cartesian);
//
//                }
//            }
//        });
//
//    }
//
//    private interface FirestoreCallback {
//        void onCallback(List<DataEntry> list);
//    }




}
