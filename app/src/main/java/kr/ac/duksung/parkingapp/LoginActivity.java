package kr.ac.duksung.parkingapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView loginId = (TextView) findViewById(R.id.IdText);
        TextView loginPw = (TextView) findViewById(R.id.PasswordText);
        Button loginButton = (Button) findViewById(R.id.loginButton);


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
}