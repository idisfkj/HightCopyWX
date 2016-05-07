package com.idisfkj.hightcopywx.main.view;

import android.app.ProgressDialog;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public interface SearchResultView {
    void succeedToFinish();

    void hideProgressDialog(ProgressDialog pd);

    void showSucceedToast();

    void showErrorToast();
}
