package com.edampe.pruebas.pruebaandroidgrability.cache;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.edampe.pruebas.pruebaandroidgrability.constant.Constants;

import java.util.HashMap;


/**
 * Clase CacheDatosApps.
 */
public class CacheDatosApps {

    // Shared Preferences
    SharedPreferences mSharedPreferences;

    // Sharedpref Nombre del archivo
    private static String PREF_NAME_COD;


    // Editor para Shared preferences
    Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    /**
     * Constructor
     *
     * @param context
     */
    public CacheDatosApps(Context context, String _Codigo) {
        this.context = context;
        PREF_NAME_COD = _Codigo;
        mSharedPreferences = this.context.getSharedPreferences(PREF_NAME_COD, PRIVATE_MODE);
        editor = mSharedPreferences.edit();
    }

    /**
     * Crear cache
     */
    public Boolean createCache(String _Image, String _Name, String _Price, String _Summary, String _Artist,
                               String _Tittle) {

        // Datos del usuario en Shared Preferences.

        editor.putString(Constants.IMAGE, _Image);
        editor.putString(Constants.NAME, _Name);
        editor.putString(Constants.PRICE, _Price);
        editor.putString(Constants.SUMMARY, _Summary);
        editor.putString(Constants.ARTIST, _Artist);
        editor.putString(Constants.TITTLE, _Tittle);

        //  Subir los cambios
        return editor.commit();
    }

    /**
     * Obtener los datos de sesion almacenados
     */
    public HashMap<String, String> getCacheDatos() {
        HashMap<String, String> _ArrayDatos = new HashMap<String, String>();

        _ArrayDatos.put(Constants.IMAGE, mSharedPreferences.getString(Constants.IMAGE, null));
        _ArrayDatos.put(Constants.NAME, mSharedPreferences.getString(Constants.NAME, null));
        _ArrayDatos.put(Constants.PRICE, mSharedPreferences.getString(Constants.PRICE, null));
        _ArrayDatos.put(Constants.SUMMARY, mSharedPreferences.getString(Constants.SUMMARY, null));
        _ArrayDatos.put(Constants.ARTIST, mSharedPreferences.getString(Constants.ARTIST, null));
        _ArrayDatos.put(Constants.TITTLE, mSharedPreferences.getString(Constants.TITTLE, null));


        return _ArrayDatos;
    }
}