package com.herprogramacion.movielife.activities.film;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private ImageView ivBookCover;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvPublisher;
    private TextView mWebsiteLabel;
    private TextView tvPageCount;
    private TextView description;
    private TextView lang;
    private TextView director;
    private TextView writer;
    private TextView year;
    private TextView actors;
    private TextView genre;
    private PelisClient client;
    private String web;
    private static Film film;
    private RatingBar rating;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        loadFilm(film);
        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        setupViews(itemss,position);


        tvTitle = (TextView) findViewById(R.id.type);
        ivBookCover = (ImageView) findViewById(R.id.imageView);
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
            Snackbar.make(v, "Añadido a Mis Favoritos", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    private void setupViews(List<Film> items, int position) {
        TextView type = (TextView) findViewById(R.id.type);
        TextView title = (TextView) findViewById(R.id.title);
        ImageView imagen = (ImageView) findViewById(R.id.imageView);

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
                    if (response.has("Director")) {
                        SearchDetailActivity.film.setDirector(response.getString("Director"));
                        director.setText("Director : "+ SearchDetailActivity.film.getDirector());
                    }
                    if (response.has("Writer")){
                        SearchDetailActivity.film.setWriter(response.getString("Writer"));
                        writer.setText("Writer : "+ SearchDetailActivity.film.getWriter());
                }
                    if (response.has("Actors")) {
                        SearchDetailActivity.film.setActors(response.getString("Actors"));
                        actors.setText("Actors : "+ SearchDetailActivity.film.getActors());
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
                        year.setText("Year : "+ SearchDetailActivity.film.getYear());
                    }
                    if (response.has("Language")){
                        SearchDetailActivity.film.setLanguage(response.getString("Language"));
                        lang.setText("Year : "+ SearchDetailActivity.film.getLanguage());
                    }
                    if (response.has("Genre")){
                        SearchDetailActivity.film.setGenre(response.getString("Genre"));
                        genre.setText("Genre : "+ SearchDetailActivity.film.getGenre());
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
