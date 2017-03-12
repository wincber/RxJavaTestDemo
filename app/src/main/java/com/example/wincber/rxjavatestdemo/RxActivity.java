package com.example.wincber.rxjavatestdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wincber.rxjavatestdemo.common.BaseActivity;
import com.example.wincber.rxjavatestdemo.common.Weather;
import com.example.wincber.rxjavatestdemo.common.WeatherConstant;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxActivity extends BaseActivity{
    private EditText input;
    private Button check;
    private TextView content;
    private Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        initView();
        initEvent();
    }
    void initView(){
        input = findView(R.id.et_input_rx);
        check = findView(R.id.bt_check_rx);
        content = findView(R.id.tv_content_rx);
    }
    void initEvent(){
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observableAsNormal(input.getText().toString().trim());
            }
        });
    }
    private void observableAsNormal(final String city){
        subscription = Observable.create(new Observable.OnSubscribe<Weather>() {
            @Override
            public void call(Subscriber<? super Weather> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                try {
                    String weatherInfo = WeatherConstant.getWeather(WeatherConstant.getWeatherApiUrl(city));
                    Weather weather = WeatherConstant.parseXNLWithPull(weatherInfo);
                    subscriber.onNext(weather);
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Weather>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RxActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Weather weather) {
                    content.setText(weather.toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if(subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
        super.onDestroy();

    }
}
