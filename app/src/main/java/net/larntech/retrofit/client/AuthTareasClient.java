package net.larntech.retrofit.client;



import net.larntech.common.Constantes;
import net.larntech.retrofit.AuthInterceptor;
import net.larntech.retrofit.service.AuthTareasService;
import net.larntech.retrofit.service.PlanService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthTareasClient {

    public static Retrofit getRetrofit(){

        //Incluir en la cabecera de la petición el TOKEN que autoriza al usuario
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new AuthInterceptor());
        OkHttpClient cliente = okHttpClientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cliente)
                .build();
        
        return retrofit;
    }

    public static AuthTareasService getAuthTareasService(){
        AuthTareasService authTareasService = getRetrofit().create(AuthTareasService.class);

        return authTareasService;
    }




}
