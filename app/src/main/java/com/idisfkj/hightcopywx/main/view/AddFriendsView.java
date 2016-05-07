package com.idisfkj.hightcopywx.main.view;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public interface AddFriendsView {
    void showSearch();

    void goneSearch();

    void jumpSearchResult(String text);

    void changeText(CharSequence text);

    void showToast(String text);
}
