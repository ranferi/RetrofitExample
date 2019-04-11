package com.ranferi.ssrsi.api;

import com.ranferi.ssrsi.model.MessageResponse;
import com.ranferi.ssrsi.model.Messages;
import com.ranferi.ssrsi.model.Places;
import com.ranferi.ssrsi.model.PlacesResponse;
import com.ranferi.ssrsi.model.UserPlace;
import com.ranferi.ssrsi.model.UserResponse;
import com.ranferi.ssrsi.model.Users;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @FormUrlEncoded
    @POST("register")
    Call<UserResponse> createUser(
            @Field("nombre") String name,
            @Field("apellido_paterno") String last,
            @Field("apellido_materno") String maiden,
            @Field("usuario") String user,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<UserResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("users")
    Call<Users> getUsers();
    
    @FormUrlEncoded
    @POST("update/{id}")
    Call<UserResponse> updateUser(
            @Path("id") int id,
            @Field("nombre") String name,
            @Field("apellido_paterno") String last,
            @Field("apellido_materno") String maiden,
            @Field("usuario") String user,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("opinion/{id}")
    Call<UserResponse> updateUserPlace(
            @Path("id") int id,
            @Field("id_sitio") int last,
            @Field("gusto") boolean liked,
            @Field("precio") String price,
            @Field("comentario") String comment
    );

    @FormUrlEncoded
    @GET("search")
    Call<PlacesResponse> searchPlaces(
            @Field("id") int id,
            @Field("tipo") String typePlace,
            @Field("precio") String price,
            @Field("distancia") String distance,
            @Field("musica") boolean music
    );

    @GET("messages/{id}")
    Call<Messages> getMessages(@Path("id") int id);

    @GET("prueba")
    Call<Places> getPlaces();

    @GET("visited/{id}")
    Call<Users> getVisited(@Path("id") int id);
}
