package com.example.newfitnesstitan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class LearningAdapter extends FirestoreRecyclerAdapter<LearningsModule, LearningAdapter.LearningHolder> {
        private LearningAdapter.OnItemClickListener listener;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LearningAdapter(@NonNull FirestoreRecyclerOptions<LearningsModule> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LearningAdapter.LearningHolder learningHolder, int i, @NonNull LearningsModule learningModule) {
        learningHolder.name.setText(learningModule.getName());
        learningHolder.summary.setText(learningModule.getSummary());

    }

    @NonNull
    @Override
    public LearningAdapter.LearningHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.learnings_rows, parent,false);
        return new LearningAdapter.LearningHolder(v);
    }
    public class LearningHolder extends RecyclerView.ViewHolder {
            TextView name, summary;

            public LearningHolder (View view){
                super(view);
                name = view.findViewById(R.id.learning_name);
                summary = view.findViewById(R.id.learning_description);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

    public void setOnItemClickListener(LearningAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }



}
