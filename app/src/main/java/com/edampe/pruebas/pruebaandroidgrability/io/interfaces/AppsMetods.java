package com.edampe.pruebas.pruebaandroidgrability.io.interfaces;

import com.edampe.pruebas.pruebaandroidgrability.io.item.GetFreeApplicationBin;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface con los metodos del servicio
 */
public interface AppsMetods {

    @GET("topfreeapplications/limit=100/genre={GENRE}/json")
    Call<GetFreeApplicationBin> GetTopFreeApplication(@Path("GENRE") String _Genre);


}
