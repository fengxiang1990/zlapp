package com.zl.app.data.home.model;

/**
 * Created by admin on 2016/2/16.
 */
public class News {

    private String code;
    private String headline;
    private String name;
    private String content;
    private String createDateFormat;
    private int favoriteNo;
    private int commentNo;
    private int clickNo;
    private int goodNo;
    private String picPath;
    private int userId;
    private String value;
    private String zhaiyao;
    private String url;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDateFormat() {
        return createDateFormat;
    }

    public void setCreateDateFormat(String createDateFormat) {
        this.createDateFormat = createDateFormat;
    }

    public int getFavoriteNo() {
        return favoriteNo;
    }

    public void setFavoriteNo(int favoriteNo) {
        this.favoriteNo = favoriteNo;
    }

    public int getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(int commentNo) {
        this.commentNo = commentNo;
    }

    public int getClickNo() {
        return clickNo;
    }

    public void setClickNo(int clickNo) {
        this.clickNo = clickNo;
    }

    public int getGoodNo() {
        return goodNo;
    }

    public void setGoodNo(int goodNo) {
        this.goodNo = goodNo;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getZhaiyao() {
        return zhaiyao;
    }

    public void setZhaiyao(String zhaiyao) {
        this.zhaiyao = zhaiyao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "News{" +
                "code='" + code + '\'' +
                ", headline='" + headline + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", createDateFormat='" + createDateFormat + '\'' +
                ", favoriteNo=" + favoriteNo +
                ", commentNo=" + commentNo +
                ", clickNo=" + clickNo +
                ", goodNo=" + goodNo +
                ", picPath='" + picPath + '\'' +
                ", userId=" + userId +
                ", value='" + value + '\'' +
                ", zhaiyao='" + zhaiyao + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
