package com.idisfkj.hightcopywx.beans;

/**
 * Created by idisfkj on 16/4/22.
 * Email : idisfkj@qq.com.
 */
public class WXItemInfo {
    private String pictureUrl;
    private String title;
    private String content;
    private String time;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WXItemInfo{" +
                "pictureUrl='" + pictureUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
