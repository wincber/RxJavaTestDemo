package com.example.wincber.rxjavatestdemo.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    protected <T extends View>T  findView(int id){
        return (T)findViewById(id);
    }
}
