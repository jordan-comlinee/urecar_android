package kr.ac.duksung.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.naver.maps.map.NaverMapSdk;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("s7xoj8yasp"));
    }
}