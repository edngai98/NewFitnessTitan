package com.example.newfitnesstitan;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newfitnesstitan.QuizContent.QuizDescriptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class QuizListFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference getQuizDatabase = db.collection("quizzes");
    public static final String KEY_PATH = "path needed";
    private QuizAdapter adapter;
    private DrawerLayout drawerLayout;
    private EditText searchView;
    String data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quizlist_fragment, container, false);


        FirestoreRecyclerOptions<QuizDescriptions> options = new FirestoreRecyclerOptions.Builder<QuizDescriptions>()
                .setQuery(getQuizDatabase, QuizDescriptions.class)
                .build();


        adapter = new QuizAdapter(options);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.quizListRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new QuizAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Bundle bundle = getArguments();
                String login = bundle.getString("login");
                String path = documentSnapshot.getReference().getPath();

                Bundle arguments = new Bundle();
                QuizDescriptionFragment fragment = new QuizDescriptionFragment();
                fragment.setArguments(arguments);
                arguments.putString(KEY_PATH, path);
                arguments.putString("loginDetails2", login);
                System.out.println(login);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();

                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

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
