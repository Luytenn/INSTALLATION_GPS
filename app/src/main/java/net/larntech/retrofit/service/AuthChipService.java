package net.larntech.retrofit.service;

import net.larntech.retrofit.response.GPSVehiculo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthChipService {

    @GET("api/gps/listarGps")
    Call<List<GPSVehiculo>> getChips(
               @Query("imei") String imei);

    

    @GET("api/gps/listarChips")
    Call<List<GPSVehiculo>> getTelefono(
            @Query("parametro") String parametro);


}
