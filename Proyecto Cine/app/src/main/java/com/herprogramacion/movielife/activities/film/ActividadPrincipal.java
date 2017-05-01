package com.herprogramacion.movielife.activities.film;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.activities.database.firebase.FireBaseActivity;
import com.herprogramacion.movielife.activities.database.sqlite.TareasSQLiteActivity;
import com.herprogramacion.movielife.activities.maps.LocationActivity;
import com.herprogramacion.movielife.fragments.FragmentCines;
import com.herprogramacion.movielife.fragments.FragmentoMisFavoritos;
import com.herprogramacion.movielife.fragments.FragmentoPeliSeries;
import com.herprogramacion.movielife.fragments.FragmentoPerfil;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Locale;

public class ActividadPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    public static String search;
    private FirebaseUser user;
    private TextView email_drawer;
    private TextView name_drawer;
    private ImageView photouser_drawer;
    private String name;
    private String email;
    private MenuItem menuSing;
    private MenuItem menuCount;
    private static final int RC_SIGN_IN=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            actionBar.setTitle(getString(R.string.app_name));
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        user = FirebaseAuth.getInstance().getCurrentUser();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
        View header = navigationView.getHeaderView(0);
        name_drawer = (TextView) header.findViewById(R.id.nameuser_drawer);
        email_drawer = (TextView) header.findViewById(R.id.mailuser_drawer);
        photouser_drawer = (ImageView) header.findViewById(R.id.icono_user);
        if (user != null) {
            if (user.getProviders().toString().equals("[google.com]")) {
                name = user.getEmail();
            } else {
                name = user.getDisplayName();
            }
            if (user.getProviders().toString().equals("[password]"))
                email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            if (photoUrl != null) {
                Picasso.with(this).load(photoUrl).into(photouser_drawer);
            }

            name_drawer.setText(name);
            email_drawer.setText(email);
            //Hace invisible menu Registrarse y visible Mi cuenta
            Menu menu = navigationView.getMenu();

            menuSing = menu.findItem(R.id.item_registrarse);
            menuCount= menu.findItem(R.id.item_cuenta);

            menuSing.setVisible(false);
            menuCount.setVisible(true);
        }

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(2));
        }

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_registrarse:
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                                .setTheme(R.style.DarkTheme)
                                .setLogo(R.drawable.movielife)
                                .build(),
                        RC_SIGN_IN);
                break;
            case R.id.item_estrenos:
                fragmentoGenerico = new FragmentoPeliSeries();
                break;
            case R.id.item_cuenta:
                fragmentoGenerico = new FragmentoPerfil();
                break;
            case R.id.item_mas_info:
                fragmentoGenerico = new FragmentoMisFavoritos();
                break;
            case R.id.item_mis_favoritos:
                startActivity(new Intent(this, SavedFilmsActivity.class));
                break;
            case R.id.item_mis_favoritos_cines:
                startActivity(new Intent(this, SavedCinesActivity.class));
                break;
            case R.id.item_configuracion:
                startActivity(new Intent(this, ActividadConfiguracion.class));
                break;
            case R.id.item_locate:
                fragmentoGenerico = new FragmentCines();
                break;
            case R.id.item_peliculas:
                startActivity(new Intent(this, Search_Activity.class));
                break;
            case R.id.item_crud:
                startActivity(new Intent(this, TareasSQLiteActivity.class));
                break;
            case R.id.item_crud_firebase:
                startActivity(new Intent(this, FireBaseActivity.class));
                break;
            case R.id.item_google_maps:
                startActivity(new Intent(this, LocationActivity.class));
                break;
            case R.id.en:
                change_lang("en");
                break;
            case R.id.es:
                change_lang("es");
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }
    //On Click salir menu Logout
    public void singout(MenuItem Item) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(ActividadPrincipal.this, ActividadPrincipal.class));//

                    }
                });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        //menu logout
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null)
                getMenuInflater().inflate(R.menu.menu_actividad_principal_auth, menu);
            else
                getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        } catch (Exception e) {
            getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        }
        //fin menu
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //  searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                if (query != null) {
                    search = query;
                    // Reset SearchView
                    searchView.clearFocus();
                    searchView.setQuery("", false);
                    searchView.setIconified(true);
                    searchItem.collapseActionView();
                    startactividad();
                    return true;
                } else
                    return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    public void startactividad() {
        startActivity(new Intent(this, Search_Activity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                Log.d("Auth","Sign in");
                finish();
                startActivity(new Intent(this, ActividadPrincipal.class));

            }else{
                Log.d("Auth","Sing out");
            }
        }
    }

    private void change_lang(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        //String lang_default = Locale.getDefault().getLanguage();
        //Toast toast1 = Toast.makeText(getApplicationContext(), lang_default, Toast.LENGTH_SHORT);
        //toast1.show();

        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
        //getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

       finish();
       Intent refresh = new Intent(this, ActividadPrincipal.class);
       startActivity(refresh);
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        change_lang(language);
    }


    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    }
