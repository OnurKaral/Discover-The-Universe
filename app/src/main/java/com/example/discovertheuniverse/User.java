package com.example.discovertheuniverse;

import android.net.Uri;

public class User {

    private String muserName;
    private String muId;
    private String mdownloadurl;
    private Uri mbmpUri;
    private String mimageTitle;

    public User() {
    }

    public User(String uid, String downloadurl, String imageTitle) {
        if (uid.trim().equals("")) {
            uid = "No Name";
        }
        muId = uid;
        mdownloadurl = downloadurl;
        mimageTitle = imageTitle;
    }

    public User(String uid, String downloadurl) {

        muId = uid;
        mdownloadurl = downloadurl;

    }

    public User(String uid, String userName, String downloadurl, Uri bmpUri, String imageTitle) {
        muserName = userName;
        muId = uid;
        mbmpUri = bmpUri;
        mdownloadurl = downloadurl;
        mimageTitle = imageTitle;
    }

    public String getDownloadurl() {
        return mdownloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        mdownloadurl = downloadurl;
    }


    public Uri getBmpUri() {
        return mbmpUri;
    }

    public void setBmpUri(Uri bmpUri) {
        mbmpUri = bmpUri;
    }

    public String getuId() {
        return muId;
    }

    public void setuId(String uId) {
        muId = uId;
    }

    public String getUserName() {
        return muserName;
    }

    public void setUserName(String userName) {
        muserName = userName;
    }

    public String getMimageTitle() {
        return mimageTitle;
    }

    public void setMimageTitle(String mimageTitle) {
        this.mimageTitle = mimageTitle;
    }
}
