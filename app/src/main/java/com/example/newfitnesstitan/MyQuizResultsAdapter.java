package com.example.newfitnesstitan;

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

public class MyQuizResultsAdapter extends FirestoreRecyclerAdapter<MyQuizResults, MyQuizResultsAdapter.MyQuizHolder> {

    private OnItemClickListener listener;

    public MyQuizResultsAdapter(@NonNull FirestoreRecyclerOptions<MyQuizResults> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyQuizHolder holder, int position, @NonNull MyQuizResults myQuizResults) {
        holder.name.setText(myQuizResults.getName());
        holder.result.setText(String.valueOf(myQuizResults.getResult()));
        holder.result_bar.setRating(Float.parseFloat(String.valueOf(myQuizResults.getResult())));

    }

    @NonNull
    @Override
    public MyQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_card_rows, parent,false);
        return new MyQuizHolder(v);
    }

    public class MyQuizHolder extends RecyclerView.ViewHolder {
        TextView name, result;
        RatingBar result_bar;
        CardView start_quiz;

        public MyQuizHolder(View view) {
            super(view);
            name = view.findViewById(R.id.quiz_name);
            //description = view.findViewById(R.id.quiz_description_text);
            //result = view.findViewById(R.id.quiz_result);
            //result_bar = view.findViewById(R.id.quiz_score_bar);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
