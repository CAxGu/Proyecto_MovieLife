package com.herprogramacion.movielife.activities.film;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.activities.maps.LocationActivity;
import com.herprogramacion.movielife.activities.maps.StreetViewActivity;
import com.herprogramacion.movielife.models.Cines;
import com.herprogramacion.movielife.models.Film;
import com.herprogramacion.movielife.net.FirebaseReferences;

import java.util.List;


public class CinesDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_POSITION = "";

    private static List<Cines> cinesS;
    private static Cines cine;
    private LinearLayout save;
    private LinearLayout street;
    private FloatingActionButton gpsbutton;
    private ImageView imagen;
    private TextView nombre;
    private RatingBar rating;
    private TextView type;
    private TextView savetext;
    private ImageView saveimg;
    private TextView streettext;
    private ImageView streetimg;
    private TextView adress;
    private TextView weblink;
    private TextView telfnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cines_detail_maps);
        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        Log.v("La posicion es:", String.valueOf(position));
        setupViews(cinesS, position);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCineDetails);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

    }

    private void setupViews(List<Cines> cines, int position) {

        save = (LinearLayout) findViewById(R.id.BOTON_SAVE);
        street = (LinearLayout) findViewById(R.id.BOTON_streetview);
        gpsbutton = (FloatingActionButton) findViewById(R.id.gpsbutton);
        imagen = (ImageView) findViewById(R.id.imageViewCine);
        nombre = (TextView) findViewById(R.id.nombre_cine);
        rating = (RatingBar) findViewById(R.id.ratingbar_cine);
        type = (TextView) findViewById(R.id.type_cine);
        savetext = (TextView) findViewById(R.id.save_text);
        saveimg = (ImageView) findViewById(R.id.icono_save);
        streettext = (TextView) findViewById(R.id.streetview_text);
        streetimg = (ImageView) findViewById(R.id.icono_streetview);
        adress = (TextView) findViewById(R.id.adress);
        weblink = (TextView) findViewById(R.id.web);
        telfnumber = (TextView) findViewById(R.id.telf);

        gpsbutton.setOnClickListener(this);
        save.setOnClickListener(this);
        street.setOnClickListener(this);
        adress.setOnClickListener(this);
        weblink.setOnClickListener(this);
        telfnumber.setOnClickListener(this);

        cine = cines.get(position);
        Cines detallesCines = cines.get(position);
        setTitle(detallesCines.getNombre());
        nombre.setText(detallesCines.getNombre());
        rating.setRating(detallesCines.getRating());
        type.setText(R.string.type_label);
        savetext.setText(R.string.save_label);
        streettext.setText("StreetView");
        adress.setText(detallesCines.getAdress());
        weblink.setText(detallesCines.getWeb());
        telfnumber.setText(detallesCines.getTelf());

        Glide.with(this).load(detallesCines.getIdDrawable()).into(imagen);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.CINES_REFERENCE);
        ref.child(cine.getIdCINE()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Film hola = dataSnapshot.getValue(Film.class);
                if (hola != null){
                    saveimg.setImageResource(R.drawable.fabred);
                }else
                    saveimg.setImageResource(R.drawable.fabnegro);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
            case android.R.id.home:
                // Obtener intent de la actividad padre
                Intent upIntent = NavUtils.getParentActivityIntent(this);

                //Comprobar si ActividadDetallesPeliSeries no se creó desde ActividadPrincipal
                if (NavUtils.shouldUpRecreateTask(this, upIntent) || this.isTaskRoot()) {
                    //Construir de nuevo la tarea para ligar ambas actividades
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //Terminar con el método correspondiente para Android 5.x
                    this.supportFinishAfterTransition();
                    return true;
                }
                //Dejar que el sistema maneje el comportamiendo del up button
                return false;
        }
    }

    public void onClick(View v) {

        if (v == gpsbutton) {
            gpsbutton.startAnimation(AnimationUtils.loadAnimation(CinesDetailsActivity.this, R.anim.zoom_out));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("google.navigation:q=" + Double.valueOf(cine.getLatitud()) + "," + Double.valueOf(cine.getLongitud())));
            startActivity(intent);

        }
        if (v == save) {
            DatabaseReference CinesRef = FirebaseDatabase.getInstance().getReference().child("cines_favoritos");
            String CinesID = CinesRef.push().getKey();
            CinesRef.child(CinesID.replace(CinesID, cine.getIdCINE())).setValue(cine);
            Snackbar.make(v, R.string.agregado_favoritos_cines, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        if (v == street) {

            Intent intent = new Intent(CinesDetailsActivity.this, StreetViewActivity.class);
            intent.putExtra("panoId", String.valueOf(cine.getPanoID()));
            intent.putExtra("name", String.valueOf(cine.getNombre()));
            startActivity(intent);

            Snackbar.make(v, "StreetView en Marcha!!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        if (v == adress) {

            Intent intent = new Intent(CinesDetailsActivity.this, LocationActivity.class);
            startActivity(intent);

        }

        if (v == weblink) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(cine.getWebpage())));
            startActivity(webIntent);
        }

        if (v == telfnumber) {

            String telf = String.valueOf(cine.getTelf());
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(telf));
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public static void launch(Activity context, int position, View sharedImage, List<Cines> items) {
        Intent intent = new Intent(context, CinesDetailsActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        cinesS = items;
        cine = items.get(position);
        context.startActivity(intent);
    }

}

