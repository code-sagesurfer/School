package com.sagesurfer.school;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.sagesurfer.school.home.main.MainActivity;
import com.sagesurfer.school.login.LoginActivity;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Preferences.initialize(this);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (Preferences.contains(General.DOMAIN)){
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 3000);
    }
}