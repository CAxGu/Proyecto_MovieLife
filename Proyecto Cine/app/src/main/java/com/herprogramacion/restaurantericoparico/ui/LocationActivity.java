package com.herprogramacion.restaurantericoparico.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.herprogramacion.restaurantericoparico.R;

/**
 * This demo shows some features supported in lite mode.
 * In particular it demonstrates the use of {@link com.google.android.gms.maps.model.Marker}s to
 * launch the Google Maps Mobile application, {@link com.google.android.gms.maps.CameraUpdate}s
 * and {@link com.google.android.gms.maps.model.Polygon}s.
 */
public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final LatLng ONTINYENT = new LatLng(38.8220593, -0.6063927);
    private static final LatLng ALCOY = new LatLng(38.6987066, -0.4810937);
    private static final LatLng BANYERES = new LatLng(38.7153272, -0.6595183999999999);
    private static final LatLng FONTANARS = new LatLng(38.7828259, -0.7852154);

    /**
     * A Polygon with five points in the Norther Territory, Australia.
     */
//    private static final LatLng[] POLYGON = new LatLng[]{
//            new LatLng(-18.000328, 130.473633), new LatLng(-16.173880, 135.087891),
//            new LatLng(-19.663970, 137.724609), new LatLng(-23.202307, 135.395508),
//            new LatLng(-19.705347, 129.550781)};

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        setToolbar();

        // Get the map and register for the ready callback
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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

    /**
     * Move the camera to center on Ontinyent.
     */
    public void showOntinyent(View v) {
        // Wait until map is ready
        if (mMap == null) {
            return;
        }
        // Center camera on Ontinyent marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ONTINYENT, 10f));
    }

    /**
     * Move the camera to center on Fontanars.
     */
    public void showFontanars(View v) {
        // Wait until map is ready
        if (mMap == null) {
            return;
        }
        // Center camera on Fontanars marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(FONTANARS, 10f));
    }

    /**
     * Move the camera to show all of c.valenciana.
     * Construct a {@link com.google.android.gms.maps.model.LatLngBounds} from markers positions,
     * then move the camera.
     */
    public void showCVAlenciana(View v) {
        // Wait until map is ready
        if (mMap == null) {
            return;
        }
        // Create bounds that include all locations of the map
        LatLngBounds.Builder boundsBuilder = LatLngBounds.builder()
                .include(ONTINYENT)
                .include(ALCOY)
                .include(BANYERES)
                .include(FONTANARS);
        // Move camera to show all markers and locations
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 20));
    }

    /**
     * Called when the map is ready to add all markers and objects to the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarkers();
        // addPolyobjects();

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
     * Add a Polyline and a Polygon to the map.
     * The Polyline connects Melbourne, Adelaide and Perth. The Polygon is located in the Northern
     * Territory (Australia).
     */
//    private void addPolyobjects() {
//        mMap.addPolyline((new PolylineOptions())
//                .add(MELBOURNE, ADELAIDE, PERTH)
//                .color(Color.GREEN)
//                .width(5f));
//
//        mMap.addPolygon(new PolygonOptions()
//                .add(POLYGON)
//                .fillColor(Color.CYAN)
//                .strokeColor(Color.BLUE)
//                .strokeWidth(5));
//    }

    /**
     * Add Markers with default info windows to the map.
     */
    private void addMarkers() {
        mMap.addMarker(new MarkerOptions()
                .position(ONTINYENT)
                .title("Ontinyent"));

        mMap.addMarker(new MarkerOptions()
                .position(ALCOY)
                .title("Alcoy")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        mMap.addMarker(new MarkerOptions()
                .position(BANYERES)
                .title("Bañeres")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.addMarker(new MarkerOptions()
                .position(FONTANARS)
                .title("Fontanars dels Alforins")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

    }
}
