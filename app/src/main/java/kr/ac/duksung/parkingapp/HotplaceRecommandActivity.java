package kr.ac.duksung.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Overlay;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotplaceRecommandActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener {

    // 위치 권한을 받아오기 위함
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMSSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static double[][] location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        location = new double[100][2];
        Log.d("TEST", "시작");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crud_RetrofitAPI retrofitAPI = retrofit.create(crud_RetrofitAPI.class);
        retrofitAPI.getmarkerData(1).enqueue(new Callback<List<crud_Post>>() {
            @Override
            public void onResponse(Call<List<crud_Post>> call, Response<List<crud_Post>> response) {
                if(response.isSuccessful()) {
                    List<crud_Post> data = response.body();
                    Log.d("TEST", "POST 성공"+data.get(0).getPlotid()+data.get(0).getLatitude()+data.get(0).getLongitude());
                    Log.d("TEST", "POST 성공"+data.get(1).getPlotid());
                    Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_LONG).show();
                    for (int i =0;i<data.size();i++) {
                        Log.d("GET: ", data.get(i).getPlotname());
                        Log.d("GET: ", data.get(i).getLatitude()+" "+ data.get(i).getLongitude());
                        Log.d("GET: ", data.get(i).getLocation());
                        Log.d("GET: ", String.valueOf(Integer.valueOf(data.get(i).getTotal_space())-Integer.valueOf(data.get(i).getAvailable_space())));
                        location[i][0] = Double.valueOf(data.get(i).getLatitude());
                        location[i][1] = Double.valueOf(data.get(i).getLongitude());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<crud_Post>> call, Throwable t) {
                Log.d("TEST", "POST 실패");
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                Log.e("TEST", exceptionAsString);
                t.printStackTrace();
            }//onFailure
        });//retrofitAPI.getmarkerData

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotplace_recommand);



    }//onCreate

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

    }//onMapReady(NaverMap)

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        return false;
    }//onClick(NaverMap)
}