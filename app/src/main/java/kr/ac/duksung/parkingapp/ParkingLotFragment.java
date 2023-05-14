package kr.ac.duksung.parkingapp;

import static kr.ac.duksung.parkingapp.R.drawable.no_car_button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParkingLotFragment extends Fragment {

    private Button button1, button2, button3, button4, button5;
    private TextView carnum;
    private int[] YN={0,1,1,0,0};
    private String carnum_result;
    List<Integer> booktime = new ArrayList<>(Arrays.asList(1,1,1,1,1));
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_parking_lot, container, false);
        View v2 = inflater.inflate(R.layout.layout_dialog_book, container, false);
        button1 = (Button) v.findViewById(R.id.button1);
        button2 = (Button) v.findViewById(R.id.button2);
        button3 = (Button) v.findViewById(R.id.button3);
        button4 = (Button) v.findViewById(R.id.button4);
        button5 = (Button) v.findViewById(R.id.button5);
        button1.setBackgroundResource(R.drawable.no_car_button);
        button4.setBackgroundResource(R.drawable.no_car_button);
        Button[] buttons = {button1, button2, button3, button4, button5};
        for(int i=0; i<5; i++) {
            if(YN[i]==0) {
                buttons[i].setBackgroundResource(R.drawable.yes_car_button);
                buttons[i].setTextColor(Color.WHITE);
                buttons[i].setText("p-"+(i+1)+"  예약가능");

                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("예약");
                        builder.setMessage("예약 하시겠습니까?");
                        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog.Builder book = new AlertDialog.Builder(getActivity());
                                View view = inflater.inflate(R.layout.layout_dialog_book, container, false);
                                book.setView(view);
                                Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
                                ArrayAdapter timeAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.time, android.R.layout.simple_spinner_item);
                                timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(timeAdapter);
                                ((TextView) view.findViewById(R.id.textTitle)).setText("p-"+(i+1));
                                carnum = (TextView) view.findViewById(R.id.carnumber);
                                book.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    // 버튼 누를 때 act
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String str = spinner.getSelectedItem().toString();
                                        str = str.substring(0,1);
                                        carnum_result = carnum.getText().toString();
                                        //booktime.set(i, Integer.parseInt(str));
                                        Toast.makeText(view.getContext(), str+"/"+carnum_result, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                book.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                book.show();
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
                });
            } else {
                buttons[i].setBackgroundResource(R.drawable.no_car_button);
                buttons[i].setTextColor(Color.RED);
                buttons[i].setText("p-"+(i+1)+"  예약중");

            }
        }
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}