package com.example.newfitnesstitan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LearningDetailFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String KEY_START_QUIZ_PATH = "start quiz";
    private StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fitnesstitan-5ed50.appspot.com/api.png");

    private TextView name, description;
    private ImageView image;
    private Context context;
    private ListenerRegistration registration;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.learning_detail_fragment, container, false);

        name = rootView.findViewById(R.id.tvQuizName);
        description = rootView.findViewById(R.id.quiz_description_text);
        image = rootView.findViewById(R.id.imageFromDB);
        Bundle bundle = getArguments();
        String path = bundle.getString("learning");
        DocumentReference getSpecificLearning = db.document(path);

//        Intent intent2 = new Intent(this, StartQuizActivity.class);

        registration = getSpecificLearning.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (documentSnapshot.exists()) {
                    LearningsModule learningDescription = documentSnapshot.toObject(LearningsModule.class);
                    String learnings_name = learningDescription.getName();
                    String learnings_description = learningDescription.getDescription();
                    String a = learningDescription.getImage();
                    name.setText(learnings_name);
                    description.setText(learnings_description);
                    Glide.with(getActivity())
                            .load(FirebaseStorage.getInstance().getReferenceFromUrl(a))
                            .into(image);


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
