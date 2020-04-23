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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class QuizDescriptionFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String KEY_START_QUIZ_PATH = "start quiz";
    private StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fitnesstitan-5ed50.appspot.com/api.png");

    private TextView name, description;
    private ImageView image;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quiz_description_fragment, container, false);


        return rootView;
    }
}
