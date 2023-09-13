package kr.ac.duksung.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class splashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView oval1 = findViewById(R.id.oval1);
        ImageView oval2 = findViewById(R.id.oval2);
        ImageView oval3 = findViewById(R.id.oval3);
        ImageView oval4 = findViewById(R.id.oval4);
        ImageView oval5 = findViewById(R.id.oval5);
        final Animation scale = AnimationUtils.loadAnimation(splashActivity.this, R.anim.scale);
        final Animation scaleSlow = AnimationUtils.loadAnimation(splashActivity.this, R.anim.scale_slow);
        oval1.startAnimation(scale);
        oval2.startAnimation(scaleSlow);
        oval3.startAnimation(scale);
        oval4.startAnimation(scale);
        oval5.startAnimation(scaleSlow);

        moveHome(5);

        /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    startActivity(new Intent(splashActivity.this, MainActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logo.startAnimation(anim);
        */

    }

    private void moveHome(int sec) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000 * sec); //sec 초만큼 딜레이를 준 후 시작한다는 뜻
    }

}