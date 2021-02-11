package net.larntech.retrofit.service;

import net.larntech.retrofit.response.Marca;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarcaService {

    @GET("api/vehiculo/listarMarcas")
    Call<List<Marca>> getMarca();

}
