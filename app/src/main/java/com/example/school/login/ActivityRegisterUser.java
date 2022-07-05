package com.example.school.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.school.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityRegisterUser extends AppCompatActivity {
    @BindView(R.id.btn_reg_cancel)
    TextView btn_reg_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        ButterKnife.bind(this);
        btn_reg_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}