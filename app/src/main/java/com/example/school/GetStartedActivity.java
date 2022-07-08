package com.example.school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.school.home.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetStartedActivity extends AppCompatActivity {
    @BindView(R.id.btn_getStarted)
    TextView btn_getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        ButterKnife.bind(this);


        btn_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetStartedActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}