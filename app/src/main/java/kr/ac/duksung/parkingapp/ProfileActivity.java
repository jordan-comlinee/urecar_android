package kr.ac.duksung.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import javax.xml.transform.Result;

public class ProfileActivity extends AppCompatActivity {

    private int userId;
    private String userName;
    private String carnum;
    private String phonenum;
    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.d("TEST", "시작");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.179.195:5500")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI result = retrofit.create(RetrofitAPI.class);

        // POST https://roadrunner.tistory.com/648

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put("userid", "2");
        Log.d("POST: ", "ongoing");

        result.postData(param).enqueue(new Callback<myPageResult>() {
            @Override
            public void onResponse(Call<myPageResult> call, Response<myPageResult> response) {
                if(response.isSuccessful()){
                    myPageResult data = response.body();
                    Log.d("POST: ", data.getUserName());
                    Log.d("POST: ", data.getAddress());
                    Log.d("POST: ", data.getCarNum());
                    Log.d("POST: ", data.getPhoneNum());
                }
            }

            @Override
            public void onFailure(Call<myPageResult> call, Throwable t) {
                    Log.d("POST: ", "Failed!!!!");
                    Log.d("TEST: ", "Failed!!!!");
                    t.printStackTrace();
            }
        });


        // 하단 네비게이션 바
        BottomNavigationView bottomNaView = findViewById(R.id.bottom_navigation_view);
        bottomNaView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                    builder.setTitle("로그아웃");
                    builder.setMessage("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intentLogOut = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intentLogOut);
                        }
                    });
                    builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.show();
                    return true;


                }
                return true;
            }
        });


    }
}