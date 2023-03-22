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

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,Overlay.OnClickListener {
    private  static final String TAG = "MainActivity";

    // 위치 권한을 받아오기 위함
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMSSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("s7xoj8yasp"));

        //지도 객체 생성
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if(mapFragment==null){
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        //getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        //onMapReady에서 NaverMap 객체를 받음
        mapFragment.getMapAsync(this);
        //위치를 반환하는 구현체인 FusedLocationSource 생성
        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d( TAG, "onMapReady");

        // 지도 상에 마커 표시하기
        // 영근터 밑 주차장1
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.6506005, 127.0158205));
        marker.setMap(naverMap);
        //////////
        marker.setOnClickListener(this);
        //////////
        // 영근터 밑 주차장2
        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(37.650972, 127.015213));
        marker2.setMap(naverMap);
        //자취집 사유 주차장
        Marker marker3 = new Marker();
        marker3.setPosition(new LatLng(37.654109, 127.014669));
        marker3.setMap(naverMap);
        //하나누리관 주차장
        Marker marker4 = new Marker();
        marker4.setPosition(new LatLng(37.6502939, 127.0193974));
        marker4.setMap(naverMap);
        //NaverMAP 객체 받아서 NaverMap 객체에 위치 소스 지정
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);



        //권한확인. 결과는 onRequesetPermissionsResult 콜백 매서드 호출
        ActivityCompat.requestPermissions(this, PERMSSIONS, PERMISSION_REQUEST_CODE);

    }
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
    }
    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if(overlay instanceof Marker) {
            Toast.makeText(this.getApplicationContext(),"마커가 선택되었습니다.", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}