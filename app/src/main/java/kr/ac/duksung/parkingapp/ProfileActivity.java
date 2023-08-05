package kr.ac.duksung.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {

    private String userId;

    // 서버로부터 받아올 정보들, xml에 올릴 변수들
    private TextView userName, carnum, phonenum, address;

    // BackPressed 에서 시간 초기 설정
    private long time = 0;

    private ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backbutton = (ImageButton) findViewById(R.id.backbutton);
        // 서버 연결 시작
        Log.d("TEST", "시작");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        crud_RetrofitAPI result = retrofit.create(crud_RetrofitAPI.class);

        // POST https://roadrunner.tistory.com/648

        HashMap<String,Object> param = new HashMap<String, Object>();
        param.put("userid", "2");
        Log.d("POST: ", "ongoing");

        result.postData(param).enqueue(new Callback<crud_myPageResult>() {
            // 서버 연결 성공 시 메서드
            @Override
            public void onResponse(Call<crud_myPageResult> call, Response<crud_myPageResult> response) {
                if(response.isSuccessful()){
                    crud_myPageResult data = response.body();
                    // userName 등의 객체 xml로부터 불러오기
                    userName = (TextView) findViewById(R.id.userName);
                    carnum = (TextView) findViewById(R.id.carNum);
                    phonenum = (TextView) findViewById(R.id.phone);
                    address = (TextView) findViewById(R.id.address);

                    // 로그로 맞는 지 확인함
                    Log.d("POST: ", data.getUserName());
                    Log.d("POST: ", data.getAddress());
                    Log.d("POST: ", data.getCarNum());
                    Log.d("POST: ", data.getPhoneNum());

                    // 각 객체에 서버로부터 불러온 정보 가져오기
                    userName.setText(data.getUserName());
                    carnum.setText(data.getCarNum());
                    phonenum.setText(data.getPhoneNum());
                    address.setText(data.getAddress());
                }
            }
            // 서버 연결 실패 시 메서드
            @Override
            public void onFailure(Call<crud_myPageResult> call, Throwable t) {
                    Log.d("POST: ", "Failed!!!!");
                    Log.d("TEST: ", "Failed!!!!");
                    t.printStackTrace();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            builder.setTitle("종료");
            builder.setMessage("시스템을 종료하시겠습니까?");
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        // finish 후 다른 Activity 뜨지 않도록 함
                        moveTaskToBack(true);
                        // 현재 액티비티 종료
                        finish();
                        // 모든 루트 액티비티 종료
                        finishAffinity();
                        // 인텐트 애니 종료
                        overridePendingTransition(0, 0);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
            builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            builder.show();
        }
    }

}