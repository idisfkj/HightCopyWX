package com.idisfkj.hightcopywx.beans;

/**
 * Created by idisfkj on 16/4/29.
 * Email : idisfkj@qq.com.
 */
public class RegisterInfo {
    private String userName;

    private String number;

    private String regId;

    public RegisterInfo() {
    }

    public RegisterInfo(String userName, String number, String regId) {
        this.userName = userName;
        this.number = number;
        this.regId = regId;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
