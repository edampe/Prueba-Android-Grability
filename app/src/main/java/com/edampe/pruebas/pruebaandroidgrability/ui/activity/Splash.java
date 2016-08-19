package com.edampe.pruebas.pruebaandroidgrability.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.edampe.pruebas.pruebaandroidgrability.R;


/**
 * The Class Splash.
 */
public class Splash extends AppCompatActivity {

    Context _Context;

    final int WELCOME = 25;

    int _Paso = 1000;


    /** (non-Javadoc)
     * @see Activity#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        _Context = this;

        ImageView _ImgSplash = (ImageView) findViewById(R.id.img_splash);
        Animation _AniImgSplash;
        _AniImgSplash = AnimationUtils.loadAnimation(this, R.anim.mover_img);
        _AniImgSplash.reset();
        _ImgSplash.startAnimation(_AniImgSplash);


    }

    /** (non-Javadoc)
     * @see Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        cuentaAtras(1000);
    }

    /**
     * Cuenta atras.
     *
     * @param milisegundos
     */
    private void cuentaAtras(long milisegundos) {
        CountDownTimer mCountDownTimer;

        mCountDownTimer = new CountDownTimer(milisegundos, _Paso) {

            @Override
            public void onTick(long millisUntilFinished) {
            }


            @Override
            public void onFinish() {
                Intent _Intent = new Intent(_Context, MainActivity.class);

                startActivityForResult(_Intent, WELCOME);
                finish();

            }
        };
        mCountDownTimer.start();
    }

    /** (non-Javadoc)
     * @see Activity#onActivityResult(int, int, Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WELCOME)
            // volvemos a la pantalla de activity_splash desde la pantalla principal,
            // la cerramos (no tiene sentido permanecer aquí):
            finish();
        else
            super.onActivityResult(requestCode, resultCode, data);
    }
}


