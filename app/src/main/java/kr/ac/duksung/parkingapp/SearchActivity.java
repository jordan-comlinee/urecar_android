package kr.ac.duksung.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import kotlinx.coroutines.ObsoleteCoroutinesApi;

public class SearchActivity extends AppCompatActivity {


    public static ArrayList<Address> addressList = new ArrayList<Address>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        try {

            // 컴포넌트 매핑 실시
            SearchView searchView = (SearchView) findViewById(R.id.searchView);
            ListView listView = (ListView) findViewById(R.id.search_listView);


            // 배열 선언 실시
            final ArrayList list = new ArrayList<>();
            list.add("영근터 주차장");
            list.add("영근터 소형 주차장");
            list.add("사유 주차장");
            list.add("하나누리관 주차장");
            list.add("우이동 공영 주차장");
            list.add("삼양로");


            // 어댑터 선언 및 리스트 뷰에 지정
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list){
                @Override
                public View getView(int position, View convertView, ViewGroup parent)
                {
                    View view = super.getView(position, convertView, parent);
                    TextView tv = (TextView) view.findViewById(android.R.id.text1);
                    tv.setTextColor(getResources().getColor(R.color.highlight_green));
                    return view;
                }
            };



            listView.setAdapter(adapter);


            // searchView 이벤트 리스너
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    // [검색 진행 시 >> 단어 필터링]
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}