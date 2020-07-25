package com.example.DiscoverTheUniverse;


import android.net.Uri;

public class User {

    private String userName;
    private String uId;
    private String downloadurl;
    private Uri bmpUri;


    public User(String uid, String userName, String downloadurl) {

    }

    public User(String uid, String downloadurl) {

        this.uId = uId;
        this.downloadurl = downloadurl;

    }

    public User(String uid, String userName, String downloadurl, Uri bmpUri) {
        this.userName = userName;
        this.uId = uId;
        this.bmpUri = bmpUri;
        this.downloadurl = downloadurl;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }


    public Uri getBmpUri() {
        return bmpUri;
    }

    public void setBmpUri(Uri bmpUri) {
        this.bmpUri = bmpUri;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
