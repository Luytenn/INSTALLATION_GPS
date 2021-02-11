package net.larntech.retrofit.client;



import net.larntech.common.Constantes;
import net.larntech.retrofit.service.LoginService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginClient {
    private static LoginClient instance = null;
    private LoginService loginService;
    private Retrofit retrofit;

    public LoginClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginService = retrofit.create(LoginService.class);
    }

    // Patrón Singleton
    public static LoginClient getInstance() {
        if(instance == null) {
            instance = new LoginClient();
        }
        return instance;
    }

    public LoginService getLoginService() {
        return loginService;
    }
}