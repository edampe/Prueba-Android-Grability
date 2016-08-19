package com.edampe.pruebas.pruebaandroidgrability.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.edampe.pruebas.pruebaandroidgrability.R;
import com.edampe.pruebas.pruebaandroidgrability.ui.fragment.DetalleAppFragment;

/**
 * Clase Detalle App
 */
public class DetalleAppActivity extends AppCompatActivity{

    DetalleAppFragment _DetalleAppFragment = new DetalleAppFragment();

    /*
     * (non-Javadoc)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_app);

        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        // Instanciamos el fragment correspondiente.
        Bundle bundle = getIntent().getExtras();

        getSupportFragmentManager().beginTransaction().add(R.id.detalle_app_container,
                _DetalleAppFragment.newInstance(bundle)).commit();
    }}




