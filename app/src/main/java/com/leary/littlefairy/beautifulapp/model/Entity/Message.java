package com.leary.littlefairy.beautifulapp.model.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * 家庭作业，教育资讯，消息中心，学生风采，班级通知
 * 以上列表的Item
 * LePay
 * Created by wbobo on 2016/12/13 16:03
 */
public class Message {

    private boolean isSelected;
    private int id;

    private int type;

    @SerializedName("has_read")
    private int read;

    private String title;

    private String content;
    private String url;
    @SerializedName("createTime")
    private long createat;

    private String intro;

    public long getCreateAt() {
        return createat;
    }

    public void setCreateAt(long createat) {
        this.createat = createat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int isRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
