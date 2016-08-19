package com.edampe.pruebas.pruebaandroidgrability.io.adapter;

import com.edampe.pruebas.pruebaandroidgrability.constant.Constants;
import com.edampe.pruebas.pruebaandroidgrability.io.interfaces.AppsMetods;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Clase singleton para el manejo de la conexion con el servicio
 */
public class AppsApiAdapter {

    private static AppsMetods API_SERVICE;

    public static AppsMetods getApiService() {
        if (API_SERVICE == null) {
            //servicio

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.MINUTES)
                    .connectTimeout(60, TimeUnit.MINUTES)
                    .writeTimeout(60, TimeUnit.MINUTES)
                    .build();

            Retrofit adapter = new Retrofit.Builder()
                    .baseUrl(Constants.SERVICIO_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            API_SERVICE = adapter.create(AppsMetods.class);
        }


        return API_SERVICE;
    }

}
