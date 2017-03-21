package com.herprogramacion.restaurantericoparico.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.herprogramacion.restaurantericoparico.R;
import com.herprogramacion.restaurantericoparico.modelo.Comida;

import java.util.List;

/**
 * Adaptador para mostrar las comidas más pedidas en la sección "Inicio"
 */
public class AdaptadorCategorias
        extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder>implements ItemClickListener {

    private final Context context;
    private final List<Comida> items;

    public AdaptadorCategorias(Context context,List<Comida> items) {
        this.context = context;
        this.items=items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_categorias, viewGroup, false);
        return new ViewHolder(v,this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Comida item = items.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.precio.setText("$" + item.getPrecio());
    }

    public void onItemClick(View view, int position) {
        // Imagen a compartir entre transiciones
        View sharedImage = view.findViewById(R.id.imageView);
        ActividadDetallesInicio.launch((Activity) context, position, sharedImage,items);
    }

    /**
     * ViewHolder para reciclar elementos
     */

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public ImageView imagen;

        public ItemClickListener listener;

        public ViewHolder(View v,ItemClickListener listener) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre_comida);
            precio = (TextView) v.findViewById(R.id.precio_comida);
            imagen = (ImageView) v.findViewById(R.id.miniatura_comida);
            v.setOnClickListener(this);
            this.listener=listener;
        }

        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }


}




