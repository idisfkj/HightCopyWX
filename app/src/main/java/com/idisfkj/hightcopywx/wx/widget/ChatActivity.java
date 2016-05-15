package com.idisfkj.hightcopywx.wx.widget;

import android.app.LoaderManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.idisfkj.hightcopywx.App;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapter.ChatAdapter;
import com.idisfkj.hightcopywx.dao.ChatMessageDataHelper;
import com.idisfkj.hightcopywx.ui.BaseActivity;
import com.idisfkj.hightcopywx.util.VolleyUtils;
import com.idisfkj.hightcopywx.wx.presenter.ChatPresenter;
import com.idisfkj.hightcopywx.wx.presenter.ChatPresenterImp;
import com.idisfkj.hightcopywx.wx.view.ChatView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 聊天界面
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public class ChatActivity extends BaseActivity implements ChatView, View.OnTouchListener, View.OnFocusChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    @InjectView(R.id.chat_content)
    EditText chatContent;
    @InjectView(R.id.chat_view)
    RecyclerView chatView;
    @InjectView(R.id.chat_line)
    View chatLine;
    private static final String ACTION_FILTER = "com.idisfkj.hightcopywx.chat";
    private ChatPresenter mChatPresenter;
    private String mChatContent;
    private ChatAdapter mChatAdapter;
    private BroadcastReceiver receiver;
    private InputMethodManager manager;
    private ChatMessageDataHelper chatHelper;
    private String userName;
    private int unReadNum;
    private int _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        ButterKnife.inject(this);
        Bundle bundle = getIntent().getExtras();
        _id = bundle.getInt("_id");

        mChatPresenter = new ChatPresenterImp(this);
        mChatPresenter.loadData(this, _id);

        NotificationManager manager = (NotificationManager) App.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(_id);

        init();

        if (unReadNum > 0) {
            mChatPresenter.cleanUnReadNum(this, App.mRegId, App.mNumber, unReadNum);
        }
        getActionBar().setTitle(userName);
    }

    public void init() {
        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        receiver = new ChatBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_FILTER);
        this.registerReceiver(receiver, filter);

        chatHelper = new ChatMessageDataHelper(this);
        mChatAdapter = new ChatAdapter(this);
        chatView.setLayoutManager(new LinearLayoutManager(this));
        chatView.setAdapter(mChatAdapter);
        chatView.setOnTouchListener(this);
        chatContent.setOnFocusChangeListener(this);

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        // 获取根布局的可视区域r
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        // 本来的实际底部距离 - 可视的底部距离
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && isKeyboardShown(chatContent.getRootView()))
            manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        chatLine.setBackgroundColor(getResources().getColor(R.color.tab_color_s));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return chatHelper.getCursorLoader(App.mNumber, App.mRegId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader != null && data.getCount() <= 0) {
            mChatPresenter.initData(chatHelper, App.mRegId, App.mNumber, userName);
        }
        mChatAdapter.changeCursor(data);
        mChatAdapter.setCursor(data);
        chatView.smoothScrollToPosition(mChatAdapter.getItemCount());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mChatAdapter.changeCursor(null);
    }

    @Override
    public void loadUserInfo(String regId, String number, String userName, int unReadNum) {
        App.mRegId = regId;
        App.mNumber = number;
        this.userName = userName;
        this.unReadNum = unReadNum;
    }

    private class ChatBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mChatPresenter.receiveData(intent, chatHelper);
        }
    }

    @OnClick(R.id.chat_send)
    public void onClick() {
        mChatContent = chatContent.getText().toString();
        if (mChatContent.trim().length() > 0) {
            mChatPresenter.sendData(mChatContent, App.mNumber, App.mRegId, chatHelper);
        }
        chatContent.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //更新数据
        mChatPresenter.updateLasterContent(this, App.mRegId, App.mNumber);
        //重置数据
        App.mNumber = "-1";
        App.mRegId = "-1";
        VolleyUtils.cancelAll("chatRequest");
        this.unregisterReceiver(receiver);
        ButterKnife.reset(this);
    }
}
