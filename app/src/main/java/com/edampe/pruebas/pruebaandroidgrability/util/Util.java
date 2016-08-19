package com.edampe.pruebas.pruebaandroidgrability.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.edampe.pruebas.pruebaandroidgrability.R;

/**
 * Clase con metodos genericos utilizados en toda la aplicacion
 */
public class Util {

    ProgressDialog _ProgresCargando;

    Activity _Activity;


    public Util(Activity _Activity) {
        this._Activity = _Activity;
        _ProgresCargando = new ProgressDialog(_Activity);
    }

    /**
     * Muestra la barra de progreso
     *
     * @param _Mensaje
     */
    public void mostrarProgress(String _Mensaje) {
        _ProgresCargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _ProgresCargando.setIndeterminate(true);
        _ProgresCargando.setCancelable(false);
        _ProgresCargando.setCanceledOnTouchOutside(false);
        _ProgresCargando.setMessage(_Mensaje);
        _ProgresCargando.show();
    }

    /**
     * Oculta la barra de progreso
     */
    public void ocultarProgress() {
        _ProgresCargando.dismiss();
    }

    /**
     * Verificar si el dispositivo tiene conexion a internet.
     */
    public boolean isDeviceOnlineActivity(View _ParentLayout) {
        ConnectivityManager cm = (ConnectivityManager) _Activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            Snackbar.make(_ParentLayout, _Activity.getResources().getString(R.string.mensaje_error_conexion), Snackbar.LENGTH_LONG)
                    .setAction(_Activity.getResources().getString(R.string.mensaje_conectar),
                            new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            _Activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    })
                    .setActionTextColor(_Activity.getResources().getColor(android.R.color.holo_red_light ))
                    .show();

            return false;
        } else {
            return true;
        }
    }

    public void ocultarTecladoActivity() {
        InputMethodManager inputMethodManager = (InputMethodManager) _Activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(_Activity.getCurrentFocus().getWindowToken(), 0);
    }

}
