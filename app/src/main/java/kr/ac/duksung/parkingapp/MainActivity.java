package kr.ac.duksung.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,Overlay.OnClickListener {
    private static final String TAG = "MainActivity";
    /*
    //변수 지정(로컬용)
    public double[][] location ={{37.6506, 127.0158},{37.65097, 127.0152},{37.65411, 127.0147},{37.65029,  127.0194},{37.65673, 127.0116}};
    public String[] placeName ={"영근터 주차장","영근터 소형 주차장","사유 주차장","하나누리관 주차장","우이동 공영 주차장"};
    public String[] address ={"서울특별시 삼양로144길 33","서울특별시 도봉구 쌍문1동 420-13","서울특별시 도봉구 삼양로144가길","서울특별시 도봉구 삼양로144길 33","서울특별시 강북구 우이동 105-2"};
    private int[] leftover={4,0,3,2,4};
    */


    // 변수 초기화(서버용)
    private double[][] location;
    private String[] placeName;
    private String[] address;
    private int[] leftover;

    private static Map< String, Integer > leftoverarr = new HashMap<>();

    // 위치 권한을 받아오기 위함
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMSSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;
    private Marker [] markerList = new Marker[5];
    private InfoWindow mInfoWindow;

    private long time = 0;

    // 상세화면 팝업창 버튼 정의
    private Button cancelButton;
    private Button bookButton;
    // 상세화면 라인차트
    private LineChart lineChart;

    private ImageView search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Utils.init(this); // MPAndroidChart 유틸리티 초기화


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FIREBASE", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("TOKEN", token);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });//FIrebaseMessaging





        location = new double[5][2];
        placeName = new String[5];
        address = new String[5];
        leftover = new int[5];
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
                    //Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_LONG).show();
                    for (int i =0;i<data.size();i++) {
                        Log.d("GET: ", data.get(i).getPlotname());
                        Log.d("GET: ", data.get(i).getLatitude()+" "+ data.get(i).getLongitude());
                        Log.d("GET: ", data.get(i).getLocation());
                        Log.d("GET: ", String.valueOf(Integer.valueOf(data.get(i).getTotal_space())-Integer.valueOf(data.get(i).getAvailable_space())));
                        location[i][0] = Double.valueOf(data.get(i).getLatitude());
                        location[i][1] = Double.valueOf(data.get(i).getLongitude());
                        placeName[i]=data.get(i).getPlotname();
                        address[i]=data.get(i).getLocation();
                        leftover[i]=Integer.valueOf(data.get(i).getAvailable_space());
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
            }
        });



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("s7xoj8yasp"));

        View toolbar = findViewById(R.id.toolbar);
        // 하단 네비게이션 바
        BottomNavigationView bottomNaView = findViewById(R.id.bottom_navigation_view);
        Menu menu = bottomNaView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.home);
        menuItem.setChecked(true);


        bottomNaView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 홈 버튼을 눌렀을 때
                if(item.getItemId() == R.id.home) {
                    try {
                        //액티비티 화면 재갱신 시키는 코드
                        Intent intent = getIntent();
                        finish(); //현재 액티비티 종료 실시
                        overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                        startActivity(intent); //현재 액티비티 재실행 실시
                        overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                // 마이페이지 버튼 클릭한 경우
                if(item.getItemId() == R.id.home) {
                    try {
                        //액티비티 화면 재갱신 시키는 코드
                        Intent intent = getIntent();
                        finish(); //현재 액티비티 종료 실시
                        overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                        startActivity(intent); //현재 액티비티 재실행 실시
                        overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }                    try {
                        //액티비티 화면 재갱신 시키는 코드
                        Intent intent = getIntent();
                        finish(); //현재 액티비티 종료 실시
                        overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                        startActivity(intent); //현재 액티비티 재실행 실시
                        overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(item.getItemId() == R.id.profile) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                // 로그아웃 버튼 클릭한 경우
                if(item.getItemId() == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
        });//bottomNaView

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

        search = (ImageView) findViewById(R.id.iv_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });


    }//onCreate

    // 지도에 마커 표시하는 함수
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d( TAG, "onMapReady");

        // 지도 상에 마커 표시하기


        // 리스트 별로 마커 차례대로 표시함
        for (int i=0; i<5; i++) {
            markerList[i] = new Marker();
            markerList[i].setTag(placeName[i]);
            markerList[i].setSubCaptionText(address[i]);
            markerList[i].setPosition(new LatLng(location[i][0], location[i][1]));
            markerList[i].setMap(naverMap);
            markerList[i].setOnClickListener(this);
            markerList[i].setWidth(100);
            markerList[i].setHeight(100);
            // marker2 = 노란색 marker3 = 보라색 none_marker = 회색
            if(leftover[i] == 0)    markerList[i].setIcon(OverlayImage.fromResource(R.drawable.m_empty_parkinglot));
            else markerList[i].setIcon(OverlayImage.fromResource(R.drawable.m_parkinglot));
            leftoverarr.put(placeName[i], leftover[i]);
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
        });//mInfoWindow.setAdapter
        // SearchActivity에서 선택한 주차장 이름을 가져옴
        Intent selectIntent = getIntent();
        String selectedPlaceName = selectIntent.getStringExtra("selectedPlaceName");
        if (selectedPlaceName != null) {
            // 선택한 주차장 이름과 일치하는 마커를 찾아서 표시
            for (int i = 0; i < markerList.length; i++) {
                if (selectedPlaceName.equals((String) markerList[i].getTag())) {
                    // 마커를 클릭한 것처럼 동작
                    Log.d("clicked", (String)markerList[i].getTag());
                    markerList[i].performClick();
                    break;
                }
            }
        }
    }//onMapReady

    // 내 위치 받아와서 네이버 지도에 표시해줌
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

    // 상세 정보 띄워주는 코드
    public void showAlertDialog(Marker marker) {
        // 클릭 시 다이얼로그 생성
        AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(MainActivity.this).inflate(
                R.layout.layout_dialog,
                (LinearLayout)findViewById(R.id.layoutDialog)
        );

        d.setView(view);
        //제목
        ((TextView)view.findViewById(R.id.textTitle)).setText((CharSequence) marker.getTag());
        //상세 내용
        ((TextView)view.findViewById(R.id.textContent)).setText("주소: "+(CharSequence) marker.getSubCaptionText()+"\n\n주차 잔여 자리: "+leftoverarr.get((CharSequence)marker.getTag())+"\n");
        //이미지
        ImageView iv = (ImageView) view.findViewById(R.id.parkimage);
        if (marker.getTag()=="영근터 소형 주차장") {
            iv.setImageResource(R.drawable.park_1);
        } else if (marker.getTag()=="영근터 주차장") {
            iv.setImageResource(R.drawable.park_2);
        }else if (marker.getTag()=="사유 주차장") {
            iv.setImageResource(R.drawable.park_3);
        }else if (marker.getTag()=="하나누리관 주차장") {
            iv.setImageResource(R.drawable.park_4);
        }else if (marker.getTag()=="우이동 공영 주차장") {
            iv.setImageResource(R.drawable.park5);
        }
        //버튼 - 취소
        cancelButton = (Button) view.findViewById(R.id.cancel);
        bookButton = (Button) view.findViewById(R.id.book);

        // 다이얼로그 창
        AlertDialog alertDialog = d.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if (leftoverarr.get((CharSequence)marker.getTag())>0) {
            bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentBook = new Intent(getApplicationContext(), BookActivity.class);
                    startActivity(intentBook);
                }
            });
        }
        else {
            bookButton.setEnabled(false);
            bookButton.setTextColor(getResources().getColor(R.color.dark_grey));
        }


        String[] time = new String[]{"0","08:00", " ", "12:00", " ",
                                     "16:00", " ", "20:00", " ",
                                     "00:00", " ", "04:00", " ", "08:00"};

        // lineChart 생성하기
        lineChart = (LineChart) view.findViewById(R.id.chart);
        ArrayList<Entry> entry_chart = new ArrayList<>(); // 데이터를 담을 Arraylist
        LineData chartData = new LineData(); // 차트에 담길 데이터

        entry_chart.add(new Entry(1, 2)); //entry_chart에 좌표 데이터를 담는다.
        entry_chart.add(new Entry(2, 3));
        entry_chart.add(new Entry(3, 2));
        entry_chart.add(new Entry(4, 4));
        entry_chart.add(new Entry(5, 5));
        entry_chart.add(new Entry(6, 6));
        entry_chart.add(new Entry(7, 5));
        entry_chart.add(new Entry(8, 4));
        entry_chart.add(new Entry(9, 2));
        entry_chart.add(new Entry(10, 1));
        entry_chart.add(new Entry(11, 1));
        entry_chart.add(new Entry(12, 0));
        entry_chart.add(new Entry(13, 2));


        LineDataSet lineDataSet = new LineDataSet(entry_chart, "LineGraph"); // 데이터가 담긴 Arraylist 를 LineDataSet 으로 변환한다.

        lineDataSet.setColor(getResources().getColor(R.color.highlight_green)); // 해당 LineDataSet의 색 설정 :: 각 Line 과 관련된 세팅은 여기서 설정한다.

        chartData.addDataSet(lineDataSet); // 해당 LineDataSet 을 적용될 차트에 들어갈 DataSet 에 넣는다.

        // 선 관련 설정만 남기고 나머지 설정 비활성화
        lineDataSet.setDrawValues(false); // 선 위의 값 표시 비활성화
        lineDataSet.setDrawIcons(false); // 아이콘 표시 비활성화
        lineDataSet.setDrawCircles(false); // 데이터 포인트에 원형 표시 비활성화
        lineDataSet.setDrawCircleHole(false); // 데이터 포인트에 원형 표시 내부의 원형 표시 비활성화
        lineDataSet.setDrawHighlightIndicators(false); // 하이라이트 표시기 비활성화
        lineDataSet.setDrawHorizontalHighlightIndicator(false); // 수평 하이라이트 표시 비활성화
        lineDataSet.setDrawVerticalHighlightIndicator(false); // 수직 하이라이트 표시 비활성화


        // X축 그리드 비활성화
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(time)); // X축에 시간 레이블 설정
        xAxis.setGranularity(1f); // X축 레이블 간격 설정
        //xAxis.setLabelRotationAngle(90);
        xAxis.setTextSize(10f); // 글꼴 크기 조정
        xAxis.setLabelCount(13);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(getResources().getColor(R.color.highlight_green));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // Y축 그리드 비활성화
        YAxis yAxis = lineChart.getAxisLeft(); // 또는 chart.getAxisRight()
        yAxis.setDrawGridLines(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setTextColor(getResources().getColor(R.color.transparent));

        YAxis yAxis2 = lineChart.getAxisRight(); // 또는 chart.getAxisRight()
        yAxis2.setDrawGridLines(false);
        yAxis2.setDrawAxisLine(false);
        yAxis2.setTextColor(getResources().getColor(R.color.transparent));

        // legend(꺾은선 밑에 설명) 없애기
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        lineChart.setNoDataText("데이터가 없습니다!"); // 데이터를 불러오지 못한 경우

        lineChart.getDescription().setEnabled(false); // 선 설명 제거

        lineChart.setExtraOffsets(15f, 0f, 15f, 15f);

        try {
            lineChart.setData(chartData); // 차트에 위의 DataSet을 넣는다.
            lineChart.invalidate(); // 차트 업데이트
            lineChart.setTouchEnabled(false); // 차트 터치 disable
        } catch (Exception e) {
            Log.e("CHART", "error");
            e.printStackTrace();
        }

    }//showAlertDialog

    // Back Button 클릭 시 종료

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
    }//onBackPressed

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if(overlay instanceof Marker) {
            Marker marker = (Marker) overlay;
            if (marker.getInfoWindow() != null){
                mInfoWindow.close();
            }
            else{
                mInfoWindow.open(marker);
                // mInfoWindow 클릭 시 상세정보 띄우기
                mInfoWindow.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        showAlertDialog(marker);
                        return true;
                    }
                });
            }
            return true;
        }
        return false;
    }
}