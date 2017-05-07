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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.net.FirebaseReferences;
import com.herprogramacion.movielife.activities.film.DetailActivity;
import com.herprogramacion.movielife.models.Film;

import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolder> implements ItemClickListener {

    private final Context context;
    private final List<Film> items;


    public FavoritosAdapter(final Context context, final List<Film> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();

    }
    private void delete(int position) { //removes the row
        Film item = items.get(position);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.PELICULAS_REFERENCE);
        myRef.child(item.getImdbID()).removeValue();
        items.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_search, viewGroup, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Film item = items.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getPoster())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.title.setText(item.getTitle());
        viewHolder.year.setText(item.getYear());
    }

    public void onItemClick(View view, int position) {
        DetailActivity.launch((Activity) context, position, items);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView title;
        public TextView year;
        public ImageView imagen;

        ItemClickListener listener;

        public ViewHolder(View v, ItemClickListener listener) {
            super(v);
            title = (TextView) v.findViewById(R.id.title_item);
            year = (TextView) v.findViewById(R.id.year_title);
            imagen = (ImageView) v.findViewById(R.id.image_item);
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



