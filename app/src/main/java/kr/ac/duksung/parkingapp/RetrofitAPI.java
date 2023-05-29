package kr.ac.duksung.parkingapp;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("marker/")
    Call<List<Post>> getmarkerData(@Query("plotid") int id);
    @GET("login/")
    Call<Post> getloginData(@Query("userid") String userid, @Query("password") String password);
    @FormUrlEncoded
    @POST("marker/")
    Call<Post> postmarekrData(@FieldMap HashMap<String, Object> param);
    @FormUrlEncoded
    @POST("login/")
    Call<Post> postloginData(@Field("userid") String userid, @Field("password") String password);

    /*
     POST 방식으로 myPage 보내기
    */
    @FormUrlEncoded
    @POST("mypage/")
    Call<myPageResult> postData(@FieldMap HashMap<String, Object> param);

    @GET("mypage/")
    Call<myPageResult> getMyPageData(@Query("userid") int id, @Query("carnum") String carnum, @Query("username") String username, @Query("phone") String phone, @Query("address") String address);


}
