package com.idisfkj.hightcopywx.main.widget;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.adapter.SearchResultAdapter;
import com.idisfkj.hightcopywx.dao.RegisterDataHelper;
import com.idisfkj.hightcopywx.main.presenter.SearchResultPresenter;
import com.idisfkj.hightcopywx.main.presenter.SearchResultPresenterImp;
import com.idisfkj.hightcopywx.main.view.SearchResultView;
import com.idisfkj.hightcopywx.util.ToastUtils;
import com.idisfkj.hightcopywx.wx.WXItemDecoration;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by idisfkj on 16/5/7.
 * Email : idisfkj@qq.com.
 */
public class SearchResultActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>, SearchResultAdapter.SearchItemClickListener, SearchResultView{

    @InjectView(R.id.search_result)
    RecyclerView searchResult;

    private String mSearchResult;
    private RegisterDataHelper mRegisterHelper;
    private SearchResultAdapter mAdapter;
    private SearchResultPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        ButterKnife.inject(this);
        mPresenter = new SearchResultPresenterImp(this);
        mSearchResult = getIntent().getStringExtra("searchResult");
        mRegisterHelper = new RegisterDataHelper(this);
        mAdapter = new SearchResultAdapter(this);
        mAdapter.setOnItemClickListener(this);
        searchResult.setAdapter(mAdapter);
        searchResult.setLayoutManager(new LinearLayoutManager(this));
        searchResult.addItemDecoration(new WXItemDecoration(this));
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mRegisterHelper.getCursorLoader(mSearchResult);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }

    @Override
    public void onItemClick(View view) {
        mPresenter.checkSelection(this, mSearchResult,view,mAdapter);
    }


    @Override
    public void succeedToFinish() {
        ToastUtils.showShort("添加成功！");
        finish();
    }

    @Override
    public void hideProgressDialog(ProgressDialog pd) {
        pd.cancel();
    }
}
