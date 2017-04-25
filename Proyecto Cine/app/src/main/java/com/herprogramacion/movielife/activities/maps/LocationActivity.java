package com.herprogramacion.movielife.activities.maps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.activities.film.CinesDetailsActivity;
import com.herprogramacion.movielife.models.Cines;
import java.util.List;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private List<Cines> cines = Cines.CINE;

    private final LatLng TELER = new LatLng(Double.valueOf(cines.get(0).getLatitud()), Double.valueOf(cines.get(0).getLongitud()));
    private final LatLng AXION_XATIVA = new LatLng(Double.valueOf(cines.get(1).getLatitud()), Double.valueOf(cines.get(1).getLongitud()));
    private final LatLng AXION_ALCOY = new LatLng(Double.valueOf(cines.get(2).getLatitud()), Double.valueOf(cines.get(2).getLongitud()));
    private final LatLng CINESMAX_PETRER = new LatLng(Double.valueOf(cines.get(3).getLatitud()), Double.valueOf(cines.get(3).getLongitud()));
    private final LatLng YELMO_PETRER = new LatLng(Double.valueOf(cines.get(4).getLatitud()), Double.valueOf(cines.get(4).getLongitud()));

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_location);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Get the map and register for the ready callback
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
            case android.R.id.home:
                // Obtener intent de la actividad padre
                Intent upIntent = NavUtils.getParentActivityIntent(this);

                //Comprobar si ActividadDetallesInicio no se creó desde ActividadPrincipal
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    public void showCVAlenciana(View v) {
        // Wait until map is ready
        if (mMap == null) {
            return;
        }
        // Create bounds that include all locations of the map
        LatLngBounds.Builder boundsBuilder = LatLngBounds.builder()
                .include(TELER)
                .include(AXION_XATIVA)
                .include(AXION_ALCOY)
                .include(CINESMAX_PETRER)
                .include(YELMO_PETRER);
        // Move camera to show all markers and locations
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 25));
    }

    /**
     * Called when the map is ready to add all markers and objects to the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarkers();
        mMap.setOnInfoWindowClickListener(this);

        final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation") // We use the new method when supported
                @SuppressLint("NewApi") // We check which build version we are using.

                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                    showCVAlenciana(null);

                }
            });
        }


    }


    /**
     * Add Markers with default info windows to the map.
     */
    private void addMarkers() {
        for (int pos = 0; pos < cines.size(); pos++) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.valueOf(cines.get(pos).getLatitud()), Double.valueOf(cines.get(pos).getLongitud())))
                    .title(String.valueOf(cines.get(pos).getNombre()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.filmmarker)));
        }

    }

    /**
     * On Info Window Click con enlace a details del cine
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        switch(marker.getTitle()){
            case "Cineapolis El Teler":
//                Intent intent = new Intent(this, CinesDetailsActivity.class);
//                intent.putExtra("position", String.valueOf(cines.get(0).getPosition()));
//                startActivity(intent);
                CinesDetailsActivity.launch(this, Integer.parseInt(String.valueOf(cines.get(0).getPosition())), null,cines);
                break;
            case "Cines Axion Xátiva":
                CinesDetailsActivity.launch(this, Integer.parseInt(String.valueOf(cines.get(1).getPosition())), null,cines);
                break;
            case "Cines Axion Alcoy":
                CinesDetailsActivity.launch(this, Integer.parseInt(String.valueOf(cines.get(2).getPosition())), null,cines);
                break;
            case "CinesMax 3D":
                CinesDetailsActivity.launch(this, Integer.parseInt(String.valueOf(cines.get(3).getPosition())), null,cines);
                break;
            case "Yelmo Cines Petrer":
                CinesDetailsActivity.launch(this, Integer.parseInt(String.valueOf(cines.get(4).getPosition())), null,cines);
                break;
            default:
               Intent intent = new Intent(this, CinesDetailsActivity.class);
                startActivity(intent);
        }

    }

}
