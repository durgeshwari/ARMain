package com.ArUndigit.ARundigit;

public class ImageViewList {

    String Uri,filname;

    public ImageViewList(String uri, String filname) {
        Uri = uri;
        this.filname = filname;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public String getFilname() {
        return filname;
    }

    public void setFilname(String filname) {
        this.filname = filname;
    }
}
