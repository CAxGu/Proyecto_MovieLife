//package com.herprogramacion.restaurantericoparico.adapters.comida;
//
//import android.app.Activity;
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.herprogramacion.restaurantericoparico.R;
//import com.herprogramacion.restaurantericoparico.activities.comida.ActividadDetallesPeliSeries;
//import com.herprogramacion.restaurantericoparico.models.Comida;
//
//import java.util.List;
//
///**
// * Adaptador para mostrar las comidas más pedidas en la sección "Inicio"
// */
//public class AdaptadorPeliSeries
//        extends RecyclerView.Adapter<AdaptadorPeliSeries.ViewHolder>implements ItemClickListener {
//
//    private final Context context;
//    private final List<Comida> items = Comida.COMIDAS_POPULARES;
//
//    public AdaptadorPeliSeries(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public int getItemCount() {
//        return Comida.COMIDAS_POPULARES.size();
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View v = LayoutInflater.from(viewGroup.getContext())
//                .inflate(R.layout.item_lista_estrenos, viewGroup, false);
//        return new ViewHolder(v,this);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int i) {
//        Comida item = Comida.COMIDAS_POPULARES.get(i);
//
//        Glide.with(viewHolder.itemView.getContext())
//                .load(item.getIdDrawable())
//                .centerCrop()
//                .into(viewHolder.imagen);
//        viewHolder.title.setText(item.getNombre());
//        viewHolder.year.setText("$" + item.getAnyo());
//    }
//
//    public void onItemClick(View view, int position) {
//        // Imagen a compartir entre transiciones
//        View sharedImage = view.findViewById(R.id.imageView);
//        ActividadDetallesPeliSeries.launch((Activity) context, position, sharedImage,items);
//    }
//
//    /**
//     * ViewHolder para reciclar elementos
//     */
//
//    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        // Campos respectivos de un item
//        public TextView title;
//        public TextView year;
//        public ImageView imagen;
//
//        public ItemClickListener listener;
//
//        public ViewHolder(View v,ItemClickListener listener) {
//            super(v);
//            title = (TextView) v.findViewById(R.id.nombre_comida);
//            year = (TextView) v.findViewById(R.id.precio_comida);
//            imagen = (ImageView) v.findViewById(R.id.miniatura_comida);
//            v.setOnClickListener(this);
//            this.listener=listener;
//        }
//
//        public void onClick(View v) {
//            listener.onItemClick(v, getAdapterPosition());
//        }
//    }
//}
//interface ItemClickListener {
//    void onItemClick(View view, int position);
//
//}

package com.herprogramacion.movielife.adapters.film;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.activities.film.ActividadDetallesPeliSeries;
import com.herprogramacion.movielife.activities.film.CinesDetailsActivity;
import com.herprogramacion.movielife.models.Cines;
import com.herprogramacion.movielife.models.Pelis;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CinesDetailsAdapter
        extends RecyclerView.Adapter<CinesDetailsAdapter.ViewHolder>implements ItemClickListener {

    private final Context context;
    private final List<Cines> cines = Cines.CINE;

    public CinesDetailsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return Cines.CINE.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cine_element, viewGroup, false);
        return new ViewHolder(v,this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Cines cines = Cines.CINE.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(cines.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        Glide.with(viewHolder.itemView.getContext())
                .load(R.drawable.filmstrip)
                .centerCrop()
                .into(viewHolder.icono);
        viewHolder.nombre.setText(cines.getNombre());
        viewHolder.adress.setText(cines.getAdress());
    }

    public void onItemClick(View view, int position) {
        // Imagen a compartir entre transiciones
        View sharedImage = view.findViewById(R.id.imageView);
        CinesDetailsActivity.launch((Activity) context, position, sharedImage,cines);
    }

    /**
     * ViewHolder para reciclar elementos
     */

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item

        public ImageView imagen;
        public ImageView icono;
        public TextView nombre;
        public TextView adress;

        public ItemClickListener listener;

        public ViewHolder(View v,ItemClickListener listener) {
            super(v);

            imagen = (ImageView) v.findViewById(R.id.cine_image);
            icono = (ImageView) v.findViewById(R.id.cine_icon);
            nombre = (TextView) v.findViewById(R.id.cine_name);
            adress = (TextView) v.findViewById(R.id.cine_extradata);
            v.setOnClickListener(this);
            this.listener=listener;
        }

        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}





