package com.herprogramacion.restaurantericoparico.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.herprogramacion.restaurantericoparico.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para poblar la lista de direcciones de la sección "Mi Cuenta"
 */
public class AdaptadorDirecciones
        extends RecyclerView.Adapter<AdaptadorDirecciones.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView direccion;
        public TextView departamento;
        public TextView ciudad;
        public TextView telefono;

        public ViewHolder(View v) {
            super(v);
            direccion = (TextView) v.findViewById(R.id.texto_direccion);
            departamento = (TextView) v.findViewById(R.id.texto_departamento);
            ciudad = (TextView) v.findViewById(R.id.texto_ciudad);
            telefono = (TextView) v.findViewById(R.id.texto_telefono);
        }
    }


    public AdaptadorDirecciones() {
    }

    @Override
    public int getItemCount() {
        return Direccion.DIRECCIONES.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_direccion, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Direccion item = Direccion.DIRECCIONES.get(i);
        viewHolder.direccion.setText(item.numeroDireccion);
        viewHolder.departamento.setText(item.departamento);
        viewHolder.ciudad.setText(item.ciudad);
        viewHolder.telefono.setText(item.telefono);
    }

    /**
     * Modelo de datos para probar el adaptador
     */
    public static class Direccion {
        public String numeroDireccion;
        public String departamento;
        public String ciudad;
        public String telefono;

        public Direccion(String numeroDireccion, String departamento,
                         String ciudad, String telefono) {
            this.numeroDireccion = numeroDireccion;
            this.departamento = departamento;
            this.ciudad = ciudad;
            this.telefono = telefono;
        }

        public final static List<Direccion> DIRECCIONES = new ArrayList<Direccion>();

        static {
            DIRECCIONES.add(new Direccion("Cra 24 #2C-50", "Valle", "Cali", "3459821"));
            DIRECCIONES.add(new Direccion("Calle 100 Trans. 23", "Valle", "Cali", "4992600"));
            DIRECCIONES.add(new Direccion("Ave. 3ra N. #20-10", "Valle", "Cali", "4400725"));
        }
    }


}