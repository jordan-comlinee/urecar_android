package kr.ac.duksung.parkingapp;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface crud_RetrofitAPI {
    @GET("marker/")
    Call<List<crud_Post>> getmarkerData(@Query("plotid") int id);
    @GET("login/")
    Call<crud_LoginResult> getloginData(@Query("userid") String userid, @Query("password") String password);
    @FormUrlEncoded
    @POST("marker/")
    Call<crud_Post> postmarkerData(@FieldMap HashMap<String, Object> param);
    @FormUrlEncoded
    @POST("login/")
    Call<crud_LoginResult> postloginData(@FieldMap HashMap<String, Object> lparam);

    @FormUrlEncoded
    @POST("update_reservation/")
    Call<crud_BookResult> postBookData(@FieldMap HashMap<String, Object> param);

    @GET("update_reservation/")
    Call<crud_BookResult> getBookData(@Query("parking_lot_name") String parking_lot_name, @Query("parking_lot_location") String parking_lot_location, @Query("slotid") String slotid, @Query("usagetime") int usagetime);
    /*
     POST 방식으로 myPage 보내기
    */
    @FormUrlEncoded
    @POST("mypage/")
    Call<crud_myPageResult> postData(@FieldMap HashMap<String, Object> param);

    @GET("mypage/")
    Call<List<crud_myPageResult>> getMyPageData(@Query("userid") int id, @Query("carnum") String carnum, @Query("username") String username, @Query("phone") String phone, @Query("address") String address);

    @FormUrlEncoded
    @POST("get_slot_info/")
    Call<List<crud_slotResult>> postSlotData(@FieldMap HashMap<String, Object> param);

    @GET("get_slot_info/")
    Call<List<crud_slotResult>> getSlotData(@Query("slotid") String slotid, @Query("available") String available);

    @FormUrlEncoded
    @POST("get_parking_stats/")
    Call<List<crud_parkingState>> postPlotData(@FieldMap HashMap<String, Object> param);

    @GET("get_parking_stats/")
    Call<List<crud_parkingState>> gePlotData(@Query("plotid") int slotid, @Query("time") String time, @Query("stats") float stats);

}
