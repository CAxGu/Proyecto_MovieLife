package com.herprogramacion.movielife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.activities.film.Activity_detail_crud;
import com.herprogramacion.movielife.net.FirebaseReferences;
import com.herprogramacion.movielife.adapters.film.AdaptadorPeliSeries;
import com.herprogramacion.movielife.models.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento para la sección de "Inicio"
 */
public class FragmentoPeliSeries extends Fragment {
    private RecyclerView reciclador;
    private AdaptadorPeliSeries adaptador;
    private FloatingActionButton add;
    private List<Film> movies;

    public FragmentoPeliSeries() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_estrenos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add = (FloatingActionButton) view.findViewById(R.id.add);
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {//No es admin lo hace invisible
            if (user.getUid().equals("Y7TqnoVGYrVKvxKzH7ctc96b0xx1"))
                add.setVisibility(View.VISIBLE);
            else
                add.setVisibility(View.INVISIBLE);
        } else
            add.setVisibility(View.INVISIBLE);

        super.onActivityCreated(savedInstanceState);
        //Si esta visible añade el listener
        if (add.getVisibility() == View.VISIBLE) {
            Log.e("add", "Visble");
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), Activity_detail_crud.class));
                }
            });
        } else {
            Log.e("add", Integer.toString(add.getVisibility()));
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(FirebaseReferences.CRUD_REFERENCE);
        ref.child(FirebaseReferences.ESTRENOS_REFERNCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                movies = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Film post = postSnapshot.getValue(Film.class);
                    if (post != null) {
                        movies.add(post);
                    }
                }
                if (movies != null) {
                    adaptador = new AdaptadorPeliSeries(getContext(), movies);
                    reciclador.setAdapter(adaptador);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("BD_Firebase", databaseError.getMessage());
            }
        });
    }
}
