package com.example.joe.wei.utils;

/**
 * Created by Joe on 2016/11/3.
 */

public class XListUtil {
    private int Xlist_imageId;
    private String Xlist_title;
    private String Xlist_text;
    private String Xlist_price;

    public XListUtil(int Xlist_imageId,String Xlist_title,String Xlist_text,String Xlist_price){
        this.Xlist_imageId = Xlist_imageId;
        this.Xlist_title = Xlist_title;
        this.Xlist_text = Xlist_text;
        this.Xlist_price = Xlist_price;
    }

    public int getXlist_imageId() {
        return Xlist_imageId;
    }

    public String getXlist_title() {
        return Xlist_title;
    }

    public String getXlist_text() {
        return Xlist_text;
    }

    public String getXlist_price() {
        return Xlist_price;
    }
}
