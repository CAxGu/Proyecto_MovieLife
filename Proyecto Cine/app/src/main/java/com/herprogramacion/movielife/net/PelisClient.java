package com.herprogramacion.movielife.net;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PelisClient {
    private static final String API_BASE_URL = "http://www.omdbapi.com/";
    private AsyncHttpClient client;

    public PelisClient() {
        this.client = new AsyncHttpClient();
    }

    public void getMoreBooks(final String query, int pag, JsonHttpResponseHandler handler){
        try {
            String url = getApiUrl("?s=");
            Log.v("OO",url + (query+"&page="+String.valueOf(pag)));
            client.get(url +(query+"&page="+String.valueOf(pag)), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getBooks(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("?s=");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Method for accessing books API to get publisher and no. of pages in a book.
    public void getExtraFilmDetails(String openLibraryId, JsonHttpResponseHandler handler) {
        String url = getApiUrl("?i=");
        client.get(url + openLibraryId +"&plot=full" , handler);
    }
}
