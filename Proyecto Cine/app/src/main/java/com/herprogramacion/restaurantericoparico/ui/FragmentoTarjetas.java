package com.herprogramacion.restaurantericoparico.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.herprogramacion.restaurantericoparico.R;

/**
 * Fragmento para la pestaña "TARJETAS" de la sección "Mi Cuenta"
 */
public class FragmentoTarjetas extends Fragment {

    public FragmentoTarjetas() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_tarjetas, container, false);
    }


}
