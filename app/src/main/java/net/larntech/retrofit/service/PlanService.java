package net.larntech.retrofit.service;

import net.larntech.retrofit.request.RequestVehiculo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PlanService {


    @GET("api/tareas/obtenerPlanxId")
    Call<String> obtenerPlan(@Query("id") int id);

}
