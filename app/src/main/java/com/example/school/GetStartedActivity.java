package com.example.school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.school.home.HomeFragment;
import com.example.school.home.MainActivity;

public class GetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(GetStartedActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}