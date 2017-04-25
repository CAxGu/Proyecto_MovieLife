package com.herprogramacion.movielife.models;

import com.herprogramacion.movielife.R;

import org.parceler.Parcel;
import org.w3c.dom.DOMConfiguration;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Cines {
    private String telf;
    private String nombre;
    private int idDrawable;
    private String idCINE;
    private float rating;
    private String type;
    private String adress;
    private String webpage;
    private String web;
    private int position;
    private Double longitud;
    private Double latitud;
    private String panoID;

    public Cines() {

    }

    public Cines(int position, Double latitud, Double longitud, String panoID, String telf, String nombre, int idDrawable, String idCINE, float rating, String type, String adress, String web, String webpage) {
        this.telf = telf;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
        this.idCINE = idCINE;
        this.rating = rating;
        this.type = type;
        this.adress = adress;
        this.web = web;
        this.webpage = webpage;
        this.position = position;
        this.longitud = longitud;
        this.latitud = latitud;
        this.panoID = panoID;
    }

    public static final ArrayList<Cines> CINE = new ArrayList<Cines>();

    static {
        CINE.add(new Cines(0, 38.824098, -0.6030605, "BV4ueBAL3NcAAAGusnLqjg", "615975632", "Cineapolis El Teler", R.drawable.cine_cinesa_teler, "CINE_1", 3.8f, "Cine", "Centro Comercial El Teler, Carrer del Pintor Josep Segrelles, 1, 46870 Onteniente, Valencia", "cineapolis.es", "https://www.google.es/url?sa=t&rct=j&url=%2Furl%3Fsa%3Dt%26rct%3Dj%26url%3Dhttp%253A%252F%252Fcineapolis.es%252Fcine.php%253Fid%253D61%26source%3Dmaps%26cd%3D1%26usg%3DAFQjCNF8k9oQGSUe41zCh7M993AknajKoA%26sig2%3DWQjXVDsFmDHeEsVCiTcORg%26ved%3D1t%253A3443%252Cp%253AEHf3WNPsOI6uUv_oiPgO&source=maps&cd=1&usg=AFQjCNF8k9oQGSUe41zCh7M993AknajKoA&sig2=WQjXVDsFmDHeEsVCiTcORg&ved=1t%3A3443%2Cp%3AEHf3WNPsOI6uUv_oiPgO"));
        CINE.add(new Cines(1, 39.003865, -0.526597, "QVB2hEw_k6RFNpmo3KvM3w", "962259189", "Cines Axion Xátiva", R.drawable.cines_axion_plaza_mayor_xativa, "CINE_2", 3.7f, "Cine", "C.C. Plaza Mayor, N-340, s/n, 46800 Xàtiva, Valencia", "cinesaxion.com", "https://www.google.es/url?sa=t&rct=j&url=http%3A%2F%2Fwww.cinesaxion.com%2Fxativa%2F&source=maps&cd=1&usg=AFQjCNE2ydtvbrlvPV7HK8ZY4ComU_HUMg&sig2=MUcVPbpOAKVEaeQEphmPaw&ved=1t%3A3443%2Cp%3AFXf3WKL1CYeAUY-ki9AL"));
        CINE.add(new Cines(2, 38.6976445, -0.4809743, "N00fsYAEB2uLR4u7EQO78w", "965331864", "Cines Axion Alcoy", R.drawable.cine_axion_alcoy, "CINE_3", 3.2f, "Cine", "Centro Comercial Alzamora, Calle Alzamora, 44, 03802 Alcoy, Alicante", "cinesaxion.com", "https://www.google.es/url?sa=t&rct=j&url=http%3A%2F%2Fwww.cinesaxion.com%2F&source=maps&cd=1&usg=AFQjCNGfCLPTi9iAtcpHtfVWQ6ckMmmzGw&sig2=OxRFiXtfoXpLf7C1tUkSlw&ved=1t%3A3443%2Cp%3AE3f3WOSYLIiUUc2yiIgP"));
        CINE.add(new Cines(3, 38.4901265, -0.7788508, "nVJWBiGHD0ETuLI5KFTdlw", "966953070", "CinesMax 3D", R.drawable.cinesmax_petrer, "CINE_4", 4.0f, "Cine", "C.C. Bassa El Moro, Av. el Guirnei, 8-10, 03610 Petrer, Alicante", "cinesmax.es", "https://www.google.es/url?sa=t&rct=j&url=http%3A%2F%2Fwww.cinesmax.es%2F&source=maps&cd=1&usg=AFQjCNFRfh8Lezk4IVLvHIlJJZihRCmlWA&sig2=CIaSnsI5Q3CU1rNFpJHg9g&ved=1t%3A3443%2Cp%3AEnf3WIDzF8mjU6_1k0A"));
        CINE.add(new Cines(4, 38.48753990000001, -0.7736348, "qYC-Tcy3kpNm6KYOdtiJXw", "902220922", "Yelmo Cines Petrer", R.drawable.cine_yelmo_petrer, "CINE_5", 3.9f, "Cine", "Carrefour, Autovia Madrid - Alicante, Km 36.5, 03610 Petrer, Alicante", "yelmocines.es", "https://www.google.es/url?sa=t&rct=j&url=http%3A%2F%2Fwww.yelmocines.es%2F&source=maps&cd=1&usg=AFQjCNE91ryD7q7SrflRCCFTn-Bz6Fz2Kw&sig2=83a0-1_17zqneyWL83vkjg&ved=1t%3A3443%2Cp%3AmnX3WLK3OMG3a9y1v9AC"));
    }


    // GETTERS AND SETTERS

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public String getIdCINE() {
        return idCINE;
    }

    public void setIdCINE(String idCINE) {
        this.idCINE = idCINE;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public double distance(GeoCoordinate other) {
        final double EARTH_RADIUS = 6371000;

        double latDistance = Math.toRadians(this.getLatitud() - other.getLatitude());
        double longDistance = Math.toRadians(this.getLongitud() - other.getLongitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.sin(longDistance / 2) * Math.sin(longDistance / 2)
                * Math.cos(Math.toRadians(this.getLatitud()))
                * Math.cos(Math.toRadians(other.getLatitude()));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return c * EARTH_RADIUS;
    }

    public String getPanoID() {
        return panoID;
    }

    public void setPanoID(String panoID) {
        this.panoID = panoID;
    }

    public static List<Cines> getCine() {
        return CINE;
    }

    public static Cines getCinesByPosition(List<Cines> cines, int position) {
        return cines.get(position);
    }

// TO STRING

    @Override
    public String toString() {
        return "Cines{" +
                "telf='" + telf + '\'' +
                ", nombre='" + nombre + '\'' +
                ", idDrawable=" + idDrawable +
                ", idCINE='" + idCINE + '\'' +
                ", rating=" + rating +
                ", type='" + type + '\'' +
                ", adress='" + adress + '\'' +
                ", webpage='" + webpage + '\'' +
                ", web='" + web + '\'' +
                ", position=" + position +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                '}';
    }
}
