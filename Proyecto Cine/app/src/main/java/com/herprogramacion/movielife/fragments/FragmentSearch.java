package com.herprogramacion.movielife.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.adapters.film.SearchAdapter;

import static com.herprogramacion.movielife.activities.film.Search_Activity.boocs;


public class FragmentSearch extends Fragment {
  //  private static final String INDICE_SECCION = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";
    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private SearchAdapter adaptador;
    private ArrayAdapter<SearchAdapter> items;

    public FragmentSearch() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador.setLayoutManager(layoutManager);
        adaptador = new SearchAdapter(getContext(),boocs);
        reciclador.setAdapter(adaptador);
        return view;
    }
}
