package com.example.newfitnesstitan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.firestore.ListenerRegistration;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

public class DashboardFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference quizResultRef;
    private Cartesian cartesian;
    private AnyChartView anyChartView;

    private TextView tvHelloMate;
    private TextView testViewData;
    private ImageView image;
    private Context context;
    private Button quizzes_button;

    public Users users;
    String name;
    int a;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        quizzes_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToQuizzes();
//            }
//        });

//        image = findViewById(R.id.editProfile);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        Bundle bundle = getArguments();
        String s = bundle.getString("login");
        String temp = bundle.getString("checker");
        if (!temp.equals("true")) {
            tvHelloMate = rootView.findViewById(R.id.textView2);
            quizzes_button = rootView.findViewById(R.id.quote_button);
            anyChartView = rootView.findViewById(R.id.any_chart_view);
            anyChartView.setProgressBar(rootView.findViewById(R.id.progress_bar));
            cartesian = AnyChart.column();
            readData(new FirestoreCallback() {
                @Override
                public void onCallback(List<DataEntry> list) {

                }
            });
//            image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = getIntent();
//                    String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
//                    i.putExtra("editProfile", login);
//                    startActivity(i);
//                }
//            });

        }
        return rootView;
    }



//    public void goToQuizzes() {
//        Intent intent = getIntent();
//        String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);
//        Intent i = new Intent(this, QuizListActivity.class);
//        i.putExtra("loginDetails", login);
//        startActivity(i);
//    }

    private void readData(FirestoreCallback firestoreCallback) {
        Bundle bundle = this.getArguments();
        String login = bundle.getString("login");
        String temp = bundle.getString("checker");
        System.out.println(login);
        if (!temp.equals("true")) {
            final DocumentReference quizResultRef = db.document("users/" + login);
            quizResultRef.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }
//                Intent intent = getIntent();
//                String login = intent.getStringExtra(LoginActivity.KEY_LOGIN_TO_MAIN);

                    Bundle bundle = getArguments();
                    String login = bundle.getString("login");

                    List<DataEntry> data = new ArrayList<>();
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        users = documentSnapshot.toObject(Users.class);
                        System.out.println(users.getFirst());
                        String className = bundle.getString("class");
//                    String className = getIntent().getStringExtra("class");
                        tvHelloMate.setText("Hello " + users.getFirst() + ",");
                        if (className.equals("true")) {

                            for (Map.Entry<String, Integer> q : users.getQuizResults().entrySet()) {

                                if (q.getKey().equals(bundle.getString("quizNameQR"))) {

                                    name = bundle.getString("quizNameQR");
                                    a = Integer.parseInt(Objects.requireNonNull(bundle.getString("quizResultScore")));

                                    if (q.getValue() == a) {
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

                        column.fill("red");
                        column.stroke("green");

//                        column.fill("function() {" +
//                                "if (this.value > 5) return 'red';\n" +
//                                "return 'green';}");

                        //cartesian.animation(true);
                        cartesian.title("My Progress");

                        //cartesian.yScale().minimum(0);
                        cartesian.yScale().maximum(5);

                        //cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

                        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                        cartesian.interactivity().hoverMode(HoverMode.BY_X);

                        //cartesian.xAxis(0).title("Product");
                        //cartesian.yAxis(0).title("Revenue");

                        anyChartView.setChart(cartesian);

                    }
                }
            });

        }


    }

    private interface FirestoreCallback {
        void onCallback(List<DataEntry> list);
    }
}
