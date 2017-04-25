package com.herprogramacion.movielife.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.herprogramacion.movielife.R;
import com.herprogramacion.movielife.activities.maps.StreetViewActivity;
import com.herprogramacion.movielife.activities.maps.LocationActivity;
import com.herprogramacion.movielife.models.Cines;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentSavedCines extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    @Bind(R.id.BOTON_SAVE)
    LinearLayout mSave;
    @Bind(R.id.BOTON_streetview)
    LinearLayout mStreet;
    @Bind(R.id.gpsbutton)
    FloatingActionButton mGpsbutton;
    @Bind(R.id.imageViewCine)
    ImageView mImagen;
    @Bind(R.id.nombre_cine)
    TextView mNombre;
    @Bind(R.id.ratingbar_cine)
    RatingBar mRating;
    @Bind(R.id.type_cine)
    TextView mType;
    @Bind(R.id.save_text)
    TextView mSave_text;
    @Bind(R.id.icono_save)
    ImageView mSaveimg;
    @Bind(R.id.streetview_text)
    TextView mStreettext;
    @Bind(R.id.icono_streetview)
    ImageView nStreetimg;
    @Bind(R.id.adress)
    TextView mAdress;
    @Bind(R.id.web)
    TextView mWeblink;
    @Bind(R.id.telf)
    TextView mTelfnumber;


    private Cines mCines = new Cines();

    public static FragmentSavedCines newInstance(Cines cines) {
        FragmentSavedCines fragmentSavedCines = new FragmentSavedCines();
        Bundle args = new Bundle();
        args.putParcelable("cines_favoritos", Parcels.wrap(cines));
        fragmentSavedCines.setArguments(args);
        return fragmentSavedCines;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCines = Parcels.unwrap(getArguments().getParcelable("cines_favoritos"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cines_detail_maps, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(mCines.getIdDrawable())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImagen);
        Picasso.with(view.getContext())
                .load(R.drawable.star)
                .centerCrop()
                .into(mSaveimg);
        Picasso.with(view.getContext())
                .load(R.drawable.street)
                .centerCrop()
                .into(nStreetimg);
        mNombre.setText(mCines.getNombre());
        mRating.setRating(mCines.getRating());
        mType.setText(mCines.getType());
        mSave_text.setText(R.string.save_label);
        mStreettext.setText("StreetView");
        mAdress.setText(mCines.getAdress());
        mWeblink.setText(mCines.getWeb());
        mTelfnumber.setText(mCines.getTelf());

        mSave.setOnClickListener(this);
        mStreet.setOnClickListener(this);
        mGpsbutton.setOnClickListener(this);
        mAdress.setOnClickListener(this);
        mWeblink.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v == mGpsbutton) {
            mGpsbutton.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_out));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("google.navigation:q=" + Double.valueOf(mCines.getLatitud()) + "," + Double.valueOf(mCines.getLongitud())));
            getActivity().startActivity(intent);

        }
        if (v == mSave) {
            DatabaseReference CinesRef = FirebaseDatabase.getInstance().getReference().child("cines_favoritos");
            String CinesID = CinesRef.push().getKey();
            CinesRef.child(CinesID.replace(CinesID, mCines.getIdCINE())).setValue(mCines);
            Snackbar.make(v, R.string.agregado_favoritos_cines, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        if (v == mStreet) {

            Intent intent = new Intent(getActivity(), StreetViewActivity.class);
            intent.putExtra("panoId", String.valueOf(mCines.getPanoID()));
            intent.putExtra("name", String.valueOf(mCines.getNombre()));
            getActivity().startActivity(intent);

            Snackbar.make(v, "StreetView en Marcha!!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        if (v == mAdress) {

            Intent intent = new Intent(getActivity(), LocationActivity.class);
            getActivity().startActivity(intent);

        }

        if (v == mWeblink) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(mCines.getWebpage())));
            startActivity(webIntent);
        }

        if (v == mTelfnumber) {

            String telf = String.valueOf(mCines.getTelf());
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(telf));
            startActivity(intent);
        }

    }

}
