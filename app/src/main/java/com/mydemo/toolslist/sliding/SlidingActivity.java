package com.mydemo.toolslist.sliding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.mydemo.toolslist.R;

public class SlidingActivity extends AppCompatActivity {

    TextView tvLeft01, tvRight01, tvLeft02, tvRight02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);

        tvLeft01 = findViewById(R.id.tvLeft01);
        tvRight01 = findViewById(R.id.tvRight01);
//        tvLeft02 = findViewById(R.id.tvLeft02);
//        tvRight02 = findViewById(R.id.tvRight02);


    }
}