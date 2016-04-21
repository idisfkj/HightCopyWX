package com.idisfkj.hightcopywx.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.main.widget.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by idisfkj on 16/4/18.
 * Email : idisfkj@qq.com.
 */
public class WelcomeActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    @Override
    public void onBackPressed() {

    }
}
