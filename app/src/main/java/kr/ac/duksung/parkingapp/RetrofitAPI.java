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
<<<<<<< HEAD
    Call<Post> postData(@FieldMap HashMap<Integer, Object> param);

    @GET("login/")
    Call<List<Post>> getLoginData(@Query("userid") String id);

    @FormUrlEncoded
    @POST("login/")
    Call<Post> postLoginData(@FieldMap HashMap<String, Object> param);
=======
    Call<Post> postmarekrData(@FieldMap HashMap<String, Object> param);
    @FormUrlEncoded
    @POST("login/")
    Call<Post> postloginData(@FieldMap HashMap<String, Object> param);
>>>>>>> 60546a1fa19d6f0277165fd048dd0d24e36c13a1
}
