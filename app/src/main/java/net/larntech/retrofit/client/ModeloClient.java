package net.larntech.retrofit.client;

import net.larntech.common.Constantes;
import net.larntech.retrofit.AuthInterceptor;
import net.larntech.retrofit.service.ModeloService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModeloClient {

    public static Retrofit getRetrofit(){



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }

    public static ModeloService getModeloService(){
        ModeloService modeloService = getRetrofit().create(ModeloService.class);
        return  modeloService;
    }

}
