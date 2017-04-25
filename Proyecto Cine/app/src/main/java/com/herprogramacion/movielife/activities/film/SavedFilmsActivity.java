package com.herprogramacion.movielife.activities.film;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.adapters.database.firebase.FireBaseFilmsViewHolder;
import com.herprogramacion.movielife.models.Film;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedFilmsActivity extends AppCompatActivity {
    private DatabaseReference mFilmsReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.reciclador) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_group_search);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        mFilmsReference = FirebaseDatabase.getInstance().getReference().child("peliculas_favoritas");
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Film, FireBaseFilmsViewHolder>
                (Film.class, R.layout.item_lista_search, FireBaseFilmsViewHolder.class, mFilmsReference) {
            @Override
            protected void populateViewHolder(FireBaseFilmsViewHolder viewHolder, Film model, int position) {
                viewHolder.bindFilms(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
