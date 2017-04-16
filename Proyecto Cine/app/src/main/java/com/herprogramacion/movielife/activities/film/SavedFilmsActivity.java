package com.herprogramacion.movielife.activities.film;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.test.ServiceTestCase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.adapters.film.FireBaseFilmsViewHolder;
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

        setToolbar();
        
        mFilmsReference = FirebaseDatabase.getInstance().getReference().child("peliculas_favoritas");
        setUpFirebaseAdapter();
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
