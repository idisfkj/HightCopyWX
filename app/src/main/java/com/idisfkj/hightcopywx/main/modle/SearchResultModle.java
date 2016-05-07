package com.idisfkj.hightcopywx.main.modle;

import android.content.Context;
import android.view.View;

import com.idisfkj.hightcopywx.adapter.SearchResultAdapter;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public interface SearchResultModle {
    void buildDialog(Context context, String number, View view, SearchResultAdapter adapter,SearchResultModleImp.requestListener listener);
}
