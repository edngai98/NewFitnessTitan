package com.example.newfitnesstitan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class LearningAdapter extends FirestoreRecyclerAdapter<LearningsModule, LearningAdapter.LearningHolder> {

        public Context context;
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
        Glide.with(learningHolder.itemView.getContext())
                .load(FirebaseStorage.getInstance().getReferenceFromUrl(learningModule.getImage()))
                .centerCrop()
                .override(250,250)
                .into(learningHolder.image);

    }

    @NonNull
    @Override
    public LearningAdapter.LearningHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.learnings_rows, parent,false);
        return new LearningAdapter.LearningHolder(v);
    }
    public class LearningHolder extends RecyclerView.ViewHolder {
            TextView name, summary;
            ImageView image;

            public LearningHolder (View view){
                super(view);
                name = view.findViewById(R.id.learning_name);
                summary = view.findViewById(R.id.learning_description);
                image = view.findViewById(R.id.imageView3);

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
