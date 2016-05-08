package com.idisfkj.hightcopywx.ui.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.EditText;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.ui.model.RegisterModel;
import com.idisfkj.hightcopywx.ui.model.RegisterModelImp;
import com.idisfkj.hightcopywx.ui.view.RegisterView;

/**
 * Created by idisfkj on 16/4/28.
 * Email : idisfkj@qq.com.
 */
public class RegisterPresenterImp implements RegisterPresenter, RegisterModelImp.saveDataListener, RegisterModelImp.sendAllListener {
    private RegisterView mRegisterView;
    private RegisterModel mRegisterModel;

    public RegisterPresenterImp(RegisterView mRegisterView) {
        this.mRegisterView = mRegisterView;
        mRegisterModel = new RegisterModelImp();
    }

    @Override
    public void switchUserLine(boolean hasFocus,int id) {
        switch (id){
            case R.id.userName_et:
                mRegisterView.changeUserNameLine(hasFocus);
                break;
            case R.id.userPhone_et:
                mRegisterView.changeUserPhoneLine(hasFocus);
                break;
            case R.id.userPassword_et:
                mRegisterView.changeUserPasswordLine(hasFocus);
                break;
        }
    }

    @Override
    public void registerInfo(EditText... editTexts) {
        mRegisterView.showProgressDialog();
        mRegisterModel.saveData(this,editTexts);
    }

    @Override
    public void choosePicture() {
        mRegisterView.showAlertDialog();
    }

    @Override
    public void callCrop(Uri uri) {
        mRegisterView.startCrop(uri);
    }

    @Override
    public void getPicture(Intent intent) {
        mRegisterView.setHeadPicture(intent);
    }

    @Override
    public void savePicture(Bitmap bitmap) {
        mRegisterView.saveHeadPicture(bitmap);
    }

    @Override
    public void onSucceed(String userName,String number) {
        mRegisterModel.sendAll(this,userName,number);
    }

    @Override
    public void onSendSucceed() {
        mRegisterView.hideProgressDialog();
        mRegisterView.showSucceedToast();
        mRegisterView.jumpMainActivity();
    }

    @Override
    public void onError() {
        mRegisterView.hideProgressDialog();
        mRegisterView.showErrorToast();
    }
}
