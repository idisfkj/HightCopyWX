package com.idisfkj.hightcopywx.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by idisfkj on 16/4/21.
 * Email : idisfkj@qq.com.
 */
public abstract class BaseActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    public void initActionBar(){
        ActionBar mActionBar = getActionBar();
        //取消回退图标
        mActionBar.setDisplayHomeAsUpEnabled(false);
        //取消icon图标
        mActionBar.setDisplayShowHomeEnabled(false);
    }
}
