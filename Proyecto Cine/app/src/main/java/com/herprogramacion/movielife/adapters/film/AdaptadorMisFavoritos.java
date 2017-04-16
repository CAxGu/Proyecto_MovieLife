package com.herprogramacion.movielife.adapters.film;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.activities.film.ActividadDetallesPeliSeries;
import com.herprogramacion.movielife.models.Pelis;

import java.util.List;

/**
 * Adaptador para mostrar las peliculas/series clasificadas
 */
public class AdaptadorMisFavoritos
        extends RecyclerView.Adapter<AdaptadorMisFavoritos.ViewHolder>implements ItemClickListener {

    private final Context context;
    private final List<Pelis> items;

    public AdaptadorMisFavoritos(Context context, List<Pelis> items) {
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
                .inflate(R.layout.item_lista_misfavoritos, viewGroup, false);
        return new ViewHolder(v,this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Pelis item = items.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.anyo.setText(""+item.getAnyo());
    }

    public void onItemClick(View view, int position) {
        // Imagen a compartir entre transiciones
        View sharedImage = view.findViewById(R.id.imageView);
        ActividadDetallesPeliSeries.launch((Activity) context, position, sharedImage,items);
    }

    /**
     * ViewHolder para reciclar elementos
     */

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView anyo;
        public ImageView imagen;

        public ItemClickListener listener;

        public ViewHolder(View v,ItemClickListener listener) {
            super(v);
            anyo = (TextView) v.findViewById(R.id.anyo_peliserie);
            nombre = (TextView) v.findViewById(R.id.anyo_peliculaserie);
            imagen = (ImageView) v.findViewById(R.id.miniatura_caratula);
            v.setOnClickListener(this);
            this.listener=listener;
        }

        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }


}




