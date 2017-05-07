package com.herprogramacion.movielife.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.net.FirebaseReferences;
import com.herprogramacion.movielife.adapters.film.FavoritosAdapter;
import com.herprogramacion.movielife.adapters.film.FavoritosAdapterCines;
import com.herprogramacion.movielife.models.Cines;
import com.herprogramacion.movielife.models.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "MisFavoritos"
 */
public class FragmentoMisFavoritosCont extends Fragment {

    private static final String INDICE_SECCION
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    private FavoritosAdapter adaptador;
    private FavoritosAdapterCines adapterCines;
    private List<Film> movies;
    private List<Film> series;
    private List<Cines> ciness;

    public static FragmentoMisFavoritosCont nuevaInstancia(int indiceSeccion) {
        FragmentoMisFavoritosCont fragment = new FragmentoMisFavoritosCont();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_grupo_items, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador.setLayoutManager(layoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final int indiceSeccion = getArguments().getInt(INDICE_SECCION);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        //Saca las peliculas y series de peliculas_favoritas y las añade a su correspondiente List
        ref.child(FirebaseReferences.PELICULAS_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                movies = new ArrayList<>();
                series = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Film post = postSnapshot.getValue(Film.class);
                    if (post != null) {
                        if (post.getType().equals("movie")) {
                            movies.add(post);
                        }
                        if (post.getType().equals("series")) {
                            series.add(post);
                        }
                    }
                }
                switch (indiceSeccion){
                    case 0:
                        if (movies!=null){adaptador = new FavoritosAdapter(getContext(), movies);}
                        reciclador.setAdapter(adaptador);
                        break;
                    case 1:
                        if (series!=null){adaptador = new FavoritosAdapter(getContext(), series);}
                        reciclador.setAdapter(adaptador);
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("BD_Firebase", databaseError.getMessage());
            }
        });
        //Saca los ciness de cines_favoritos y las añade a su List
        ref.child(FirebaseReferences.CINES_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ciness = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Cines post = postSnapshot.getValue(Cines.class);
                    if (post != null) {
                        ciness.add(post);
                    }
                }
                switch (indiceSeccion) {
                    case 2:
                        if (ciness!=null){adapterCines = new FavoritosAdapterCines(getContext(), ciness);}
                        reciclador.setAdapter(adapterCines);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("BD_Firebase", databaseError.getMessage());
            }
        });

    }
}
