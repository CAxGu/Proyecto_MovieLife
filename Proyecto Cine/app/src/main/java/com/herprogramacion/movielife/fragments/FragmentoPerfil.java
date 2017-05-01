package com.herprogramacion.movielife.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.herprogramacion.movielife.R;
import com.squareup.picasso.Picasso;

/**
 * Fragmento para la pestaña "PERFIL" De la sección "Mi Cuenta"
 */
public class FragmentoPerfil extends Fragment {

    private ImageView image_user;
    private TextView name_user;
    private ImageView image_email;
    private TextView name_email;
    private String email;
    private String name;
    private FirebaseUser user;
    private CardView card_email;
    private RelativeLayout change_pwd;
    private TextView pwd_label;
    private CardView pwd_rest_card;

    public FragmentoPerfil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_perfil, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image_user = (ImageView) view.findViewById(R.id.imageView_user);
        name_user = (TextView) view.findViewById(R.id.texto_nombre);
        image_email = (ImageView) view.findViewById(R.id.imageView_user);
        name_email = (TextView) view.findViewById(R.id.texto_email);
        card_email = (CardView) view.findViewById(R.id.cardview_email);
        change_pwd = (RelativeLayout) view.findViewById(R.id.pwd);
        pwd_label = (TextView)view.findViewById(R.id.reset_pwd);
        pwd_rest_card = (CardView)view.findViewById(R.id.rest_pwd);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getProviders().toString().equals("[google.com]")) {
            name = user.getEmail();
        } else {
            name = user.getDisplayName();
        }
        if (user.getProviders().toString().equals("[password]")) {
            email = user.getEmail();
            card_email.setVisibility(View.VISIBLE);
            pwd_label.setVisibility(View.VISIBLE);
            pwd_rest_card.setVisibility(View.VISIBLE);
        }
        Uri photoUrl = user.getPhotoUrl();
        if (photoUrl != null) {
            Picasso.with(getContext()).load(photoUrl).into(image_user);
        }
        name_user.setText(name);
        name_email.setText(email);

        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task<Void> task = FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getContext(),"SEND SUCCESS",Toast.LENGTH_SHORT).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"SEND FAILED",Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
    }

}
