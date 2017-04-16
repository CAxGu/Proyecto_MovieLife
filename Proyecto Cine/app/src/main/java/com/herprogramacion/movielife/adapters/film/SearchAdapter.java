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
import com.herprogramacion.movielife.activities.film.SearchDetailActivity;
import com.herprogramacion.movielife.models.Film;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements ItemClickListener {

    private final Context context;
    private final List<Film> items;

    public SearchAdapter(Context context, List<Film> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();

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
        viewHolder.year.setText("" + item.getYear());
    }

    public void onItemClick(View view, int position) {
        // Imagen a compartir entre transiciones
        //ImageView sharedImage = ImageView.(R.id.ivBookCover);
        SearchDetailActivity.launch((Activity) context, position, items);
    }

    /**
     * ViewHolder para reciclar elementos
     */

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView title;
        public TextView year;
        public ImageView imagen;

        public ItemClickListener listener;

        public ViewHolder(View v, ItemClickListener listener) {
            super(v);
            title = (TextView) v.findViewById(R.id.title_item);
            year = (TextView) v.findViewById(R.id.year_title);
            imagen = (ImageView) v.findViewById(R.id.image_item);
            v.setOnClickListener(this);
            this.listener = listener;
        }

        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}



