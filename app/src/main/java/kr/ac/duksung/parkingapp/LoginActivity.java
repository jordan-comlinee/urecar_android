package kr.ac.duksung.parkingapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> launcher;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.10.11:5500/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        HashMap<String, Object> input = new HashMap<>();
        input.put("userid", "2");
        input.put("passwork","2222");
        retrofitAPI.postloginData(input).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    Post data = response.body();
                    Log.d("TEST","POST 성공성공");
                    Log.d("TEST",data.getPassword());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
        TextView loginId = (TextView) findViewById(R.id.IdText);
        TextView loginPw = (TextView) findViewById(R.id.PasswordText);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        Log.d("TEST", "시작");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.10.11:5500/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        HashMap<String, Object> input = new HashMap<>();
        input.put("userid", "로그인");
        input.put("password", "비밀번호");
        retrofitAPI.postLoginData(input).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()) {
                    Post data = response.body();
                    Log.d("TEST", "POST SUCCESS");
                    Log.d("TEST", data.getUserid());
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("TEST", "LogIn POST 실패");
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();
                Log.e("TEST", exceptionAsString);
                t.printStackTrace();
            }
        });









        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String id = loginId.getText().toString();
                String pw = loginPw.getText().toString();

                if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(pw))
                    loginButton.setEnabled(true);
                else
                    loginButton.setEnabled(false);
                return false;
            }
        };
        loginId.setOnKeyListener(keyListener);
        loginPw.setOnKeyListener(keyListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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