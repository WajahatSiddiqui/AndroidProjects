package com.example.wajahat.project;

/**
 * Created by wajahat on 28/3/16.
 */
public class ListItem {
    private String mProjectTitle;
    private int mPicCount;
    private String [] mImageUrl;
    private String [] mThumbUrl;
    private boolean mIsSelected = false;

    public ListItem(String projectTitle, int count, String [] imageUrl) {
        mProjectTitle = projectTitle;
        mPicCount = count;
        mImageUrl = imageUrl;
    }

    public void setProjectTitle(String projectTitle) {
        mProjectTitle = projectTitle;
    }

    public String getProjectTitle() {
        return mProjectTitle;
    }

    public void setPicCount(int picCount) {
        mPicCount = picCount;
    }

    public int getPicCount() {
        return mPicCount;
    }

    public void setImageUrl(String [] imageUrl) {
        mImageUrl = imageUrl;
    }

    public String[] getImageUrl() {
        return mImageUrl;
    }

    public String[] getThumbUrl() {
        int len = mImageUrl.length;
        mThumbUrl = new String[len];
        int index = 0;
        String ext;
        for (int i = 0; i < len; i++) {
            index = mImageUrl[i].lastIndexOf(".");
            ext = mImageUrl[i].substring(index+1);
            if (index != -1) {
                mThumbUrl[i] = mImageUrl[i].substring(0, index) + "-thumb." + ext;
            }

        }
        return mThumbUrl;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }
}
