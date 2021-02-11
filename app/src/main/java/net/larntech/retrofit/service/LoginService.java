package net.larntech.retrofit.service;

import android.os.Build;

import androidx.annotation.RequiresApi;




import android.util.*;

import net.larntech.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface LoginService {

    @Headers({"Authorization: Basic "+"YW5kcm9pZEdQUzphbmRyb2lkY2xhdmU="})
    @FormUrlEncoded
    @POST("oauth/token")
    Call<ResponseAuth> doLogin(
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grant_type

    );


}