package com.idisfkj.hightcopywx.ui.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public interface RegisterView {
    void changeUserNameLine(boolean hasFocus);

    void changeUserPhoneLine(boolean hasFocus);

    void changeUserPasswordLine(boolean hasFocus);

    void jumpMainActivity();

    void showAlertDialog();

    void startCrop(Uri uri);

    void setHeadPicture(Intent intent);

    void saveHeadPicture(Bitmap bitmap);

    void showProgressDialog();

    void hideProgressDialog();

    void showErrorToast();

    void showSucceedToast();
}
