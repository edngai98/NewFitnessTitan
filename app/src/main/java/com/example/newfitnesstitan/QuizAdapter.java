package com.example.newfitnesstitan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newfitnesstitan.QuizContent.QuizDescriptions;
import com.example.newfitnesstitan.UserResults.Users;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.protobuf.compiler.PluginProtos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class QuizAdapter extends FirestoreRecyclerAdapter<QuizDescriptions, QuizAdapter.QuizHolder> {

    public Context context;
    private QuizAdapter.OnItemClickListener listener;

    public QuizAdapter(@NonNull FirestoreRecyclerOptions<QuizDescriptions> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuizAdapter.QuizHolder holder, int position, @NonNull QuizDescriptions quizDescriptions) {

        holder.name.setText(quizDescriptions.getName());
        //holder.description.setText(quizDescriptions.getDescription());
        Glide.with(holder.itemView.getContext())
                .load(FirebaseStorage.getInstance().getReferenceFromUrl(quizDescriptions.getImage()))
                .centerCrop()
                .override(250,250)
                .into(holder.image);

    }

    @NonNull
    @Override
    public QuizAdapter.QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_card_rows, parent,false);
        return new QuizAdapter.QuizHolder(v);
    }

    public class QuizHolder extends RecyclerView.ViewHolder {
        TextView name, description;
        ImageView image;


        public QuizHolder (View view) {
            super(view);
            name = view.findViewById(R.id.quiz_name);
            //description = view.findViewById(R.id.quiz_short_description);
            image = view.findViewById(R.id.imageView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(QuizAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


}
