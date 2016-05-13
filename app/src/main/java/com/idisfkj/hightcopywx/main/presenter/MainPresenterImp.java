package com.idisfkj.hightcopywx.main.presenter;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.main.model.MainModel;
import com.idisfkj.hightcopywx.main.model.MainModelImp;
import com.idisfkj.hightcopywx.main.view.MainView;

/**
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class MainPresenterImp implements MainPresenter {

    private MainView mMianViw;
    private MainModel mMainModel;

    public MainPresenterImp(MainView mainView) {
        mMianViw = mainView;
        mMainModel = new MainModelImp();
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.ll_wx:
                mMianViw.switchWX();
                break;
            case R.id.ll_address:
                mMianViw.switchAddressBook();
                break;
            case R.id.ll_find:
                mMianViw.switchFind();
                break;
            case R.id.ll_me:
                mMianViw.switchMe();
                break;
        }
        mMianViw.switchAlpha(id);
    }

    @Override
    public void switchActivity() {
        mMianViw.jumpChatActivity();
    }
}
