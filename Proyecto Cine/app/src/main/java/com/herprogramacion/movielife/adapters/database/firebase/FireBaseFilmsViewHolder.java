package com.herprogramacion.movielife.adapters.database.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.activities.film.FilmsDetailActivity;
import com.herprogramacion.movielife.models.Film;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;


import java.util.ArrayList;
import java.util.Iterator;

public class FireBaseFilmsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    View mView;
    Context mContext;

    public FireBaseFilmsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bindFilms(Film films) {
        ImageView FilmImageView = (ImageView) mView.findViewById(R.id.image_item);
        TextView nameTextView = (TextView) mView.findViewById(R.id.title_item);
        TextView YearTextView = (TextView) mView.findViewById(R.id.year_title);

        Picasso.with(mContext)
                .load(films.getPoster())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(FilmImageView);

        nameTextView.setText(films.getTitle());
        YearTextView.setText(films.getYear());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Film> films = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("peliculas_favoritas");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    films.add(snapshot.getValue(Film.class));
                }
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, FilmsDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("peliculas_favoritas", Parcels.wrap(films));
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

//    //VACIA FAVORITOS
//    public boolean onLongClick(final View view) { //reference.removeValue();
//        int itemPosition = getLayoutPosition();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("peliculas_favoritas");
////        DatabaseReference reference = getRef(position);
//        //reference.removeValue();
//        reference.removeValue(new DatabaseReference.CompletionListener() {
//            public void onComplete(DatabaseError error, DatabaseReference ref) {
//
//                if(error == null)
//                    Toast.makeText(view.getContext(), "Borrado de Mis Fvoritos", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        return true;
//    }

    //VACIA FAVORITOS


    public boolean onLongClick(final View view) { //reference.removeValue();
        final int itemPosition = getLayoutPosition();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("peliculas_favoritas");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    boolean borrado = false;
                    int cont = 0;
                    Iterator iterator =  dataSnapshot.getChildren().iterator();
                    while(iterator.hasNext()){
                        DataSnapshot firstChild =(DataSnapshot) iterator.next();
                        if(cont==itemPosition) {
                            firstChild.getRef().removeValue();
                            borrado = true;
                            break;
                        }
                        cont++;
                    }

                    if(borrado){
                        Toast.makeText(view.getContext(), "Borrado de Mis Favoritos", Toast.LENGTH_SHORT).show();
                    }

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
       /** reference.removeValue(new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {

                if(error == null)
                    Toast.makeText(view.getContext(), "Borrado de Mis Favoritos", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
        return true;
    }



}