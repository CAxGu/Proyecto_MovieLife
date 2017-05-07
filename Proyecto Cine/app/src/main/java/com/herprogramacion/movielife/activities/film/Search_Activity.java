package com.herprogramacion.movielife.activities.film;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.adapters.film.SearchAdapter;
import com.herprogramacion.movielife.fragments.FragmentSearch;
import com.herprogramacion.movielife.models.Film;
import com.herprogramacion.movielife.net.DBAdapter;
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
    private DBAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new DBAdapter(this);
        mDbHelper.open();
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
                    mDbHelper.createTodo_words(query);
                    searchView.setIconified(true);
                    searchItem.collapseActionView();
                    Search_Activity.this.setTitle(query);
                    return true;
                }else
                    return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {

                try {

                    if(s.length() > 0) {
                        Cursor hola = mDbHelper.fetchAllTodos_word(s);
                        //Cursor Adaptor
                        String[] columnNames = {"_id", "word", "datetime"};
                        MatrixCursor cursor = new MatrixCursor(columnNames);
                        String[] temp = new String[3];
                        int id = 0;
                        while (hola.moveToNext()){
                            temp[0]=Integer.toString(id++);
                            temp[1]=hola.getString(0);
                            temp[2]= hola.getString(1);
                            cursor.addRow(temp);
                        }

                        final CursorAdapter cursorAdapter = new CursorAdapter(getApplicationContext(), cursor, false) {
                            final List<String> del = new ArrayList<String>();

                            @Override
                            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                                return LayoutInflater.from(context).inflate(R.layout.search_suggestion_list_item, parent, false);
                            }

                            @Override
                            public void bindView(View view, final Context context, final Cursor cursor) {
                                final TextView suggest = (TextView) view.findViewById(R.id.suggest);
                                final TextView num = (TextView) view.findViewById(R.id.number);
                                ImageView putInSearchBox = (ImageView) view.findViewById(R.id.put_in_search_box);
                                String body = cursor.getString(cursor.getColumnIndexOrThrow("word"));
                                final String number = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                                num.setText(number);
                                suggest.setText(body);
                                suggest.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        searchView.setQuery(suggest.getText(), true);
                                        searchView.clearFocus();
                                    }
                                });
                                putInSearchBox.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.e("sd",num.getText().toString());
                                        mDbHelper.deleteTodo_word(suggest.getText().toString());
                                        Toast.makeText(context,"Borrado correctamente",Toast.LENGTH_SHORT).show();
                                        refresh_words(searchView,s);
                                    }
                                });
                            }
                        };
                        searchView.setSuggestionsAdapter(cursorAdapter);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
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

    public void refresh_words(final SearchView searchView, final String s){//no se puede hacer statico para que accedan desde otra activity
        Cursor hola = mDbHelper.fetchAllTodos_word(s);
        //Cursor Adaptor
        String[] columnNames = {"_id", "word", "datetime"};
        MatrixCursor cursor = new MatrixCursor(columnNames);
        String[] temp = new String[3];
        int id = 0;
        while (hola.moveToNext()){
            temp[0]=Integer.toString(id++);
            temp[1]=hola.getString(0);
            temp[2]= hola.getString(1);
            cursor.addRow(temp);
        }

        final CursorAdapter cursorAdapter = new CursorAdapter(getApplicationContext(), cursor, false) {
            final List<String> del = new ArrayList<String>();

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.search_suggestion_list_item, parent, false);
            }

            @Override
            public void bindView(View view, final Context context, final Cursor cursor) {
                final TextView suggest = (TextView) view.findViewById(R.id.suggest);
                final TextView num = (TextView) view.findViewById(R.id.number);
                ImageView putInSearchBox = (ImageView) view.findViewById(R.id.put_in_search_box);
                String body = cursor.getString(cursor.getColumnIndexOrThrow("word"));
                final String number = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                num.setText(number);
                suggest.setText(body);
                suggest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchView.setQuery(suggest.getText(), true);
                        searchView.clearFocus();
                    }
                });
                putInSearchBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("sd",num.getText().toString());
                        mDbHelper.deleteTodo_word(suggest.getText().toString());
                        Toast.makeText(context,"Borrado correctamente",Toast.LENGTH_SHORT).show();
                        refresh_words(searchView,s);
                    }
                });
            }
        };
        searchView.setSuggestionsAdapter(cursorAdapter);
    }


}
