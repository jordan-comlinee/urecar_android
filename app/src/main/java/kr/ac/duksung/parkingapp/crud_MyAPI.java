package kr.ac.duksung.parkingapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface crud_MyAPI {

    @POST("/posts/")
    Call<crud_Post> post_posts(@Body crud_Post post);

    @PATCH("/posts/{pk}/")
    Call<crud_Post> patch_posts(@Path("pk") int pk, @Body crud_Post post);

    @DELETE("/posts/{pk}/")
    Call<crud_Post> delete_posts(@Path("pk") int pk);

    @GET("/posts/")
    Call<List<crud_Post>> get_posts();

    @GET("/posts/{pk}/")
    Call<crud_Post> get_post_pk(@Path("pk") int pk);
}