package com.idisfkj.hightcopywx.main.presenter;

import android.content.Context;
import android.view.View;

import com.idisfkj.hightcopywx.adapter.SearchResultAdapter;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public interface SearchResultPresenter {
    void checkSelection(Context context, String number, View view, SearchResultAdapter mAdapter);
}
