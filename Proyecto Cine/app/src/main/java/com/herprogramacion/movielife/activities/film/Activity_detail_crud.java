package com.herprogramacion.movielife.activities.film;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.models.Film;
import com.herprogramacion.movielife.net.PelisClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.herprogramacion.movielife.R.id.plot;

/**
 * Created by root on 22/04/17.
 */

public class Activity_detail_crud extends ActionBarActivity implements View.OnClickListener {

    private static final String EXTRA_POSITION = "search";
    private static List<Film> itemss;
    private ImageView Poster;
    private EditText type;
    private EditText mWebsiteLabel;
    private FloatingActionButton fabs;
    private FloatingActionButton refresh_poster;
    private EditText description;
    private EditText lang;
    private EditText director;
    private EditText writer;
    private EditText year;
    private EditText actors;
    private EditText genre;
    private EditText imdbID;
    private EditText title;
    private EditText Poster_url;
    private Button api_search;

    private static Film film;
    private RatingBar rating;
    private DatabaseReference crud_ref;
    ///Crontoladores de Errores
    private TextInputLayout til_imdbID;
    private TextInputLayout til_year;
    private TextInputLayout til_posterurl;
    private TextInputLayout til_weburl;
    private static final String plantilla_imdbID = "^([a-z]{2})([0-9]{7})$";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_crud);
        crud_ref = FirebaseDatabase.getInstance().getReference(FirebaseReferences.CRUD_REFERENCE);
        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        if (itemss != null) {
            setupViews(itemss, position);
        }
        fabs = (FloatingActionButton) findViewById(R.id.fab);
        refresh_poster = (FloatingActionButton) findViewById(R.id.refresh);

        this.til_weburl = (TextInputLayout) findViewById(R.id.til_weburl);
        this.til_imdbID = (TextInputLayout) findViewById(R.id.til_imdbID);
        this.til_year = (TextInputLayout) findViewById(R.id.til_year);
        this.til_posterurl = (TextInputLayout) findViewById(R.id.til_posterurl);

        title = (EditText) findViewById(R.id.title);
        imdbID = (EditText) findViewById(R.id.imdbID);
        type = (EditText) findViewById(R.id.type);
        Poster = (ImageView) findViewById(R.id.imageView_user);
        mWebsiteLabel = (EditText) findViewById(R.id.websiteTextView);
        description = (EditText) findViewById(plot);
        rating = (RatingBar) findViewById(R.id.ratingbar);
        lang = (EditText) findViewById(R.id.lang);
        director = (EditText) findViewById(R.id.director);
        writer = (EditText) findViewById(R.id.writer);
        year = (EditText) findViewById(R.id.year);
        actors = (EditText) findViewById(R.id.actors);
        genre = (EditText) findViewById(R.id.genre);
        Poster_url = (EditText) findViewById(R.id.url_poster);
        fabs.setOnClickListener(this);
        refresh_poster.setOnClickListener(this);
        api_search = (Button) findViewById(R.id.api_imdb);
        api_search.setOnClickListener(this);
        if (film != null) {
            Glide.with(this).load(film.getPoster()).centerCrop().into(Poster);
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
            imdbID.setText(film.getImdbID());
            Poster_url.setText(film.getPoster());
        }
        if (film != null) {//Si esta en Firebase se actualizara
            try {
                crud_ref.child(FirebaseReferences.ESTRENOS_REFERNCE).child(film.getImdbID()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getValue() != null) {
                            film = dataSnapshot.getValue(Film.class);
                            title.setText(film.getTitle());
                            description.setText(film.getPlot());
                            rating.setRating((float) film.getRated());
                            lang.setText(film.getLanguage());
                            type.setText(film.getType());
                            director.setText(film.getDirector());
                            writer.setText(film.getWriter());
                            year.setText(film.getYear());
                            actors.setText(film.getActors());
                            genre.setText(film.getGenre());
                            mWebsiteLabel.setText(film.getWeb());
                            imdbID.setText(film.getImdbID());
                            Poster_url.setText(film.getPoster());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("BD", databaseError.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.i("detail", e.getMessage());
            }
        }
        imdbID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verify_ImdbID(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verify_year(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mWebsiteLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verify_Web(til_weburl, String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Poster_url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verify_Web(til_posterurl, String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void onClick(View v) {
        if (v == fabs) {

            //Falten les comprobacions ,proximamente .Suposem que el admin no la lie
            String imdbID = this.imdbID.getText().toString();
            String plot = description.getText().toString();
            String director = this.director.getText().toString();
            String writer = this.writer.getText().toString();
            String actors = this.actors.getText().toString();
            String title = this.title.getText().toString();
            double rated = rating.getRating();
            String year = this.year.getText().toString();
            String type = this.type.getText().toString();
            String web = this.mWebsiteLabel.getText().toString();
            String genre = this.genre.getText().toString();
            String language = this.lang.getText().toString();
            String Poster = this.Poster_url.getText().toString();

            //validar datos importantes
            boolean imdb=verify_ImdbID(imdbID);
            boolean anyo=verify_year(year);
            boolean poster=verify_Web(til_posterurl,Poster);
            boolean website=verify_Web(til_weburl,web);
            if(imdb && anyo && poster && website) {
                //crea la Film que se subira a Firebase
                final Film newfilm = new Film(imdbID, plot, director, writer, actors, title, rated, year, type, web, genre, language, Poster);
                //Lo sube si no se modifico el imdbID , si se modifico pregunta si quieres eliminar el anterior Film
                if ((film == null) || (newfilm.getImdbID().equals(film.getImdbID()))) {
                    crud_ref.child(FirebaseReferences.ESTRENOS_REFERNCE).child(newfilm.getImdbID()).setValue(newfilm);//ponemos el id como key para que no se repita
                    finish();
                    startActivity(new Intent(Activity_detail_crud.this, ActividadPrincipal.class));

                } else {
                    //Si existe film pero esta vacio
                    if (film.getImdbID() == null) {
                        crud_ref.child(FirebaseReferences.ESTRENOS_REFERNCE).child(newfilm.getImdbID()).setValue(newfilm);//ponemos el id como key para que no se repita
                        finish();
                        startActivity(new Intent(Activity_detail_crud.this, ActividadPrincipal.class));
                    } else {
                        //Si entra aqui es porque supuestament se a cambiado el imdbID
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Activity_detail_crud.this);

                        // set title
                        alertDialogBuilder.setTitle("Your Title");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Do you want to delete the item with imdbID " + film.getImdbID() + " ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference(FirebaseReferences.CRUD_REFERENCE).child(FirebaseReferences.ESTRENOS_REFERNCE);
                                        myRef.child(film.getImdbID()).removeValue();
                                        myRef.child(newfilm.getImdbID()).setValue(newfilm);
                                        finish();
                                        startActivity(new Intent(Activity_detail_crud.this, ActividadPrincipal.class));

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        crud_ref.child(FirebaseReferences.ESTRENOS_REFERNCE).child(newfilm.getImdbID()).setValue(newfilm);
                                        dialog.cancel();
                                        finish();
                                        startActivity(new Intent(Activity_detail_crud.this, ActividadPrincipal.class));

                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it and add to firebase
                        alertDialog.show();
                    }
                }
            }else{
                Toast.makeText(getApplicationContext(),"Compureba los campos",Toast.LENGTH_LONG).show();
            }
        }
        if (v == refresh_poster) {
            try {
                Glide.with(this).load(Poster_url.getText().toString()).centerCrop().into(Poster);
            } catch (Exception e) {
                Glide.with(this).load("https://www.materialui.co/materialIcons/image/broken_image_black_192x192.png").centerCrop().into(Poster);
            }
        }
        if (v == api_search) {
            if(film == null)
                film = new Film();
            loadFilm(film);
        }
    }

    private void setupViews(List<Film> items, int position) {
        type = (EditText) findViewById(R.id.type);
        title = (EditText) findViewById(R.id.title);
        ImageView imagen = (ImageView) findViewById(R.id.imageView_user);
        if (film != null) {
            Film item = film;
            setTitle(item.getTitle());
            type.setText(item.getType());
            title.setText(item.getTitle());
            Glide.with(this).load(item.getPoster()).centerCrop().into(imagen);
        }
    }
//Busca con el imdbID el item en la api y lo pinta
    private void loadFilm(final Film film) {
        // fetch extra film data from film API
        PelisClient client = new PelisClient();
        client.getExtraFilmDetails(imdbID.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.has("Title")) {
                        Activity_detail_crud.film.setTitle(response.getString("Title"));
                        title.setText(Activity_detail_crud.film.getTitle());
                    }
                    if (response.has("Year")) {
                        Activity_detail_crud.film.setYear(response.getString("Year"));
                        year.setText(Activity_detail_crud.film.getYear());
                    }
                    if (response.has("Genre")) {
                        Activity_detail_crud.film.setGenre(response.getString("Genre"));
                        genre.setText(Activity_detail_crud.film.getGenre());
                    }
                    if (response.has("Director")) {
                        Activity_detail_crud.film.setDirector(response.getString("Director"));
                        director.setText(Activity_detail_crud.film.getDirector());
                    }
                    if (response.has("Writer")) {
                        Activity_detail_crud.film.setWriter(response.getString("Writer"));
                        writer.setText(Activity_detail_crud.film.getWriter());
                    }
                    if (response.has("Actors")) {
                        Activity_detail_crud.film.setActors(response.getString("Actors"));
                        actors.setText(Activity_detail_crud.film.getActors());
                    }
                    if (response.has("Plot")) {
                        Activity_detail_crud.film.setPlot(response.getString("Plot"));
                        description.setText(Activity_detail_crud.film.getPlot());
                    }
                    if (response.has("Language")) {
                        Activity_detail_crud.film.setLanguage(response.getString("Language"));
                        lang.setText(Activity_detail_crud.film.getLanguage());
                    }
                    if (response.has("Type")) {
                        Activity_detail_crud.film.setType(response.getString("Type"));
                        type.setText(Activity_detail_crud.film.getType());
                    }
                    if (response.has("Website")) {
                        Activity_detail_crud.film.setWeb(response.getString("Website"));
                        mWebsiteLabel.setText(film.getWeb());
                    }
                    if (response.has("Poster")) {
                        Activity_detail_crud.film.setPoster(response.getString("Poster"));
                        Glide.with(getApplicationContext()).load(film.getPoster()).centerCrop().into(Poster);
                        Poster_url.setText(film.getPoster());
                    }
                    if (response.has("imdbRating")) {
                        Activity_detail_crud.film.setRated(response.getDouble("imdbRating"));
                        rating.setRating((float) Activity_detail_crud.film.getRated());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void launch_crud(Activity context, int position, List<Film> items) {
        Intent intent = new Intent(context, Activity_detail_crud.class);
        intent.putExtra(EXTRA_POSITION, position);
        itemss = items;
        film = items.get(position);
        context.startActivity(intent);
    }


    ///Funciones para verificar
    public boolean verify_ImdbID(String id) {
        Pattern pattern = Pattern.compile(plantilla_imdbID);
        Matcher matcher = pattern.matcher(id);
        if (matcher.matches()) {
            til_imdbID.setError(null);
            return true;
        }
        til_imdbID.setError("imdbID inválido");
        return false;
    }

    private boolean verify_Web(TextInputLayout til, String web_url) {
        if (!Patterns.WEB_URL.matcher(web_url).matches()) {
            til.setError("URL inválido");
            return false;
        } else {
            til.setError(null);
        }

        return true;
    }

    private boolean verify_year(String year) {
        try {
            int ano = Integer.valueOf(year);
            int ano_actual = year_now();
            if (ano >= ano_actual && ano <= (ano_actual + 3)) {
                til_year.setError(null);
                return true;
            } else {
                til_year.setError("Year invalido");
                return false;
            }

        } catch (Exception e) {
            til_year.setError("Year invalido");
            return false;
        }
    }

    public int year_now() {
        Calendar fecha = new GregorianCalendar();
        return fecha.get(Calendar.YEAR);
    }

    //esto es para que no almacene los items de otras vistas
    @Override
    protected void onPause() {
        super.onPause();
        film = new Film();
    }
}


