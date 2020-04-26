package com.example.newfitnesstitan;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.newfitnesstitan.Entities.Joke;
import com.example.newfitnesstitan.UserResults.Users;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {

//Firestore database call information variables declared here
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
        //Temp is a checker to see if they are coming from login activity or another navigation screen
        // It is passed via intent for any fragment that returns to the home page after it is completed
        if (!temp.equals("true")) {
            tvHelloMate = rootView.findViewById(R.id.textView2);
            anyChartView = rootView.findViewById(R.id.any_chart_view);
            anyChartView.setProgressBar(rootView.findViewById(R.id.progress_bar));
            cartesian = AnyChart.column();
            funnyJokes = rootView.findViewById(R.id.funnyJoke);
            //Async task to read data from the database
            readData(new FirestoreCallback() {
                @Override
                public void onCallback(List<DataEntry> list) {

                }
            });
            //Begin ASYNC Task and API call
            new JokeTask().execute();

        }
        return rootView;
    }

    //Async task to pull from Firestore database
    private void readData(FirestoreCallback firestoreCallback) {
        Bundle bundle = getArguments();
        String login = bundle.getString("login");
        String temp = bundle.getString("checker");
        System.out.println(login);
        if (!temp.equals("true")) {
            //Get the user that is logged in
            final DocumentReference quizResultRef = db.document("users/" + login);
            registration = quizResultRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }
                    //Getting the login from other fragments
                    Bundle bundle = getArguments();
                    String login = bundle.getString("login");
                    //Creating a list here for the Anychart API
                    List<DataEntry> data = new ArrayList<>();
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        users = documentSnapshot.toObject(Users.class);
                        System.out.println(users.getFirst());
                        String className = bundle.getString("class");

                        tvHelloMate.setText("Hello " + users.getFirst() + ",");
                        //Checker from the quizResult fragment, upon change it'll reflect in the database
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
                        //Put the data into the Anychart API
                        for (Map.Entry<String, Integer> q : users.getQuizResults().entrySet()) {
                            quiz_name = q.getKey();
                            //Trim the titles to ensure they fit on the screen
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
                        //AnyChart API colour formatting
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
    //To stop listening to the database after it has requested information
    @Override
    public void onStop() {
        super.onStop();
        registration.remove();

    }

    private interface FirestoreCallback {
        void onCallback(List<DataEntry> list);
    }

//Async task
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
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
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
