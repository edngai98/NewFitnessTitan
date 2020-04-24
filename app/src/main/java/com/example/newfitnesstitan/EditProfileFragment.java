package com.example.newfitnesstitan;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newfitnesstitan.UserResults.Users;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

public class EditProfileFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference checkAcc = db.collection("users");
    private DocumentReference doc;

    private EditText user_first_name, user_last_name, user_id, user_password;
    private Button saveButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        user_first_name = rootView.findViewById(R.id.etFirstEdit);
        user_last_name = rootView.findViewById(R.id.etLastEdit);
        user_id = rootView.findViewById(R.id.etUserEdit);
        user_password = rootView.findViewById(R.id.etPassEdit);
        saveButton = rootView.findViewById(R.id.btnSaveAcc);

        Bundle bundle = getArguments();
        String login = bundle.getString("login");

        doc = db.collection("users").document(login);

        doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if(documentSnapshot.exists()) {
                    Users user = documentSnapshot.toObject(Users.class);
                    user_first_name.setText(user.getFirst());
                    user_last_name.setText(user.getLast());
                    user_id.setText(user.getUsername());
                    user_password.setText(user.getPassword());

                }
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.edit_profile_builder, null);
                Button mSave = mView.findViewById(R.id.btnSave);
                String first = user_first_name.getText().toString();
                String last = user_last_name.getText().toString();
                String username = user_id.getText().toString();
                String userPass = user_password.getText().toString();
                if(!username.isEmpty() && !userPass.isEmpty() && !first.isEmpty() && !last.isEmpty()) {
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    mSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            doc.update(
                                    "first", first,
                                    "last", last,
                                    "username", username,
                                    "password", userPass

                            );
                            Toast.makeText(getActivity(), "Saved Changes", Toast.LENGTH_SHORT).show();
                            dialog.hide();
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }
}
