package com.example.newfitnesstitan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class QuizAdapter extends FirestoreRecyclerAdapter<Quizzes, QuizAdapter.QuizHolder> {

    private QuizAdapter.OnItemClickListener listener;

    public QuizAdapter(@NonNull FirestoreRecyclerOptions<Quizzes> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QuizAdapter.QuizHolder holder, int position, @NonNull Quizzes quizzes) {
        holder.name.setText(quizzes.getName());
        holder.description.setText(quizzes.getDescription());
        //holder.result.setText(String.valueOf(quizzes.getResult()));
        //holder.result_bar.setRating(Float.parseFloat(String.valueOf(quizzes.getResult())));

    }

    @NonNull
    @Override
    public QuizAdapter.QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_card_rows, parent,false);
        return new QuizAdapter.QuizHolder(v);
    }

    public class QuizHolder extends RecyclerView.ViewHolder {
        TextView name, description, result;
        RatingBar result_bar;
        CardView start_quiz;

        public QuizHolder (View view) {
            super(view);
            name = view.findViewById(R.id.quiz_name);
            description = view.findViewById(R.id.quiz_short_description);
            //result = view.findViewById(R.id.quiz_result);
            //result_bar = view.findViewById(R.id.quiz_score_bar);

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