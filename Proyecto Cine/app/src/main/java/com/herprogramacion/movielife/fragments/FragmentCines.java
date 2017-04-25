package com.herprogramacion.movielife.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.adapters.film.CinesAdapter;

/**
 * Fragmento para la sección de "Cerca de ti"
 */
public class FragmentCines extends Fragment {
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private CinesAdapter adaptador;

    public FragmentCines() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.image_content);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        adaptador = new CinesAdapter(getContext());
        reciclador.setAdapter(adaptador);
        return view;
    }

}
