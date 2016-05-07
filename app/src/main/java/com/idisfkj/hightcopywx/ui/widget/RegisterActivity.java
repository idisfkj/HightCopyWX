package com.idisfkj.hightcopywx.ui.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.main.widget.MainActivity;
import com.idisfkj.hightcopywx.ui.presenter.RegisterPresenter;
import com.idisfkj.hightcopywx.ui.presenter.RegisterPresenterImp;
import com.idisfkj.hightcopywx.ui.view.RegisterView;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
    private String[] items;
    private static final int RESULT_PHOTO = 0;
    private static final int RESULT_CAMERA = 1;
    private static final int RESULT_CROP = 2;
    public static final String PICTURE_NAME = "head.jpg";
    public static final String SAVE_PATH = Environment.getExternalStorageDirectory() + "/hegitcopywx/user_head/";
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        items = new String[]{getResources().getString(R.string.choose_from_photo), getResources().getString(R.string.choose_from_camera)};
        mRegisterPresenter = new RegisterPresenterImp(this);
        userNameEt.setOnFocusChangeListener(this);
        userPhoneEt.setOnFocusChangeListener(this);
        userPasswordEt.setOnFocusChangeListener(this);
    }

    @OnClick({R.id.register_bt, R.id.register_picture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_picture:
                mRegisterPresenter.choosePicture();
                break;
            case R.id.register_bt:
                mRegisterPresenter.registerInfo(userNameEt, userPhoneEt, userPasswordEt);
                break;
        }
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
    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.choose_picture)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent photo = new Intent();
                                photo.setAction(Intent.ACTION_GET_CONTENT);
                                photo.setType("image/*");
                                startActivityForResult(photo, RESULT_PHOTO);
                                break;
                            case 1:
                                Intent camera = new Intent();
                                camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                String state = Environment.getExternalStorageState();
                                if (state.equals(Environment.MEDIA_MOUNTED)) {
                                    //获取系统共享的图库路径
                                    File path = Environment.getExternalStoragePublicDirectory(
                                            Environment.DIRECTORY_PICTURES
                                    );
                                    if (!path.exists()) {
                                        path.mkdirs();
                                    }
                                    //构造要存储的图片
                                    File file = new File(path, PICTURE_NAME);
                                    camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                    startActivityForResult(camera, RESULT_CAMERA);
                                } else {
                                    ToastUtils.showShort(getResources().getString(R.string.choose_camera_error));
                                }
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public void startCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale",true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_CROP);
    }

    @Override
    public void setHeadPicture(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Bitmap bitmap = bundle.getParcelable("data");
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                registerPicture.setImageDrawable(drawable);
                mRegisterPresenter.savePicture(bitmap);
            }
        }
    }

    @Override
    public void saveHeadPicture(Bitmap bitmap) {
        File path = new File(SAVE_PATH);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, PICTURE_NAME);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            //还原图片
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showProgressDialog() {
        pd = ProgressDialog.show(this,"注册自动登录中...","请稍后...",true);
    }

    @Override
    public void hideProgressDialog() {
        pd.cancel();
    }

    @Override
    public void showErrorToast() {
        ToastUtils.showShort("注册登录失败,请检查网络");
    }

    @Override
    public void showSucceedToast() {
        ToastUtils.showShort("注册登录成功！");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_PHOTO:
                if (data != null)
                    mRegisterPresenter.callCrop(data.getData());
                break;
            case RESULT_CAMERA:
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File file = new File(path, PICTURE_NAME);
                Uri uri = Uri.fromFile(file);
                mRegisterPresenter.callCrop(uri);
                break;
            case RESULT_CROP:
                mRegisterPresenter.getPicture(data);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtils.cancelAll("registerRequest");
        ButterKnife.reset(this);
    }
}
