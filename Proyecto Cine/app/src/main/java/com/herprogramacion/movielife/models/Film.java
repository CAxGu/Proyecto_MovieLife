package com.herprogramacion.movielife.models;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Film implements Serializable {
    private String imdbID;
    private String director;
    private String writer;
    private String actors;
    private String title;
    private double rated;
    private String year;
    private String plot;
    private String type;
    private String web;
    private String genre;
    private String language;
    private String Poster;
    private int runtime;//minutos

    //Getters and Setters
    public String getWeb() {
        return web;
    }
    public void setWeb(String web) {
        this.web = web;
    }
    public String getPlot() {
        return plot;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getImdbID() {
        return imdbID;
    }
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }
    public String getActors() {
        return actors;
    }
    public void setActors(String actors) {
        this.actors = actors;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getRated() {
        return rated;
    }
    public void setRated(double rated) {
        this.rated = rated/2;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public int getRuntime() {
        return runtime;
    }
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genere) {
        this.genre = genere;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setPoster(String poster) {
        Poster = poster;
    }
    public String getPoster() {
        return Poster;
    }
    public String getTitle() {
        return title;
    }


    // Returns a Film given the expected JSON
    public static Film fromJson(JSONObject jsonObject) {
        Film film = new Film();
        try {
            // Deserialize json into object fields
            if (jsonObject.has("imdbID")) {
                film.imdbID = jsonObject.getString("imdbID");
            } else if(jsonObject.has("imdbID")) {
                final JSONArray ids = jsonObject.getJSONArray("imdbID");
                film.imdbID = ids.getString(0);
            }
            film.Poster= jsonObject.has("Poster") ? jsonObject.getString("Poster"):"";
            if(film.getPoster().length()==3)//no funciona == "N/A"
                film.setPoster("https://www.materialui.co/materialIcons/image/broken_image_black_192x192.png");
            film.title = jsonObject.has("Title") ? jsonObject.getString("Title") : "";
            film.year = jsonObject.has("Year") ? jsonObject.getString("Year") : "";
            film.type = jsonObject.has("Type") ? jsonObject.getString("Type") : "";
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return film;
    }

    public String getYear() {
        return year;
    }

    // Decodes array of book json results into business model objects
    public static ArrayList<Film> fromJson(JSONArray jsonArray) {
        ArrayList<Film> films = new ArrayList<Film>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Film film = Film.fromJson(bookJson);
            if (film != null) {
                films.add(film);
            }
        }
        return films;
    }
}