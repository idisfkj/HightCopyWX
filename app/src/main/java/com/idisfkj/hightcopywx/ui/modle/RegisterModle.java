package com.idisfkj.hightcopywx.ui.modle;

import android.widget.EditText;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public interface RegisterModle {
    void saveData(RegisterModleImp.saveDataListener listener, EditText... editTexts);
    void sendAll(RegisterModleImp.sendAllListener listener,String userName,String number);
}
