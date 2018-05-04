package com.ranferi.retrofitexample.api;

import com.ranferi.retrofitexample.model.Result;
import com.ranferi.retrofitexample.model.Users;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    // The register call
    @FormUrlEncoded
    @POST("register")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender);

    // the signing call
    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("users")
    Call<Users> getUsers();
}
