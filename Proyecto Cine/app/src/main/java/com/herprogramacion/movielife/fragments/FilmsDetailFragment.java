package com.herprogramacion.movielife.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.models.Film;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FilmsDetailFragment extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    @Bind(R.id.imageView)
    ImageView mImageLabel;
    @Bind(R.id.title)
    TextView mNameLabel;
    @Bind(R.id.year)
    TextView mYearLabel;
    @Bind(R.id.ratingbar)
    RatingBar mRatingLabel;
    @Bind(R.id.websiteTextView)
    TextView mWebsiteLabel;
    @Bind(R.id.type)
    TextView mTypeLabel;
    @Bind(R.id.tag_description)
    TextView mDescription;
    @Bind(R.id.plot)
    TextView mArgumentsLabel;
    @Bind(R.id.lang_label)
    TextView mLanguageLabel;
    @Bind(R.id.lang)
    TextView mLanguage;
    @Bind(R.id.director_label)
    TextView mDirectorLabel;
    @Bind(R.id.director)
    TextView mDirector;
    @Bind(R.id.writer_label)
    TextView mWriterLabel;
    @Bind(R.id.writer)
    TextView mWriter;
    @Bind(R.id.actors_label)
    TextView mActorsLabel;
    @Bind(R.id.actors)
    TextView mActors;
    @Bind(R.id.genre)
    TextView mGenreLabel;
    @Bind(R.id.fab)
    FloatingActionButton mSaveFilmsButton;

    private Film mfilms = new Film();

    public static FilmsDetailFragment newInstance(Film films) {
        FilmsDetailFragment filmsDetailFragment = new FilmsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("peliculas_favoritas", Parcels.wrap(films));
        filmsDetailFragment.setArguments(args);
        return filmsDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mfilms = Parcels.unwrap(getArguments().getParcelable("peliculas_favoritas"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(mfilms.getPoster())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);
        mNameLabel.setText(mfilms.getTitle());
        mYearLabel.setText(mfilms.getYear());
        mRatingLabel.setRating((float) mfilms.getRated() * 2);
        mWebsiteLabel.setOnClickListener(this);
        mTypeLabel.setText(mfilms.getType());
        mDescription.setText(R.string.descripcion_label);
        mArgumentsLabel.setText(mfilms.getPlot());
        mLanguage.setText(mfilms.getLanguage());
        mLanguageLabel.setText(R.string.language_label);
        mDirector.setText(mfilms.getDirector());
        mDirectorLabel.setText(R.string.director_label);
        mWriter.setText(mfilms.getWriter());
        mWriterLabel.setText(R.string.writer_label);
        mActors.setText(mfilms.getActors());
        mActorsLabel.setText(R.string.actors_label);
        mGenreLabel.setText(mfilms.getGenre());
        mSaveFilmsButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mSaveFilmsButton) {
            DatabaseReference FilmsRef = FirebaseDatabase.getInstance().getReference().child("peliculas_favoritas");
            String mFilmsID = FilmsRef.push().getKey();
            FilmsRef.child(mFilmsID.replace(mFilmsID, String.valueOf(mfilms.getImdbID()))).setValue(mfilms);
            Toast.makeText(getContext(), R.string.agregado_favoritos, Toast.LENGTH_SHORT).show();


        }
        if (v == mWebsiteLabel) {
            try {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mfilms.getWeb()));
                startActivity(webIntent);
            } catch (Exception web) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://duckduckgo.com/?q=" + mfilms.getTitle()));
                startActivity(webIntent);
            }
        }

    }
}