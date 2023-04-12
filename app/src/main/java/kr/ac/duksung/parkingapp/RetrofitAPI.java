package kr.ac.duksung.parkingapp;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("marker/")
    Call<List<Post>> getData(@Query("plotid") int id);

    @FormUrlEncoded
    @POST("marker/")
    Call<Post> postData(@FieldMap HashMap<Integer, Object> param);

    @GET("login/")
    Call<List<Post>> getLoginData(@Query("userid") String id);

    @FormUrlEncoded
    @POST("login/")
    Call<Post> postLoginData(@FieldMap HashMap<String, Object> param);
}
