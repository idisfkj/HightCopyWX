package com.idisfkj.hightcopywx.ui.presenter;

import android.widget.EditText;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public interface RegisterPresenter {
    void switchUserLine(boolean hasFocus,int id);
    void registerInfo(EditText... editTexts);
}
