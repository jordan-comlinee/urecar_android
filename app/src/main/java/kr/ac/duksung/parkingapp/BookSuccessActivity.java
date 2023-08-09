package kr.ac.duksung.parkingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import static nl.dionsegijn.konfetti.core.Position.Relative;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class BookSuccessActivity extends AppCompatActivity {

    private KonfettiView konfettiView = null;
    // 컨페티 모양 지정하기 위한 drawableShape 객체 생성
    private Shape.DrawableShape drawableShape = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_success);
        // 하트모양 컨페티 만들어보기
        final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_heart);
        drawableShape = new Shape.DrawableShape(drawable, true);

        konfettiView = findViewById(R.id.konfettiView);
        parade();
    }


    // 가운데에서 흩뿌려지는 모양
    public void explode() {
        EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .spread(360)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 30f)
                        .position(new Relative(0.5, 0.3))
                        .build()
        );
    }
    // 양 옆에서 흩뿌려지는 모양
    public void parade() {
        EmitterConfig emitterConfig = new Emitter(2, TimeUnit.SECONDS).perSecond(60);
        konfettiView.start(
                // 왼쪽 방향
                new PartyFactory(emitterConfig)
                        // 뿌릴 때 각도 지정
                        .angle(Angle.RIGHT - 45)
                        // 뿌려지는 범위, SMALL/WIDE/ROUND 등이 있음
                        .spread(Spread.WIDE)
                        //컴페티 모양, 네모, 동그라미, 하트모양
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        // 컨페티 색상, hex코드 앞에 0x 붙여서 넣어주면 된다. 랜덤으로 발생
                        .colors(Arrays.asList(0x76E12F, 0x3B7017, 0x0b1604, 0x5eb425))
                        //속도 지정
                        .setSpeedBetween(10f, 60f)
                        // 좌표 지정
                        .position(new Relative(0.0, 0.2))
                        .build(),
                // 오른쪽 방향
                new PartyFactory(emitterConfig)
                        .angle(Angle.LEFT + 45)
                        .spread(Spread.WIDE)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0x76E12F, 0x3B7017, 0x0b1604, 0x5eb425))
                        .setSpeedBetween(10f, 60f)
                        .position(new Relative(1.0, 0.2))
                        .build()
        );
    }
    // 위에서 떨어지는 모양
    public void rain() {
        EmitterConfig emitterConfig = new Emitter(5, TimeUnit.SECONDS).perSecond(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .angle(Angle.BOTTOM)
                        .spread(Spread.ROUND)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 15f)
                        .position(new Relative(0.0, 0.0).between(new Relative(1.0, 0.0)))
                        .build()
        );
    }




}