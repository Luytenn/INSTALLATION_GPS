package net.larntech.retrofit.service;

import net.larntech.retrofit.response.TipoVehiculo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TipoVehiculoService {

    @GET("api/vehiculo/listarTipos")
    Call<List<TipoVehiculo>> getTipoVechiulo(
            @Query("descripcion") String descripcion);

}
