package com.framgia.soundcloud.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by anh on 24/10/2017.
 */

public class Category extends BaseObservable {
    private int mImage;
    private String mName;
    private String mParam;

    public Category(int image, String name, String param) {
        mImage = image;
        mName = name;
        mParam = param;
    }

    @Bindable
    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getParam() {
        return mParam;
    }

    public void setParam(String param) {
        mParam = param;
        notifyPropertyChanged(BR.name);
    }
}
