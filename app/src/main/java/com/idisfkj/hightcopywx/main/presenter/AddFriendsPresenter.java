package com.idisfkj.hightcopywx.main.presenter;

import android.widget.TextView;

import com.idisfkj.hightcopywx.dao.RegisterDataHelper;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public interface AddFriendsPresenter {
    void switchView(CharSequence text);

    void switchActicity(TextView searchContent, RegisterDataHelper helper);
}
