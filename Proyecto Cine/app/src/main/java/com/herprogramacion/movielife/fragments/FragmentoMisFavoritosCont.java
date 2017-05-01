package com.herprogramacion.movielife.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.adapters.film.AdaptadorMisFavoritos;
import com.herprogramacion.movielife.models.Pelis;

/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "MisFavoritos"
 */
public class FragmentoMisFavoritosCont extends Fragment {

    private static final String INDICE_SECCION
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorMisFavoritos adaptador;

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
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador.setLayoutManager(layoutManager);

        int indiceSeccion = getArguments().getInt(INDICE_SECCION);

        switch (indiceSeccion) {
            case 0:
                adaptador = new AdaptadorMisFavoritos(getContext(),Pelis.PELICULAS);
                break;
            case 1:
                adaptador = new AdaptadorMisFavoritos(getContext(), Pelis.SERIES);
                break;
        }

        reciclador.setAdapter(adaptador);

        return view;
    }

}
