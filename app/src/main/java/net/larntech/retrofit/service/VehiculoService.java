package net.larntech.retrofit.service;

import net.larntech.retrofit.request.RequestVehiculo;
import net.larntech.retrofit.response.TareaCompleta;
import net.larntech.retrofit.response.TecnicoTareas;
import net.larntech.retrofit.response.Ubicacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VehiculoService {


        @POST("api/vehiculo/guardarVehiculo")
        Call<RequestVehiculo> registrarVehiculo(@Body RequestVehiculo body,
                                                @Query("idTarea") int idTarea);

        @GET("api/vehiculo/obtenerUltimaUbicacion")
        Call<Ubicacion> obtenerUbicacion(@Query("idVehiculo") int idVehiculo);

        @GET("api/tareas/obtenerVehiculoxId")
        Call<Integer> obtenerVehiculoxId(@Query("id") int id);

        @POST("api/vehiculo/enviarComando")
        Call<RequestVehiculo> enviarComando(@Query("idVehiculo") Integer idVehiculo,
                                            @Query("flagActivado") int flagActivado);

        @GET("api/tareas/editar/{idTarea}")
        Call<TareaCompleta> getTareaEditar(@Path("idTarea") int idTarea);


}
