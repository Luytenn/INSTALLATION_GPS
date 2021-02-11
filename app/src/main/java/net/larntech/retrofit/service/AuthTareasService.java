package net.larntech.retrofit.service;

import net.larntech.retrofit.request.RequestGrabarTarea;
import net.larntech.retrofit.request.RequestVehiculo;
import net.larntech.retrofit.response.TecnicoTareas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthTareasService {

    @GET("api/tareas/listar")
    Call<List<TecnicoTareas>> getTareas();

    @POST("api/tareas/grabarTarea")
    Call<RequestGrabarTarea> grabarTarea(@Body RequestGrabarTarea body,
                                         @Query("idTarea") int idTarea);

}
