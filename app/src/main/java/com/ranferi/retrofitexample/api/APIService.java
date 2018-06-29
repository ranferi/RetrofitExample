package com.ranferi.retrofitexample.api;

import com.ranferi.retrofitexample.model.MessageResponse;
import com.ranferi.retrofitexample.model.Messages;
import com.ranferi.retrofitexample.model.Result;
import com.ranferi.retrofitexample.model.Users;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    // The register call
    @FormUrlEncoded
    @POST("register")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender);
    /*Call<Result> createUser(
            @Field("nombre") String name,
            @Field("apellido_paterno") String last,
            @Field("apellido_materno") String maiden,
            @Field("usuario") String user,
            @Field("email") String email,
            @Field("password") String password);*/

    /* Call<Result> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender);*/

    // the signing call
    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("users")
    Call<Users> getUsers();

    // sending message
    @FormUrlEncoded
    @POST("sendmessage")
    Call<MessageResponse> sendMessage(
            @Field("from") int from,
            @Field("to") int to,
            @Field("title") String title,
            @Field("message") String message
    );

    // updating user
    @FormUrlEncoded
    @POST("update/{id}")
    Call<Result> updateUser(
            @Path("id") int id,
            @Field("nombre") String name,
            @Field("apellido_paterno") String last,
            @Field("apellido_materno") String maiden,
            @Field("usuario") String user,
            @Field("email") String email,
            @Field("password") String password
    );


    /*Call<Result> updateUser(
            @Path("id") int id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender
    );*/

    // getting messages
    @GET("messages/{id}")
    Call<Messages> getMessages(@Path("id") int id);
}
