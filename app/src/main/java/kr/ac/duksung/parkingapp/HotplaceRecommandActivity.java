package kr.ac.duksung.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Overlay;

public class HotplaceRecommandActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener {

    // 위치 권한을 받아오기 위함
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMSSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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