package com.idisfkj.hightcopywx.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.idisfkj.hightcopywx.main.FragmentFactory;

/**
 * 主界面Fragment适配器
 * Created by idisfkj on 16/4/19.
 * Email : idisfkj@qq.com.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter{
    private FragmentFactory mFragmentFactory;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragmentFactory = new FragmentFactory(4);
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return mFragmentFactory.getSize();
    }
}
