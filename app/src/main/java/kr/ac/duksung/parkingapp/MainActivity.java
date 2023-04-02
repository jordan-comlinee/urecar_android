package kr.ac.duksung.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,Overlay.OnClickListener {
    private static final String TAG = "MainActivity";

    private static double[][] location = {{37.6506005, 127.0158205}, {37.650972, 127.015213}, {37.654109, 127.014669}, {37.6502939, 127.0193974}, {37.656725, 127.011576}};
    private static String[] placeName = {"영근터 주차장", "영근터 소형 주차장", "사유 주차장", "하나누리관 주차장", "우이동 공영 주차장"};
    private static String[] address = {"서울특별시 삼양로144길 33", "서울 도봉구 쌍문1동 420-13", "서울특별시 도봉구 삼양로144가길" , "서울특별시 도봉구 삼양로144길 33" , "서울특별시 강북구 우이동 105-2"};

    // 위치 권한을 받아오기 위함
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMSSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;

    private InfoWindow mInfoWindow;

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
        Marker [] markerList = new Marker[5];
        // 리스트 별로 마커 차례대로 표시함
        for (int i=0; i<5; i++) {
            markerList[i] = new Marker();
            markerList[i].setTag(placeName[i]);
            markerList[i].setSubCaptionText(address[i]);
            markerList[i].setPosition(new LatLng(location[i][0], location[i][1]));
            markerList[i].setMap(naverMap);
            markerList[i].setOnClickListener(this);
        }

        //NaverMAP 객체 받아서 NaverMap 객체에 위치 소스 지정
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);
        //권한확인. 결과는 onRequesetPermissionsResult 콜백 매서드 호출
        ActivityCompat.requestPermissions(this, PERMSSIONS, PERMISSION_REQUEST_CODE);

        //InfoWindow 객체 생성
        mInfoWindow = new InfoWindow();
        mInfoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this){
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow){
                return (CharSequence)infoWindow.getMarker().getTag();
            }
        });
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
            Marker marker = (Marker) overlay;
            // 클릭 시 다이얼로그 생성
            AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.this);
            //제목
            d.setTitle("상세 정보");
            //상세 내용
            d.setMessage((CharSequence) marker.getSubCaptionText()+"\n주차 남는자리\n잔여: 5\n");
            // 버튼 생성
            d.setPositiveButton("예약하기", new DialogInterface.OnClickListener() {
                // 버튼 누를 때 act
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "예약버튼 누름", Toast.LENGTH_LONG).show();
                }
            });
            d.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Toast.makeText(getApplicationContext(), "취소 누름", Toast.LENGTH_LONG).show();
                }
            });
            if (marker.getInfoWindow() != null){
                mInfoWindow.close();
            }
            else{
                d.show();
                mInfoWindow.open(marker);
               // Toast.makeText(this.getApplicationContext(),"InfoWindow Open.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }
}