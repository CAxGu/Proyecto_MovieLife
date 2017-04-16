package com.herprogramacion.movielife.activities.film;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.herprogramacion.movielife.R;

public class SplashActivity extends Activity {
    private final int ANIMATION_DURATION = 3000;
    private RelativeLayout relativeLayout;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.splash_activity);

        relativeLayout = (RelativeLayout) findViewById(R.id.main_layout);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, ActividadPrincipal.class));
                SplashActivity.this.finish();
                spinner.setVisibility(View.GONE);
            }
        }, ANIMATION_DURATION);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
