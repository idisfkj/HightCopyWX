package com.idisfkj.hightcopywx.ui.model;

import android.widget.EditText;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public interface RegisterModel {
    void saveData(RegisterModelImp.saveDataListener listener, EditText... editTexts);

    void sendAll(RegisterModelImp.sendAllListener listener, String userName, String number);
}
