package com.example.school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(LoginActivity.this, GetStartedActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}