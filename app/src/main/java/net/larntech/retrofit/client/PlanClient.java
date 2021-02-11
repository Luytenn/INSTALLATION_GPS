package net.larntech.retrofit.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.larntech.common.Constantes;
import net.larntech.retrofit.AuthInterceptor;
import net.larntech.retrofit.service.AuthTareasService;
import net.larntech.retrofit.service.PlanService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PlanClient {

    public static Retrofit getRetrofit(){

        //Incluir en la cabecera de la petición el TOKEN que autoriza al usuario
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new AuthInterceptor());
        OkHttpClient cliente = okHttpClientBuilder.build();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(cliente)
                .build();

        return retrofit;
    }


    public static PlanService getPlanService(){
        PlanService planService = getRetrofit().create(PlanService.class);

        return planService;
    }

}
