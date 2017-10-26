package com.framgia.soundcloud.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.framgia.soundcloud.BR;

/**
 * Created by anh on 24/10/2017.
 */

public class Category extends BaseObservable implements Parcelable {
    private int mImage;
    private String mName;
    private String mParam;

    public Category(int image, String name, String param) {
        mImage = image;
        mName = name;
        mParam = param;
    }

    protected Category(Parcel in) {
        mImage = in.readInt();
        mName = in.readString();
        mParam = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mImage);
        dest.writeString(mName);
        dest.writeString(mParam);
    }
}
