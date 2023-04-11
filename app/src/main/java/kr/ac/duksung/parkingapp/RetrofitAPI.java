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
    @GET("plot_list/")
    Call<List<Post>> getData(@Query("plotid") int id);

    @FormUrlEncoded
    @POST("plot_list/")
    Call<Post> postData(@FieldMap HashMap<Integer, Object> param);
}
