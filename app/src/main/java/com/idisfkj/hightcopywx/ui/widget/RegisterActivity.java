package com.idisfkj.hightcopywx.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.main.widget.MainActivity;
import com.idisfkj.hightcopywx.ui.presenter.RegisterPresenter;
import com.idisfkj.hightcopywx.ui.presenter.RegisterPresenterImp;
import com.idisfkj.hightcopywx.ui.view.RegisterView;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by idisfkj on 16/4/27.
 * Email : idisfkj@qq.com.
 */
public class RegisterActivity extends Activity implements RegisterView, View.OnFocusChangeListener {

    @InjectView(R.id.register_picture)
    ImageView registerPicture;
    @InjectView(R.id.userName_line)
    View userNameLine;
    @InjectView(R.id.userName_et)
    EditText userNameEt;
    @InjectView(R.id.userPhone_line)
    View userPhoneLine;
    @InjectView(R.id.userPhone_et)
    EditText userPhoneEt;
    @InjectView(R.id.userPassword_line)
    View userPasswordLine;
    @InjectView(R.id.userPassword_et)
    EditText userPasswordEt;

    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        mRegisterPresenter = new RegisterPresenterImp(this);
        userNameEt.setOnFocusChangeListener(this);
        userPhoneEt.setOnFocusChangeListener(this);
        userPasswordEt.setOnFocusChangeListener(this);
    }

    @OnClick(R.id.register_bt)
    public void onClick() {
        mRegisterPresenter.registerInfo(userNameEt,userPhoneEt,userPasswordEt);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        mRegisterPresenter.switchUserLine(hasFocus, v.getId());
    }

    @Override
    public void changeUserNameLine(boolean hasFocus) {
        if (hasFocus)
            userNameLine.setBackgroundColor(getResources().getColor(R.color.tab_color_s));
        else
            userNameLine.setBackgroundColor(getResources().getColor(R.color.line_color));
    }

    @Override
    public void changeUserPhoneLine(boolean hasFocus) {
        if (hasFocus)
            userPhoneLine.setBackgroundColor(getResources().getColor(R.color.tab_color_s));
        else
            userPhoneLine.setBackgroundColor(getResources().getColor(R.color.line_color));
    }

    @Override
    public void changeUserPasswordLine(boolean hasFocus) {
        if (hasFocus)
            userPasswordLine.setBackgroundColor(getResources().getColor(R.color.tab_color_s));
        else
            userPasswordLine.setBackgroundColor(getResources().getColor(R.color.line_color));
    }

    @Override
    public void jumpMainActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtils.cancelAll("registerRequest");
        ButterKnife.reset(this);
    }
}
