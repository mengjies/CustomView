package com.mj.customview.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mj.customview.R;

public class CustomView1Activity extends AppCompatActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CustomView1Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view1);
    }
}
