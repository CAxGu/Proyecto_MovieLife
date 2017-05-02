package com.herprogramacion.movielife.activities.film;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.adapters.film.SearchAdapter;
import com.herprogramacion.movielife.fragments.FragmentSearch;
import com.herprogramacion.movielife.models.Film;
import com.herprogramacion.movielife.net.PelisClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.herprogramacion.movielife.activities.film.ActividadPrincipal.search;


public class Search_Activity extends AppCompatActivity {
    public static final String BOOK_DETAIL_KEY = "book";
    private ListView lvBooks;
    private SearchAdapter bookAdapter;
    private static PelisClient client;
    public static final List<Film> boocs = new ArrayList<>();
    private static ProgressBar progress;
    private static int pag;
    private SharedPreferences preferences;
    private double maximunDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        progress = (ProgressBar) findViewById(R.id.progress);
//        Log.v("Search",search);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        maximunDistance = (double) preferences.getInt("prf_seekbar", 10);


        if(search==null)
            search = "empty";
        setTitle(search);
        fetchBooks(search);
        setToolbar();
        Fragment fragmentoGenerico = null;
        fragmentoGenerico = new FragmentSearch();
        if (fragmentoGenerico != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor_peliculas, fragmentoGenerico)
                    .commit();
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //  searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                search=query;
                if(query != null) {
                    fetchBooks(query);
                    // Reset SearchView
                    searchView.clearFocus();
                    searchView.setQuery("", false);
                    searchView.setIconified(true);
                    searchItem.collapseActionView();
                    Search_Activity.this.setTitle(query);
                    return true;
                }else
                    return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)// Habilitar Up Button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void fetchBooks(final String query) {
        // Show progress bar before making network request
        progress.setVisibility(ProgressBar.VISIBLE);
        client = new PelisClient();
        client.getBooks(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // hide progress bar
                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if (response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("Search");
                        // Parse json array into array of model objects
                        final ArrayList<Film> films = Film.fromJson(docs);
                        boocs.clear();
                        // Load model objects into the adapter
                        for (Film film : films) {
                            boocs.add(film);
                        }

                        for (int i = 1;i<maximunDistance;i++){
                            fetchBooks_10more(query);
                        }

                        Fragment fragmentoGenerico = null;
                        fragmentoGenerico = new FragmentSearch();

                        if (fragmentoGenerico != null) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.contenedor_peliculas, fragmentoGenerico)
                                    .commit();
                        }
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.setVisibility(ProgressBar.GONE);
            }
        });

    }

    public static void fetchBooks_10more(String query) {
        // Show progress bar before making network request
        progress.setVisibility(ProgressBar.VISIBLE);
        client = new PelisClient();
        pag += 1 ; //Ba pasando de pagina
        client.getMoreBooks(query,pag, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // hide progress bar
                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if (response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("Search");
                        // Parse json array into array of model objects
                        final ArrayList<Film> films = Film.fromJson(docs);
                        //boocs.clear(); NO queremos eliminar los anteriores
                        // Load model objects into the adapter
                        for (Film film : films) {
                            boocs.add(film);
                        }
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.setVisibility(ProgressBar.GONE);
            }
        });

    }



}
