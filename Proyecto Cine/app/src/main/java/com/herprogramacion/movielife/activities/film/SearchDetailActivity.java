package com.herprogramacion.movielife.activities.film;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.models.Film;
import com.herprogramacion.movielife.net.PelisClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class SearchDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String EXTRA_POSITION = "search";
    private static List<Film> itemss ;
    private TextView mWebsiteLabel;
    private TextView description;
    private TextView lang;
    private TextView director;
    private TextView writer;
    private TextView year;
    private TextView actors;
    private TextView genre;
    private ImageView Poster;
    private TextView title;
    private TextView type;
    private PelisClient client;
    private String web;
    private static Film film;
    private RatingBar rating;
    private DatabaseReference crud_ref;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        setupViews(itemss,position);
        crud_ref = FirebaseDatabase.getInstance().getReference(FirebaseReferences.CRUD_REFERENCE);
        title = (TextView)findViewById(R.id.title);
        Poster = (ImageView)findViewById(R.id.imageView_user);
        type = (TextView) findViewById(R.id.type);
        mWebsiteLabel = (TextView) findViewById(R.id.websiteTextView);
        description = (TextView) findViewById(R.id.plot);
        rating = (RatingBar) findViewById(R.id.ratingbar);
        lang = (TextView) findViewById(R.id.lang);
        director = (TextView) findViewById(R.id.director);
        writer = (TextView) findViewById(R.id.writer);
        year = (TextView) findViewById(R.id.year);
        actors =(TextView) findViewById(R.id.actors);
        genre =(TextView) findViewById(R.id.genre);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        if(film != null){
            try{
                crud_ref.child(FirebaseReferences.ESTRENOS_REFERNCE).child(film.getImdbID()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getValue() != null ) {// si esta en firebase lo pintara como esta en firebase
                            film = dataSnapshot.getValue(Film.class);
                            title.setText(film.getTitle());
                            description.setText(film.getPlot());
                            rating.setRating((float) film.getRated());
                            lang.setText(film.getLanguage());
                            director.setText(film.getDirector());
                            writer.setText(film.getWriter());
                            year.setText(film.getYear());
                            actors.setText(film.getActors());
                            genre.setText(film.getGenre());
                            mWebsiteLabel.setText(film.getWeb());
                        }else{// si no esta en firebase lo pintara de la api
                            loadFilm(film);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("BD", databaseError.getMessage());
                    }
                });
            }catch (Exception e){
                Log.i("detail","Error firebase "+e.getMessage().toString());

            }
        }



        fab.setOnClickListener(this);
        mWebsiteLabel.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            try {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(film.getWeb()));
                startActivity(webIntent);
            }catch (Exception web){
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://duckduckgo.com/?q="+ film.getTitle()));
                startActivity(webIntent);
            }
        }
        if (v == fab) {
            DatabaseReference FilmsRef = FirebaseDatabase.getInstance().getReference().child("peliculas_favoritas");
            FilmsRef.push().setValue(film);
            Snackbar.make(v, R.string.agregado_favoritos, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    private void setupViews(List<Film> items, int position) {
        TextView type = (TextView) findViewById(R.id.type);
        TextView title = (TextView) findViewById(R.id.title);
        ImageView imagen = (ImageView) findViewById(R.id.imageView_user);

        film = items.get(position);
        Film item = items.get(position);
        setTitle(item.getTitle());
        type.setText(item.getType());
        title.setText(item.getTitle());
        Picasso.with(this).load(item.getPoster()).into(imagen);

    }

    // Populate data for the film
    private void loadFilm(final Film film) {
        // fetch extra film data from film API
        client = new PelisClient();
        client.getExtraFilmDetails(film.getImdbID(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.has("Website"))
                        SearchDetailActivity.film.setWeb(response.getString("Website"));
                    mWebsiteLabel.setText(film.getWeb());
                    if (response.has("Director")) {
                        SearchDetailActivity.film.setDirector(response.getString("Director"));
                        director.setText( SearchDetailActivity.film.getDirector());
                    }
                    if (response.has("Writer")){
                        SearchDetailActivity.film.setWriter(response.getString("Writer"));
                        writer.setText( SearchDetailActivity.film.getWriter());
                    }
                    if (response.has("Actors")) {
                        SearchDetailActivity.film.setActors(response.getString("Actors"));
                        actors.setText( SearchDetailActivity.film.getActors());
                    }
                    if (response.has("Plot")) {
                        SearchDetailActivity.film.setPlot(response.getString("Plot"));
                        description.setText(SearchDetailActivity.film.getPlot());
                    }
                    if (response.has("imdbRating")){
                        SearchDetailActivity.film.setRated(response.getDouble("imdbRating"));
                        rating.setRating((float) SearchDetailActivity.film.getRated());
                    }
                    if (response.has("Year")){
                        SearchDetailActivity.film.setYear(response.getString("Year"));
                        year.setText( SearchDetailActivity.film.getYear());
                    }
                    if (response.has("Language")){
                        SearchDetailActivity.film.setLanguage(response.getString("Language"));
                        lang.setText( SearchDetailActivity.film.getLanguage());
                    }
                    if (response.has("Genre")){
                        SearchDetailActivity.film.setGenre(response.getString("Genre"));
                        genre.setText( SearchDetailActivity.film.getGenre());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public  static void launch(Activity context, int position, List<Film> items){
        Intent intent = new Intent(context,SearchDetailActivity.class);
        intent.putExtra(EXTRA_POSITION,position);
        itemss=items;
        film = items.get(position);
        context.startActivity(intent);
    }
}
