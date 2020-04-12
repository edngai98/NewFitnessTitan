package com.example.newfitnesstitan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.anychart.sample.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference quizListRef = db.collection("users/admin/quiz results");

    private TextView textViewData;
    private TextView testViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewData = findViewById(R.id.testText);
        testViewData = findViewById(R.id.test2);

        Button quizzes_button = findViewById(R.id.quote_button);
        quizzes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToQuizzes();
            }
        });
        Intent test = new Intent(this, LoginActivity.class);


    }

    @Override
    protected void onStart() {
        super.onStart();
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        Cartesian cartesian = AnyChart.column();

        quizListRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent( QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                List<DataEntry> data = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    MyQuizResults myQuizResults = documentSnapshot.toObject(MyQuizResults.class);
                    //quizzes.setDocumentID(documentSnapshot.getId());

                    //String doc_ID = quizzes.getDocumentID();
                    String quiz_name = myQuizResults.getName();
                    int quiz_result = myQuizResults.getResult();

                    //List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry(quiz_name, quiz_result));
                }

                Column column = cartesian.column(data);

                column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.CENTER_BOTTOM)
                        .anchor(Anchor.CENTER_BOTTOM)
                        .offsetX(0d)
                        .offsetY(5d)
                        .format("{%Value}{groupsSeparator: }");

                //cartesian.animation(true);
                cartesian.title("My Progress");

                cartesian.yScale().minimum(0d);

                cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.interactivity().hoverMode(HoverMode.BY_X);

                //cartesian.xAxis(0).title("Product");
                //cartesian.yAxis(0).title("Revenue");

                anyChartView.setChart(cartesian);

            }
        });



    }

    public void goToQuizzes() {
        Intent intent = new Intent(this, QuizListActivity.class);
        startActivity(intent);
    }
}
