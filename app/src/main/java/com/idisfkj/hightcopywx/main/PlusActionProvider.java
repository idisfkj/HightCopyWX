package com.idisfkj.hightcopywx.main;

import android.content.Context;
import android.content.Intent;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.main.widget.AddFriendsActivity;
import com.idisfkj.hightcopywx.util.ToastUtils;

/**
 * 加号添加子菜单
 * Created by idisfkj on 16/4/21.
 * Email : idisfkj@qq.com.
 */
public class PlusActionProvider extends ActionProvider implements MenuItem.OnMenuItemClickListener {
    private Context mContext;
    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public PlusActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add(Menu.NONE,0,Menu.NONE,mContext.getString(R.string.multi_chat))
                .setIcon(R.drawable.multi_chat)
                .setOnMenuItemClickListener(this);
        subMenu.add(Menu.NONE,1,Menu.NONE,mContext.getString(R.string.add_friends))
                .setIcon(R.drawable.add_friends)
                .setOnMenuItemClickListener(this);
        subMenu.add(Menu.NONE,2,Menu.NONE,mContext.getString(R.string.scan))
                .setIcon(R.drawable.scan)
                .setOnMenuItemClickListener(this);
        subMenu.add(Menu.NONE,3,Menu.NONE,mContext.getString(R.string.pay_money))
                .setIcon(R.drawable.pay_money)
                .setOnMenuItemClickListener(this);
        subMenu.add(Menu.NONE,4,Menu.NONE,mContext.getString(R.string.help_devise))
                .setIcon(R.drawable.help_advise)
                .setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                ToastUtils.showShort("打开群聊");
                break;
            case 1:
                Intent intent = new Intent(mContext, AddFriendsActivity.class);
                mContext.startActivity(intent);
                break;
            case 2:
                ToastUtils.showShort("打开扫一扫");
                break;
            case 3:
                ToastUtils.showShort("打开收付款");
                break;
            case 4:
                ToastUtils.showShort("打开帮助与反馈");
                break;
        }
        return true;
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }
}
