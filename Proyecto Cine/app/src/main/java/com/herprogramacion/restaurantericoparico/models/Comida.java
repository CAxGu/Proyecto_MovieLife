package com.herprogramacion.restaurantericoparico.models;

import com.herprogramacion.restaurantericoparico.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Comida {
    private float precio;
    private String nombre;
    private int idDrawable;
    private int position;
    private float rating;

    public Comida(float precio, String nombre, int idDrawable,int position,float rating) {
        this.precio = precio;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        this.position = position;
        this.rating = rating;

    }

    public static final List<Comida> COMIDAS_POPULARES = new ArrayList<Comida>();
    public static final List<Comida> BEBIDAS = new ArrayList<>();
    public static final List<Comida> POSTRES = new ArrayList<>();
    public static final List<Comida> PLATILLOS = new ArrayList<>();


    static {
        COMIDAS_POPULARES.add(new Comida(5, "Camarones Tismados", R.drawable.camarones,0,3f));
        COMIDAS_POPULARES.add(new Comida(3.2f, "Rosca Herbárea", R.drawable.rosca,1,3f));
        COMIDAS_POPULARES.add(new Comida(12f, "Sushi Extremo", R.drawable.sushi,2,3f));
        COMIDAS_POPULARES.add(new Comida(9, "Sandwich Deli", R.drawable.sandwich,3,3f));
        COMIDAS_POPULARES.add(new Comida(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo,4,3f));

        PLATILLOS.add(new Comida(5, "Camarones Tismados", R.drawable.camarones,5,3f));
        PLATILLOS.add(new Comida(3.2f, "Rosca Herbárea", R.drawable.rosca,6,3f));
        PLATILLOS.add(new Comida(12f, "Sushi Extremo", R.drawable.sushi,7,3f));
        PLATILLOS.add(new Comida(9, "Sandwich Deli", R.drawable.sandwich,8,3f));
        PLATILLOS.add(new Comida(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo,9,3f));

        BEBIDAS.add(new Comida(3, "Taza de Café", R.drawable.cafe,10,3f));
        BEBIDAS.add(new Comida(12, "Coctel Tronchatoro", R.drawable.coctel,11,3f));
        BEBIDAS.add(new Comida(5, "Jugo Natural", R.drawable.jugo_natural,12,3f));
        BEBIDAS.add(new Comida(24, "Coctel Jordano", R.drawable.coctel_jordano,13,3f));
        BEBIDAS.add(new Comida(30, "Botella Vino Tinto Darius", R.drawable.vino_tinto,14,3f));

        POSTRES.add(new Comida(2, "Postre De Vainilla", R.drawable.postre_vainilla,15,3f));
        POSTRES.add(new Comida(3, "Flan Celestial", R.drawable.flan_celestial,16,3f));
        POSTRES.add(new Comida(2.5f, "Cupcake Festival", R.drawable.cupcakes_festival,17,3f));
        POSTRES.add(new Comida(4, "Pastel De Fresa", R.drawable.pastel_fresa,18,3f));
        POSTRES.add(new Comida(5, "Muffin Amoroso", R.drawable.muffin_amoroso,19,3f));
    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public int getPosition(){return position;}

    public float getRating(){return rating;}

    public static  List<Comida> getComidasPopulares(){
        return COMIDAS_POPULARES;
    }

    public static  List<Comida> getBebidas(){
        return BEBIDAS;
    }

    public static  List<Comida> getPlatillos(){
        return PLATILLOS;
    }

    public static  List<Comida> getPostres(){
        return POSTRES;
    }

    public static Comida getComidaByPosition(List<Comida> items,int position) {

        if(items==COMIDAS_POPULARES){
            return items.get(position);
        }else if (items==PLATILLOS) {
            return items.get(position);
        }else if (items == BEBIDAS){
            return items.get(position);
        }else  {
            return POSTRES.get(position);
        }

    }
}
