package com.idisfkj.hightcopywx.main.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.view.View;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.adapter.SearchResultAdapter;
import com.idisfkj.hightcopywx.beans.ChatMessageInfo;
import com.idisfkj.hightcopywx.beans.WXItemInfo;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.dao.WXDataHelper;
import com.idisfkj.hightcopywx.main.model.SearchResultModel;
import com.idisfkj.hightcopywx.main.model.SearchResultModelImp;
import com.idisfkj.hightcopywx.main.view.SearchResultView;
import com.idisfkj.hightcopywx.util.CalendarUtils;
import com.idisfkj.hightcopywx.util.SPUtils;
import com.idisfkj.hightcopywx.util.VolleyUtils;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public class SearchResultPresenterImp implements SearchResultPresenter, SearchResultModelImp.requestListener {
    private SearchResultModel mModel;
    private SearchResultView mView;
    private WXDataHelper wxHelper;
    private ChatMessageDataHelper chatHelper;

    public SearchResultPresenterImp(SearchResultView View) {
        this.mView = View;
        mModel = new SearchResultModelImp();
    }

    @Override
    public void checkSelection(Context context, String number, View view, SearchResultAdapter adapter) {
        mModel.buildDialog(context,number,view,adapter,this);
    }

    @Override
    public void onSucceed(String userName, String number, String regId, Cursor cursor, ProgressDialog pd) {
        wxHelper = new WXDataHelper(App.getAppContext());
        chatHelper = new ChatMessageDataHelper(App.getAppContext());
        WXItemInfo info = new WXItemInfo();
        String currentAccount = SPUtils.getString("userPhone");
        info.setTitle(userName);
        info.setNumber(number);
        info.setRegId(regId);
        info.setContent(String.format(App.HELLO_MESSAGE, userName));
        info.setCurrentAccount(currentAccount);
        info.setTime(CalendarUtils.getCurrentDate());
        //添加到聊天通信数据库
        wxHelper.insert(info);
        cursor.close();

        //添加系统消息
        ChatMessageInfo chatInfo = new ChatMessageInfo(String.format(App.HELLO_MESSAGE,userName),2
                ,CalendarUtils.getCurrentDate(),currentAccount,regId,number);
        chatHelper.insert(chatInfo);

        VolleyUtils.cancelAll("addRequest");
        mView.succeedToFinish();
        mView.hideProgressDialog(pd);
        mView.showSucceedToast();
    }

    @Override
    public void onError(ProgressDialog pd) {
        pd.cancel();
        mView.showErrorToast();
    }
}
