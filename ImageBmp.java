package com.company;

import java.io.File;
import java.io.FileInputStream;

public class ImageBmp {

    private long iOffBits;
    private long iSize;
    private long iWidth;
    private long iHeight;
    private long iPadding;


    public long getiWidth() {
        return iWidth;
    }

    public void setiWidth(long iWidth) {
        this.iWidth = iWidth;
    }

    public long getiHeight() {
        return iHeight;
    }

    public void setiHeight(long iHeight) {
        this.iHeight = iHeight;
    }

    public long getiPadding() {
        return iPadding;
    }

    public void setiPadding(long iPadding) {
        this.iPadding = iPadding;
    }

    public long getiOffBits() {
        return iOffBits;
    }

    public void setiOffBits(long iOffBits) {
        this.iOffBits = iOffBits;
    }

    public long getiSize() {
        return iSize;
    }

    public void setiSize(long iSize) {
        this.iSize = iSize;
    }

    public ImageBmp() {

    }


}
