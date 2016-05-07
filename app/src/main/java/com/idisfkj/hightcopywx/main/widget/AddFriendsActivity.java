package com.idisfkj.hightcopywx.main.widget;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.dao.RegisterDataHelper;
import com.idisfkj.hightcopywx.main.presenter.AddFriendsPresenter;
import com.idisfkj.hightcopywx.main.presenter.AddFriendsPresenterImp;
import com.idisfkj.hightcopywx.main.view.AddFriendsView;
import com.idisfkj.hightcopywx.ui.BaseActivity;
import com.idisfkj.hightcopywx.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by idisfkj on 16/5/6.
 * Email : idisfkj@qq.com.
 */
public class AddFriendsActivity extends BaseActivity implements TextWatcher, AddFriendsView {

    @InjectView(R.id.search_friends)
    EditText searchFriends;
    @InjectView(R.id.search_content)
    TextView searchContent;
    @InjectView(R.id.start_search)
    LinearLayout startSearch;

    private AddFriendsPresenter mPresenter;
    private RegisterDataHelper mRegisterHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friends);
        ButterKnife.inject(this);
        init();
    }

    public void init() {
        mRegisterHelper = new RegisterDataHelper(this);
        mPresenter = new AddFriendsPresenterImp(this);
        searchFriends.addTextChangedListener(this);
    }

    @OnClick(R.id.start_search)
    public void onClick() {
        mPresenter.switchActicity(searchContent,mRegisterHelper);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mPresenter.switchView(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void showSearch() {
        startSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void goneSearch() {
        startSearch.setVisibility(View.GONE);
    }

    @Override
    public void jumpSearchResult(String text) {
        Intent intent = new Intent(this,SearchResultActivity.class);
        intent.putExtra("searchResult",searchContent.getText().toString());
        startActivity(intent);
    }

    @Override
    public void changeText(CharSequence text) {
        searchContent.setText(text);
    }

    @Override
    public void showToast(String text) {
        ToastUtils.showShort(text);
    }
}
