package com.example.newfitnesstitan;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.newfitnesstitan.QuizContent.QuizDescriptions;
import com.example.newfitnesstitan.UserResults.Users;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class QuizDescriptionFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String KEY_START_QUIZ_PATH = "start quiz";

    private TextView name, description;
    private ImageView image;
    private Button button;
    private ImageView searchButton;
    private ListenerRegistration registration;
    private ListenerRegistration registration2;
    private DocumentReference getSpecificQuiz;
    private String path2;
    private String login;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quiz_description_fragment, container, false);

        name = rootView.findViewById(R.id.tvQuizName);
        description = rootView.findViewById(R.id.quiz_description_text);
        image = rootView.findViewById(R.id.imageFromDB);
        button = rootView.findViewById(R.id.StartQuiz);
        RatingBar ratingBar = rootView.findViewById(R.id.bestScore);
        searchButton = rootView.findViewById(R.id.ivSearch);

        Bundle bundle = getArguments();
        String path = bundle.getString(QuizListFragment.KEY_PATH);
        path2 = bundle.getString("learning");

        if (path == null) {
            getSpecificQuiz = db.document(path2);
        } else {
            getSpecificQuiz = db.document(path);
        }


        registration = getSpecificQuiz.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot.exists()) {
                    QuizDescriptions quizDescriptions = documentSnapshot.toObject(QuizDescriptions.class);
                    String quiz_name = quizDescriptions.getName();
                    String quiz_description = quizDescriptions.getDescription();
                    String a = quizDescriptions.getImage();
                    System.out.println(a);
                    name.setText(quiz_name);
                    description.setText(quiz_description);

                    Glide.with(getActivity())
                            .load(FirebaseStorage.getInstance().getReferenceFromUrl(a))
                            .into(image);


                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String path2 = documentSnapshot.getReference().getPath() +"/Details";
                            Bundle bundle = getArguments();
                            login = bundle.getString("loginDetails2");
                            if (bundle.getString("loginDetails2") == null) {
                                login = bundle.getString("login");
                            }

                            String quiz_name = quizDescriptions.getName();

                            Bundle arguments = new Bundle();
                            StartQuizFragment fragment = new StartQuizFragment();
                            fragment.setArguments(arguments);
                            arguments.putString(KEY_START_QUIZ_PATH, path2);
                            arguments.putString("loginDetails3", login);
                            arguments.putString("quiz_name_key", quiz_name);
                            System.out.println("This is QuizDescriptionFragment");
                            System.out.println(login);

                            getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .addToBackStack(null)
                                    .commit();

                        }
                    });

                }
            }
        });

        login = bundle.getString("loginDetails2");
        if (bundle.getString("loginDetails2") == null) {
            login = bundle.getString("login");
        }

        DocumentReference userScore = db.document("users/" + login);

        registration2 = userScore.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null) {
                    return;
                }

                if(documentSnapshot.exists()) {
                    Users users = documentSnapshot.toObject(Users.class);
                    System.out.println("Before Loop");
                    for (Map.Entry<String, Integer> q : users.getQuizResults().entrySet()) {

                        if(q.getKey().equals(name.getText().toString())) {
                            ratingBar.setRating(q.getValue());
                        }
                    }
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuiz(name.getText().toString());
            }
        });

        return rootView;

    }

    @Override
    public void onStop() {
        super.onStop();
        registration.remove();
        registration2.remove();
    }

    private void searchQuiz(String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + name));
        startActivity(intent);
    }
}
