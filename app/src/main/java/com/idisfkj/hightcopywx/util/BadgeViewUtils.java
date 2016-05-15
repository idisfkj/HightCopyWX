package com.idisfkj.hightcopywx.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.idisfkj.hightcopywx.R;
import com.readystatesoftware.viewbadger.BadgeView;

/**
 * Created by idisfkj on 16/5/13.
 * Email : idisfkj@qq.com.
 */
public class BadgeViewUtils {

    public BadgeViewUtils() {
    }

    public static BadgeView create(Context context, View view, String textValue) {
        BadgeView badgeView = new BadgeView(context, view);
        badgeView.setTextColor(Color.WHITE);
        badgeView.setText(textValue);
        badgeView.setBackground(context.getResources().getDrawable(R.drawable.dot_bg));
        badgeView.setTextSize(12);
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setBadgeMargin(0, 0);
        badgeView.show();
        return badgeView;
    }
}
