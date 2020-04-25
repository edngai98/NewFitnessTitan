package com.example.newfitnesstitan;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference quizResultRef;
    private Cartesian cartesian;
    private AnyChartView anyChartView;

    private TextView tvHelloMate;
    private TextView testViewData;
    private TextView funnyJokes;
    private ImageView image;
    private Context context;
    private Button quizzes_button;
    private ListenerRegistration registration;
    public Users users;
    String name;
    String s;
    String quiz_name;
    int a;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        Bundle bundle = getArguments();
        s = bundle.getString("login");
        String temp = bundle.getString("checker");
        //Intent intent = new Intent(getContext(), QuizListActivity.class);
        if (!temp.equals("true")) {
            tvHelloMate = rootView.findViewById(R.id.textView2);
            anyChartView = rootView.findViewById(R.id.any_chart_view);
            anyChartView.setProgressBar(rootView.findViewById(R.id.progress_bar));
            cartesian = AnyChart.column();
            funnyJokes = rootView.findViewById(R.id.funnyJoke);
            readData(new FirestoreCallback() {
                @Override
                public void onCallback(List<DataEntry> list) {

                }
            });
            new JokeTask().execute();

        }


        return rootView;
    }


    private void readData(FirestoreCallback firestoreCallback) {
        Bundle bundle = getArguments();
        String login = bundle.getString("login");
        String temp = bundle.getString("checker");
        System.out.println(login);
        if (!temp.equals("true")) {

            final DocumentReference quizResultRef = db.document("users/" + login);
            registration = quizResultRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }

                    Bundle bundle = getArguments();
                    String login = bundle.getString("login");

                    List<DataEntry> data = new ArrayList<>();
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        users = documentSnapshot.toObject(Users.class);
                        System.out.println(users.getFirst());
                        String className = bundle.getString("class");

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
                            quiz_name = q.getKey();
                            if(q.getKey().equals("Carbohydrate")) {
                                quiz_name = "Carbs";

                            }
                            else if (q.getKey().equals("Vegetable")) {
                                quiz_name = "Veg";
                            }
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

                        column.stroke("function() {" +
                                "if (this.value >= 0 && this.value <=2) {return 'red';}\n" +
                                "else if (this.value < 5 && this.value >= 3) {return 'orange';}\n" +
                                "else {return 'green'};}");

                        column.fill("function() {" +
                                "if (this.value >= 0 && this.value <=2) {return 'red';}\n" +
                                "else if (this.value < 5 && this.value >= 3) {return 'orange';}\n" +
                                "else {return 'green'};}");

                        //cartesian.animation(true);
                        //cartesian.title("My Progress");

                        //cartesian.yScale().minimum(0);
                        cartesian.yScale().maximum(5);
                        cartesian.yScale().ticks().allowFractional(false);

                        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                        cartesian.interactivity().hoverMode(HoverMode.BY_X);

                        anyChartView.setChart(cartesian);

                    }
                }
            });

        }


    }

    @Override
    public void onStop() {
        super.onStop();
        registration.remove();

    }

    private interface FirestoreCallback {
        void onCallback(List<DataEntry> list);
    }


    public class JokeTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {
                Retrofit retrofit = new Retrofit.Builder().
                        baseUrl("https://api.spoonacular.com/").
                        addConverterFactory(GsonConverterFactory.create()).
                        build();

                JokeAPI quoteapi = retrofit.create(JokeAPI.class);
                Call<Joke> call = quoteapi.getQuote();
                Response<Joke> jokeResponse = call.execute();
                String quote = String.valueOf(jokeResponse.body().getQuote());
                return quote;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            funnyJokes.setText(s);
        }
    }
}
