package com.mj.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mj.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                CustomView1Activity.actionStart(this);
                break;
            case R.id.button2:
                CustomView2Activity.actionStart(this);
                break;
            case R.id.button3:
                CustomView3Activity.actionStart(this);
                break;
            case R.id.button4:
                CustomView4Activity.actionStart(this);
                break;
            case R.id.button5:
                CustomLayout1Activity.actionStart(this);
                break;
        }
    }
}
