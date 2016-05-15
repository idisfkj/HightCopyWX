package com.idisfkj.hightcopywx.wx.view;

/**
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public interface ChatView {
    /**
     * 获取该聊天用户信息
     * @param regId
     * @param number
     * @param userName
     * @param unReadNum
     */
    void loadUserInfo(String regId, String number, String userName, int unReadNum);
}
