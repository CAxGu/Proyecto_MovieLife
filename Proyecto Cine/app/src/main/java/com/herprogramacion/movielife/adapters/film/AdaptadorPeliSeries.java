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
//        viewHolder.year.setText("$" + item.getYear());
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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.activities.film.Activity_detail_crud;
import com.herprogramacion.movielife.activities.film.FirebaseReferences;
import com.herprogramacion.movielife.activities.film.SearchDetailActivity;
import com.herprogramacion.movielife.models.Film;

import java.util.List;

/**
 * Adaptador para mostrar las comidas más pedidas en la sección "Inicio"
 */
public class AdaptadorPeliSeries
        extends RecyclerView.Adapter<AdaptadorPeliSeries.ViewHolder> implements ItemClickListener {

    private final Context context;
    private final List<Film> items;

    public AdaptadorPeliSeries(Context context, List<Film> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_estrenos, viewGroup, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Film item = items.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getPoster())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getTitle());
        viewHolder.anyo.setText("" + item.getYear());
    }

    public void onItemClick(View view, int position) {
        // Imagen a compartir entre transiciones
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getUid().equals("Y7TqnoVGYrVKvxKzH7ctc96b0xx1")){
                Activity_detail_crud.launch_crud((Activity) context, position, items);//Si esta registrado y es admin
            }else{
                SearchDetailActivity.launch((Activity) context, position, items);//Si esta registrado pero no es admin
            }
        }else{
            SearchDetailActivity.launch((Activity) context, position, items);//Si no esta registrado
        }
    }

    /**
     * ViewHolder para reciclar elementos
     */

    public void delete(int position) { //removes the row
        Film item = items.get(position);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseReferences.CRUD_REFERENCE).child(FirebaseReferences.ESTRENOS_REFERNCE);
        myRef.child(item.getImdbID()).removeValue();
        items.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView anyo;
        public ImageView imagen;

        public ItemClickListener listener;

        public ViewHolder(View v, ItemClickListener listener) {
            super(v);
            anyo = (TextView) v.findViewById(R.id.anyo_peliserie);
            nombre = (TextView) v.findViewById(R.id.anyo_peliculaserie);
            imagen = (ImageView) v.findViewById(R.id.miniatura_caratula);
            v.setOnClickListener(this);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    delete(getAdapterPosition());
                    return false;
                }
            });
            this.listener = listener;
        }

        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}

interface ItemClickListener {
    void onItemClick(View view, int position);

}





