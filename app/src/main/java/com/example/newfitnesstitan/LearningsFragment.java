package com.example.newfitnesstitan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newfitnesstitan.Entities.LearningsModule;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        adapter.setOnItemClickListener(new LearningAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Bundle bundle = getArguments();
                String login = bundle.getString("login");

                String path = documentSnapshot.getReference().getPath();
                Bundle arguments = new Bundle();
                LearningDetailFragment fragment = new LearningDetailFragment();
                fragment.setArguments(arguments);
                arguments.putString("learning", path);
                System.out.println(path);
                arguments.putString("login", login);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();

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
