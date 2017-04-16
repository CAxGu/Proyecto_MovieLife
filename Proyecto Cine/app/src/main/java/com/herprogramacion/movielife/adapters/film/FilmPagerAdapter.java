package com.herprogramacion.movielife.adapters.film;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.herprogramacion.movielife.fragments.FilmsDetailFragment;
import com.herprogramacion.movielife.models.Film;

import java.util.ArrayList;

public class FilmPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Film> mFilms;

    public FilmPagerAdapter(FragmentManager fm, ArrayList<Film> films) {
        super(fm);
        mFilms = films;
    }

    @Override
    public Fragment getItem(int position) {
        return FilmsDetailFragment.newInstance(mFilms.get(position));
    }

    @Override
    public int getCount() {
        return mFilms.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFilms.get(position).getTitle();
    }
}