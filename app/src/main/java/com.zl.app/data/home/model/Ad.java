package com.zl.app.data.home.model;

/**
 * Created by admin on 2016/2/16.
 */
public class Ad {

    private String headline;
    private String linkUrl;
    private String picPath;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "headline='" + headline + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", picPath='" + picPath + '\'' +
                '}';
    }
}
