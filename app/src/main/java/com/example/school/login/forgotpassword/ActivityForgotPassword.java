package com.example.school.login.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.school.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityForgotPassword extends AppCompatActivity {
    @BindView(R.id.btn_forgot_pass_cancel)
    TextView btn_forgot_pass_cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        btn_forgot_pass_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}