package kr.ac.duksung.parkingapp;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("marker/")
    Call<List<Post>> getmarkerData(@Query("plotid") int id);
    @GET("login/")
    Call<List<Post>> getloginData(@Query("plotid") int id);
    @FormUrlEncoded
    @POST("marker/")
    Call<Post> postmarekrData(@FieldMap HashMap<String, Object> param);
    @FormUrlEncoded
    @POST("login/")
    Call<Post> postloginData(@FieldMap HashMap<String, Object> param);
}
