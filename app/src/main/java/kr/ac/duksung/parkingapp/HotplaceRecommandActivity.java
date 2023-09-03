package kr.ac.duksung.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

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
    private static double[][] restaurantLocation;
    private static FusedLocationSource mLocationSource;
    private static NaverMap mNaverMap;
    private InfoWindow mInfoWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        restaurantLocation = new double[100][2];
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
                        restaurantLocation[i][0] = Double.valueOf(data.get(i).getLatitude());
                        restaurantLocation[i][1] = Double.valueOf(data.get(i).getLongitude());
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




        //지도 객체 생성
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.recommandMap);
        if(mapFragment==null){
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.recommandMap, mapFragment).commit();
        }
        //getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        //onMapReady에서 NaverMap 객체를 받음
        mapFragment.getMapAsync(this);
        //위치를 반환하는 구현체인 FusedLocationSource 생성
        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);



    }//onCreate

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        //NaverMAP 객체 받아서 NaverMap 객체에 위치 소스 지정
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);
        //권한확인. 결과는 onRequestPermissionsResult 콜백 매서드 호출
        ActivityCompat.requestPermissions(this, PERMSSIONS, PERMISSION_REQUEST_CODE);

        //InfoWindow 객체 생성
        mInfoWindow = new InfoWindow();
        mInfoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this){
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow){
                return (CharSequence)infoWindow.getMarker().getTag();
            }
        });//mInfoWindow.setAdapter

    }//onMapReady(NaverMap)

    //내 위치 받아와서 네이버 지도에 표시해줌
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }//onRequestPermissionsResult


    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        return false;
    }//onClick(NaverMap)
}