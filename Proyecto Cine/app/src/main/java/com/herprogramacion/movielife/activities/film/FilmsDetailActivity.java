package com.herprogramacion.movielife.activities.film;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.adapters.film.FilmPagerAdapter;
import com.herprogramacion.movielife.models.Film;

import org.parceler.Parcels;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

public class FilmsDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private FilmPagerAdapter adapterViewPager;
    ArrayList<Film> mFilms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films_detail);
        ButterKnife.bind(this);

        mFilms = Parcels.unwrap(getIntent().getParcelableExtra("peliculas_favoritas"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));

        adapterViewPager = new FilmPagerAdapter(getSupportFragmentManager(), mFilms);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}