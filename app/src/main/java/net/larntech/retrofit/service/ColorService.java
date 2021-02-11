package net.larntech.retrofit.service;

import net.larntech.retrofit.response.ColorVehiculo;
import net.larntech.retrofit.response.Marca;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ColorService {

    @GET("api/vehiculo/listarColores")
    Call<List<ColorVehiculo>> getColores(
            @Query("descripcion") String descripcion);

}
