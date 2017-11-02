package com.framgia.soundcloud.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anh on 23/10/2017.
 */

public class User implements Parcelable {
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    @SerializedName("full_name")
    private String mFullName;
    @SerializedName("id")
    private int mId;
    @SerializedName("username")
    private String mUserName;
    @SerializedName("avatar_url")
    private String mAvatarUrl;

    public User(String userName) {
        mUserName = userName;
    }

    protected User(Parcel in) {
        mFullName = in.readString();
        mId = in.readInt();
        mUserName = in.readString();
        mAvatarUrl = in.readString();
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFullName);
        dest.writeInt(mId);
        dest.writeString(mUserName);
        dest.writeString(mAvatarUrl);
    }
}
