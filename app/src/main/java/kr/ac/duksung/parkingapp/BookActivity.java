package kr.ac.duksung.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  {
    SwipeRefreshLayout swipeRefreshLayout;

    private int plotid;
    private String slotid;
    private String userid;
    private String carnum;
    private int usagetime;
    private String parking_lot_name;
    private String parking_lot_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


        ViewPager vp =findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);
        tab.setTabTextColors(Color.rgb(255,255,255),Color.rgb(255,255,255));
        // 위로 끌어올리면 새로고침되도록 구현하였음
        swipeRefreshLayout = findViewById(R.id.bookLayout);
        swipeRefreshLayout.setOnRefreshListener(this);


        //Firebase가 잘 동작하는 지 테스트
        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello");
        //read
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.toException().toString(), Toast.LENGTH_LONG).show();
            }
        });

         */


    }

    @Override
    public void onRefresh() {
        //새로 고침 코드
        updateLayoutView();
        //새로 고침 완
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.darker_gray);
    }

    // 당겨서 새로고침 했을 때 뷰 변경 메서드
    public void updateLayoutView(){

    }
}