package com.idisfkj.hightcopywx.beans;

import java.io.Serializable;

/**
 * Created by idisfkj on 16/4/25.
 * Email : idisfkj@qq.com.
 */
public class ChatMessageInfo implements Serializable {
    private String message;
    /**
     * 接收与发送的标识
     */
    private int flag;
    private String time;
    /**
     * 接收该信息的账号
     */
    private String receiverNumber;
    private String regId;
    /**
     * 发送者的账号
     */
    private String sendNumber;

    public ChatMessageInfo() {
    }

    public ChatMessageInfo(String message, int flag, String time, String receiverNumber, String regId, String sendNumber) {
        this.message = message;
        this.flag = flag;
        this.time = time;
        this.receiverNumber = receiverNumber;
        this.regId = regId;
        this.sendNumber = sendNumber;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(String sendNumber) {
        this.sendNumber = sendNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
