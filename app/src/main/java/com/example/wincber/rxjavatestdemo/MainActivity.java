package com.example.wincber.rxjavatestdemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wincber.rxjavatestdemo.common.BaseActivity;
import com.example.wincber.rxjavatestdemo.common.Weather;
import com.example.wincber.rxjavatestdemo.common.WeatherConstant;

public class MainActivity extends BaseActivity {
    private EditText input;
    private Button check;
    private Button toRx;
    private TextView content;
    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Weather weather =(Weather) msg.obj;
            content.setText(weather.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewEvent();
    }
    void initView(){
        input = findView(R.id.et_input);
        check = findView(R.id.bt_check);
        content = findView(R.id.tv_content);
        toRx = findView(R.id.bt_to_rx);
    }
    void initViewEvent(){
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkContent = input.getText().toString().trim();
                getWeatherInfo(checkContent);
            }
        });
        toRx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RxActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    void getWeatherInfo(final String city){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String weatherInfo = WeatherConstant.getWeather(WeatherConstant.getWeatherApiUrl(city));
                Weather weather = WeatherConstant.parseXNLWithPull(weatherInfo);
                Message msg = new Message();
                msg.obj = weather;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

}
