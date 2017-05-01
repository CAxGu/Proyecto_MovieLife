package com.herprogramacion.movielife.models;

import com.herprogramacion.movielife.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/05/17.
 */

public class Pelis {
    private int anyo;
    private String nombre;
    private int idDrawable;
    private int position;
    private float rating;
    private String type;

    public Pelis(int anyo, String nombre, int idDrawable, int position, float rating,String type ) {
        this.anyo = anyo;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        this.position = position;
        this.rating = rating;
        this.type=type;

    }

    public static final List<Pelis> ESTRENOS = new ArrayList<Pelis>();
    public static final List<Pelis> PELICULAS = new ArrayList<>();
    public static final List<Pelis> SERIES = new ArrayList<>();


    static {
        ESTRENOS.add(new Pelis(2017, "Thor Ragnarok", R.drawable.thor_ragnarok, 0, 3f,"pelicula"));
        ESTRENOS.add(new Pelis(2017, "Kingsman", R.drawable.kingsman, 1, 3f,"pelicula"));
        ESTRENOS.add(new Pelis(2017, "Wonder Woman", R.drawable.ww, 2, 3f,"pelicula"));
        ESTRENOS.add(new Pelis(2017, "Plan de Fuga", R.drawable.plandefuga, 3, 3f,"pelicula"));
        ESTRENOS.add(new Pelis(2017, "Guerra Planeta de los Simios", R.drawable.planeta, 4, 3f,"pelicula"));
        ESTRENOS.add(new Pelis(2017, "Liga de la Justicia", R.drawable.liga, 5, 3f,"pelicula"));

        PELICULAS.add(new Pelis(2017, "Thor Ragnarok", R.drawable.thor_ragnarok, 6, 3f,"pelicula"));
        PELICULAS.add(new Pelis(2017, "Kingsman", R.drawable.kingsman, 7, 3f,"pelicula"));
        PELICULAS.add(new Pelis(2017, "Wonder Woman", R.drawable.ww, 8, 3f,"pelicula"));
        PELICULAS.add(new Pelis(2017, "Plan de Fuga", R.drawable.plandefuga, 9, 3f,"pelicula"));
        PELICULAS.add(new Pelis(2017, "Guerra Planeta de los Simios", R.drawable.planeta, 10, 3f,"pelicula"));
        PELICULAS.add(new Pelis(2017, "Liga de la Justicia", R.drawable.liga, 11, 3f,"pelicula"));

        SERIES.add(new Pelis(2014, "Arrow", R.drawable.arrow, 12, 3f,"serie"));
        SERIES.add(new Pelis(2012, "La Hora de J.Mota", R.drawable.hora_mota, 13 ,3f,"serie"));
        SERIES.add(new Pelis(2015, "The FLASH", R.drawable.flash, 14, 3f,"serie"));
        SERIES.add(new Pelis(2011, "Juego de Tronos", R.drawable.juegotronos, 15, 3f,"serie"));
        SERIES.add(new Pelis(2012, "Los 100", R.drawable.los100, 16, 3f,"serie"));
        SERIES.add(new Pelis(2014, "The Magicians", R.drawable.magicians, 17, 3f,"serie"));

    }

    public int getAnyo() {
        return anyo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public int getPosition() {
        return position;
    }

    public float getRating() {
        return rating;
    }

    public String getType() {
        return type;
    }

    public static List<Pelis> getEstrenos() {
        return ESTRENOS;
    }

    public static List<Pelis> getPeliculas() {
        return PELICULAS;
    }

    public static List<Pelis> getSeries() {
        return SERIES;
    }

    public static Pelis getESTRENOSByPosition(List<Pelis> items, int position) {

        if (items == ESTRENOS) {
            return items.get(position);
        } else if (items == PELICULAS) {
            return items.get(position);
        } else {
            return SERIES.get(position);
        }

    }
}
