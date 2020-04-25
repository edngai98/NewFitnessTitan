package com.example.newfitnesstitan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LearningsFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference getLearningsDatabase = db.collection("modules");

    private LearningAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.learnings_fragment, container, false);
        FirestoreRecyclerOptions<LearningsModule> options = new FirestoreRecyclerOptions.Builder<LearningsModule>()
                .setQuery(getLearningsDatabase, LearningsModule.class)
                .build();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.learnings_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new LearningAdapter(options);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Intent intent = new Intent (this, LearningDetail.class);



        adapter.setOnItemClickListener(new LearningAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                String path = documentSnapshot.getReference().getPath();
                Bundle arguments = new Bundle();
                LearningDetailFragment fragment = new LearningDetailFragment();
                fragment.setArguments(arguments);
                arguments.putString("learning", path);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
//                Bundle bundle = new Bundle();
//                bundle.putString("learning", path);

//                intent.putExtra("learning", path);
//                startActivity(intent);

            }
        });


        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
