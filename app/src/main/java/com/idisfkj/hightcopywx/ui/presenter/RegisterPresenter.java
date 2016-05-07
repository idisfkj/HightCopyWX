package com.idisfkj.hightcopywx.ui.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.EditText;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public interface RegisterPresenter {
    void switchUserLine(boolean hasFocus, int id);

    void registerInfo(EditText... editTexts);

    void choosePicture();

    void callCrop(Uri uri);

    void getPicture(Intent intent);

    void savePicture(Bitmap bitmap);
}
