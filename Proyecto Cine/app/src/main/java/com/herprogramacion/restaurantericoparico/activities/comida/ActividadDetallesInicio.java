package com.herprogramacion.restaurantericoparico.activities.comida;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.herprogramacion.restaurantericoparico.R;
import com.herprogramacion.restaurantericoparico.models.Comida;

import java.util.List;

public class ActividadDetallesInicio extends AppCompatActivity {
    private static final String EXTRA_POSITION = "com.herprogramacion.cursospoint.extra.POSITION";
    private static List <Comida> itemss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_detalles_inicio);

        setToolbar();

        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        setupViews(itemss,position);
    }

    private void setupViews(List <Comida> items,int position) {
        TextView nombre = (TextView) findViewById(R.id.plato_name);
        TextView precio = (TextView) findViewById(R.id.precio_comida);
        ImageView imagen = (ImageView) findViewById(R.id.imageView);
        RatingBar rating = (RatingBar) findViewById(R.id.detail_rating);

        Comida detallesComida = Comida.getComidaByPosition(items,position);
        setTitle(detallesComida.getNombre());
        nombre.setText(detallesComida.getNombre());
        precio.setText("$" + detallesComida.getPrecio());
        rating.setRating(detallesComida.getRating());
        Glide.with(this).load(detallesComida.getIdDrawable()).into(imagen);
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

    public  static void launch(Activity context, int position,View sharedImage,List<Comida> items){
        Intent intent = new Intent(context,ActividadDetallesInicio.class);
        intent.putExtra(EXTRA_POSITION,position);
        itemss=items;
        context.startActivity(intent);
    }
}
