package net.larntech.retrofit.service;

import net.larntech.retrofit.response.Flota;
import net.larntech.retrofit.response.ModeloVehiculo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlotaService {

    @GET("api/vehiculo/listarFlotas")
    Call<List<Flota>> getFlota();

}
