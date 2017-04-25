package com.herprogramacion.movielife.activities.film;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.adapters.database.firebase.FireBaseCinesViewHolder;
import com.herprogramacion.movielife.models.Cines;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedCinesActivity extends AppCompatActivity{
    private static final String EXTRA_POSITION = "";
    private DatabaseReference mCinesReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.image_content) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCineDetails);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCinesReference = FirebaseDatabase.getInstance().getReference().child("cines_favoritos");
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Cines, FireBaseCinesViewHolder>
                (Cines.class, R.layout.cine_element, FireBaseCinesViewHolder.class, mCinesReference) {
            @Override
            protected void populateViewHolder(FireBaseCinesViewHolder viewHolder, Cines model, int position) {
                viewHolder.bindCines(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

}
