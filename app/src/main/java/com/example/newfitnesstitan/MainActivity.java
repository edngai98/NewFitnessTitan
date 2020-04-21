package com.example.newfitnesstitan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.bumptech.glide.Glide;
import com.example.newfitnesstitan.UserResults.Users;
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

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private CollectionReference quizListRef = db.collection("users/admin/quiz results");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fitnesstitan-5ed50.appspot.com/api.png");

    private TextView tvHelloMate;
    private TextView testViewData;
    private ImageView image;

    public Users users;
    String name;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHelloMate = findViewById(R.id.textView2);
        Button quizzes_button = findViewById(R.id.quote_button);


        quizzes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToQuizzes();
            }
        });

        image = findViewById(R.id.editProfile);


    }

    @Override
    protected void onStart() {
        super.onStart();

            Intent intent = getIntent();
            String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);

            DocumentReference quizResultRef = db.document("users/" + login);

            Intent i = new Intent(this, EditProfileActivity.class);

            AnyChartView anyChartView = findViewById(R.id.any_chart_view);
            anyChartView.setProgressBar(findViewById(R.id.progress_bar));
            Cartesian cartesian = AnyChart.column();

            quizResultRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }
                    Intent intent = getIntent();
                    String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
                    List<DataEntry> data = new ArrayList<>();
                    if (documentSnapshot.exists()) {
                        users = documentSnapshot.toObject(Users.class);
                        String className = getIntent().getStringExtra("class");
                        tvHelloMate.setText("Hello " + users.getFirst() +",");
                        if(className.equals("true")) {

                            for(Map.Entry<String, Integer> q : users.getQuizResults().entrySet()) {

                                if(q.getKey().equals(getIntent().getStringExtra("quizNameQR"))) {

                                    name = getIntent().getStringExtra("quizNameQR");
                                    a = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("quizResultScore")));

                                    if(q.getValue() == a){
                                        return;
                                    } else {
                                        q.setValue(a);
                                        db.collection("users").document(login).update("quizResults." + name, a);
                                    }

                                }
                            }

                        }

                        for (Map.Entry<String, Integer> q : users.getQuizResults().entrySet()) {
                            String quiz_name = q.getKey();
                            int quiz_result = q.getValue();
                            data.add(new ValueDataEntry(quiz_name, quiz_result));
                        }
                        Column column = cartesian.column(data);

                        column.tooltip()
                                .titleFormat("{%X}")
                                .position(Position.CENTER_BOTTOM)
                                .anchor(Anchor.CENTER_BOTTOM)
                                .offsetX(0)
                                .offsetY(5)
                                .format("{%Value}{groupsSeparator: }");

                        //cartesian.animation(true);
                        cartesian.title("My Progress");

                        cartesian.yScale().minimum(0);
                        cartesian.yScale().maximum(5);

                        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

                        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                        cartesian.interactivity().hoverMode(HoverMode.BY_X);

                        //cartesian.xAxis(0).title("Product");
                        //cartesian.yAxis(0).title("Revenue");

                        anyChartView.setChart(cartesian);

                    }
                }
            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
                    i.putExtra("editProfile", login);
                    startActivity(i);
                }
            });
        }

    public void goToQuizzes() {
        Intent intent = getIntent();
        String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
        Intent i = new Intent(this, QuizListActivity.class);
        i.putExtra("loginDetails", login);
        startActivity(i);
    }


}
