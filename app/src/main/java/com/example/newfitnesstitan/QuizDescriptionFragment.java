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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.newfitnesstitan.QuizContent.QuizDescriptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class QuizDescriptionFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String KEY_START_QUIZ_PATH = "start quiz";
    private StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fitnesstitan-5ed50.appspot.com/api.png");

    private TextView name, description;
    private ImageView image;
    private Context context;
    private Button button;
    private ListenerRegistration registration;

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

//        Intent intent = getIntent();
//        String path = intent.getStringExtra(QuizListActivity.KEY_PATH);
//        System.out.println(path);

        Bundle bundle = getArguments();
        String path = bundle.getString(QuizListFragment.KEY_PATH);

        DocumentReference getSpecificQuiz = db.document(path);

//        Intent intent2 = new Intent(this, StartQuizActivity.class);

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
                            String login = bundle.getString("loginDetails2");
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
//                            Intent i = getIntent();
//                            String login = i.getStringExtra("loginDetails2");

//                            intent2.putExtra(KEY_START_QUIZ_PATH, path2);
//                            intent2.putExtra("quiz_name_key", quiz_name);
//                            intent2.putExtra("loginDetails3", login);
//
//                            startActivity(intent2);

                        }
                    });

                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        registration.remove();
    }
}
